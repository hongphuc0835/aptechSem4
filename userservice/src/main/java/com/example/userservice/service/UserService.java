package com.example.userservice.service;

import com.example.userservice.dto.UpdateUserRequestDTO;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.security.JwtUtil;
import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.dto.UserResponseDTO;
import com.example.userservice.entity.Role;
import com.example.userservice.entity.User;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Đăng ký người dùng
    public UserResponseDTO register(UserRequestDTO request) {
        // Kiểm tra xem email có đã tồn tại không
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email đã được đăng ký");
        }

        // Tạo mới đối tượng User
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPasswordHash()));

        // Lấy role "USER" từ cơ sở dữ liệu
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("bạn chưa thể tạo tài khoản ngay lúc này ")); // role user chưa có trên hệ thống nên chưa thể thục hiện gán

        // Gán role cho user
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        // Lưu user vào cơ sở dữ liệu
        userRepository.save(user);

        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }


    // Đăng nhập và trả về JWT
    public String login(String email, String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không thể để trống");
        }

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new RuntimeException("Email không tồn tại"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Mật khẩu không chính xác");
        }

        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return jwtUtil.generateToken(user.getEmail(), roleNames, user.getId());
    }

    // Gửi OTP qua email
    public void sendOTP(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new RuntimeException("Email chưa được đăng ký"));

        emailService.sendOtpEmail(email);
    }

    // Xác thực OTP và đổi mật khẩu
    public void verifyOtpAndChangePassword(String email, String otp, String newPassword) {
        if (!emailService.verifyOtp(email, otp)) {
            throw new RuntimeException("OTP không hợp lệ hoặc đã hết hạn");
        }

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new RuntimeException("Email không tồn tại"));

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        emailService.removeOtp(email);
        System.out.println("Mật khẩu của " + email + " đã được đổi thành công");
    }

    // Cập nhật thông tin user
    public void updateUserByEmail(UpdateUserRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User không tồn tại với email: " + request.getEmail()));

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            user.setName(request.getName());
        }
        if (request.getPhone() != null && !request.getPhone().trim().isEmpty()) {
            user.setPhone(request.getPhone());
        }
        if (request.getAddress() != null && !request.getAddress().trim().isEmpty()) {
            user.setAddress(request.getAddress());
        }
        // Thêm role nếu có
        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(request.getRoleIds()));
            user.getRoles().addAll(roles);
        }


        userRepository.save(user);
        System.out.println("User với email " + request.getEmail() + " đã được cập nhật thành công!");
    }


    // Xóa user
    public void deleteUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User không tồn tại với email: " + email));

        userRepository.delete(user);
        System.out.println("🗑User với email " + email + " đã bị xóa thành công!");
    }

    // Lấy thông tin user
    public UserDTO getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User không tồn tại với email: " + email));

        return new UserDTO(user.getId(),
                            user.getName(),
                            user.getEmail(),
                            user.getAddress(),
                            user.getPhone());
    }
}