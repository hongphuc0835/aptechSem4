package com.example.userservice.controller;

import com.example.userservice.dto.*;
import com.example.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController // đánh dấu Spring Bean, Spring Container quản lý.
                //Định nghĩa một REST API controller
@RequestMapping("/users") //Xử lý HTTP request đến một URL cụ thể
@AllArgsConstructor
public class UserController {

    @Autowired
    // Nhiệm vụ: Nhận request từ client, gọi userService, và trả response.
    private final UserService userService;

    // API đăng ký người dùng
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO request) {
        return ResponseEntity.ok(userService.register(request));
    }

    // API đăng nhập và trả về JWT
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserRequestDTO request) {
        return ResponseEntity.ok(new AuthResponseDTO(
                userService.login(request.getEmail(), request.getPasswordHash())
        ));
    }

    // API gửi OTP qua email
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody OtpRequestDTO request) {
        userService.sendOTP(request.getEmail());
        return ResponseEntity.ok("OTP đã gửi đến email " + request.getEmail());
    }

    // API xác thực OTP và đổi mật khẩu
    @PostMapping("/verifyAndChange")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpRequestDTO request) {
        userService.verifyOtpAndChangePassword( request.getEmail(), request.getOtp(), request.getNewPassword());
        return ResponseEntity.ok("Mật khẩu đã đổi thành công!");
    }

    // API cap nhật người dùng
//    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PutMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserRequestDTO request) {
        userService.updateUserByEmail(request);
        return ResponseEntity.ok("User đã được cập nhật thành công!");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok("User với email " + email + " đã bị xóa!");
    }


    // lấy ra người dung theo email
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping("/getByEmail{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }


}
