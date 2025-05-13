import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'package:intl/intl.dart';

void main() async {
  // Thay bằng API key thực của bạn
  const String apiKey = 'bbf32fc37b4a01d319c573d4ea48a2cb'; // CẬP NHẬT API KEY MỚI NẾU LỖI 401
  const String apiUrl = 'https://api.openweathermap.org/data/2.5/forecast';

  while (true) {
    // Nhập tên thành phố
    print('\nNhập tên thành phố (ví dụ: Hanoi, London) hoặc "exit" để thoát: ');
    String? city = stdin.readLineSync()?.trim();

    // Kiểm tra thoát
    if (city?.toLowerCase() == 'exit') {
      print('Đã thoát chương trình.');
      break;
    }

    // Kiểm tra đầu vào
    if (city == null || city.isEmpty) {
      print('Lỗi: Tên thành phố không hợp lệ. Sử dụng mặc định: Hanoi');
      city = 'Hanoi';
    }

    print('Đang tải dữ liệu thời tiết cho $city...');
    print('URL: $apiUrl?q=$city&appid=$apiKey&units=metric&lang=vi');

    try {
      // Gọi API
      final response = await http.get(
        Uri.parse('$apiUrl?q=$city&appid=$apiKey&units=metric&lang=vi'),
      );

      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        final weatherList = data['list'] as List<dynamic>;

        // Lọc dữ liệu lúc 12:00 mỗi ngày
        final dailyWeather = weatherList.where((item) => item['dt_txt'].contains('12:00:00')).toList();

        if (dailyWeather.isEmpty) {
          print('Không tìm thấy dữ liệu thời tiết cho $city.');
          continue; // Quay lại vòng lặp để nhập thành phố mới
        }

        print('\nDỰ BÁO THỜI TIẾT 5 NGÀY - ${city.toUpperCase()}');
        print('=================================');

        for (var weather in dailyWeather) {
          final date = DateTime.parse(weather['dt_txt']);
          final formattedDate = DateFormat('dd/MM/yyyy').format(date);
          final temp = weather['main']['temp'].toStringAsFixed(1);
          final description = weather['weather'][0]['description'];
          final icon = weather['weather'][0]['icon'];

          print('Ngày: $formattedDate');
          print('Nhiệt độ: $temp°C');
          print('Thời tiết: $description');
          print('Biểu tượng: http://openweathermap.org/img/wn/$icon@2x.png');
          print('---------------------------------');
        }
      } else {
        print('Lỗi: Không thể tải dữ liệu thời tiết (Mã lỗi: ${response.statusCode})');
        print('Phản hồi: ${response.body}');
        if (response.statusCode == 401) {
          print('Hướng dẫn: API key không hợp lệ. Tạo key mới tại https://openweathermap.org/.');
        } else if (response.statusCode == 404) {
          print('Hướng dẫn: Thành phố không tìm thấy. Dùng tên tiếng Anh (ví dụ: Hanoi, London).');
        }
      }
    } catch (e) {
      print('Lỗi: $e');
      print('Hướng dẫn: Kiểm tra kết nối internet hoặc cấu hình dự án.');
    }
  }
}