import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:intl/intl.dart';
import 'package:intl/date_symbol_data_local.dart';
import 'package:google_fonts/google_fonts.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await initializeDateFormatting('vi', null); // Fix lỗi Locale
  runApp(const WeatherApp());
}

class WeatherApp extends StatelessWidget {
  const WeatherApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Dự báo thời tiết',
      theme: ThemeData(
        textTheme: GoogleFonts.robotoTextTheme(),
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.indigo),
        useMaterial3: true,
      ),
      home: const WeatherScreen(),
      debugShowCheckedModeBanner: false,
    );
  }
}

class WeatherScreen extends StatefulWidget {
  const WeatherScreen({super.key});

  @override
  State<WeatherScreen> createState() => _WeatherScreenState();
}

class _WeatherScreenState extends State<WeatherScreen> {
  final TextEditingController _cityController = TextEditingController();
  final String apiKey = 'bbf32fc37b4a01d319c573d4ea48a2cb';
  final String apiUrl = 'https://api.openweathermap.org/data/2.5/forecast';

  bool isLoading = false;
  String errorMessage = '';
  String cityName = '';
  Map<String, dynamic> currentWeather = {};
  List<Map<String, dynamic>> hourlyForecast = [];
  List<Map<String, dynamic>> dailyForecast = [];

  @override
  void initState() {
    super.initState();
    fetchWeather('Hanoi');
  }

  Future<void> fetchWeather(String city) async {
    setState(() {
      isLoading = true;
      errorMessage = '';
    });

    try {
      final response = await http.get(
        Uri.parse('$apiUrl?q=$city&appid=$apiKey&units=metric&lang=vi'),
      );

      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        cityName = data['city']['name'];

        final List<dynamic> list = data['list'];
        currentWeather = list[0];

        hourlyForecast = list.take(8).map<Map<String, dynamic>>((item) => {
          'time': item['dt_txt'],
          'temp': item['main']['temp'],
          'icon': item['weather'][0]['icon'],
        }).toList();

        final dailyMap = <String, Map<String, dynamic>>{};
        for (var item in list) {
          final date = item['dt_txt'].substring(0, 10);
          if (!dailyMap.containsKey(date) &&
              item['dt_txt'].contains('12:00:00')) {
            dailyMap[date] = {
              'temp_max': item['main']['temp_max'],
              'temp_min': item['main']['temp_min'],
              'icon': item['weather'][0]['icon'],
              'date': date,
            };
          }
        }
        dailyForecast = dailyMap.values.toList();

        setState(() {
          isLoading = false;
        });
      } else {
        setState(() {
          errorMessage = 'Lỗi: ${response.body}';
          isLoading = false;
        });
      }
    } catch (e) {
      setState(() {
        errorMessage = 'Lỗi kết nối: $e';
        isLoading = false;
      });
    }
  }

  String formatDay(String dateStr) {
    final date = DateTime.parse(dateStr);
    final formatter = DateFormat.EEEE('vi');
    return formatter.format(date); // ví dụ: Thứ ba
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('🌤 Dự báo - $cityName'),
        centerTitle: true,
        backgroundColor: Colors.indigo,
        foregroundColor: Colors.white,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            TextField(
              controller: _cityController,
              decoration: InputDecoration(
                labelText: 'Nhập tên thành phố (VD: Hanoi)',
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(12),
                ),
                suffixIcon: IconButton(
                  icon: const Icon(Icons.search),
                  onPressed: () {
                    String city = _cityController.text.trim();
                    if (city.isEmpty) city = 'Hanoi';
                    fetchWeather(city);
                  },
                ),
              ),
              onSubmitted: (value) {
                if (value.trim().isEmpty) {
                  fetchWeather('Hanoi');
                } else {
                  fetchWeather(value.trim());
                }
              },
            ),
            const SizedBox(height: 16),
            if (isLoading)
              const CircularProgressIndicator()
            else if (errorMessage.isNotEmpty)
              Text(errorMessage, style: const TextStyle(color: Colors.red))
            else if (currentWeather.isEmpty)
                const Text('Không có dữ liệu thời tiết')
              else
                Expanded(
                  child: SingleChildScrollView(
                    child: Column(
                      children: [
                        // Thời tiết hiện tại
                        Card(
                          color: Colors.indigo[50],
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(16),
                          ),
                          child: Padding(
                            padding: const EdgeInsets.all(16),
                            child: Column(
                              children: [
                                Image.network(
                                  'http://openweathermap.org/img/wn/${currentWeather['weather'][0]['icon']}@2x.png',
                                  width: 100,
                                ),
                                Text(
                                  '${currentWeather['main']['temp'].toStringAsFixed(1)}°C',
                                  style: const TextStyle(
                                    fontSize: 48,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                Text(
                                  currentWeather['weather'][0]['description'],
                                  style: const TextStyle(fontSize: 18),
                                ),
                                const SizedBox(height: 8),
                                Text(
                                  'Độ ẩm: ${currentWeather['main']['humidity']}% | Gió: ${currentWeather['wind']['speed']} km/h',
                                ),
                              ],
                            ),
                          ),
                        ),

                        const SizedBox(height: 20),

                        // Dự báo theo giờ
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text('🌤 Dự báo theo giờ',
                                style: TextStyle(
                                    fontSize: 20, fontWeight: FontWeight.bold)),
                            const SizedBox(height: 8),
                            SizedBox(
                              height: 110,
                              child: ListView.builder(
                                scrollDirection: Axis.horizontal,
                                itemCount: hourlyForecast.length,
                                itemBuilder: (context, index) {
                                  final hour = hourlyForecast[index];
                                  final time = DateFormat.Hm('vi').format(
                                    DateTime.parse(hour['time']),
                                  );
                                  final temp =
                                  hour['temp'].toStringAsFixed(0);
                                  final icon = hour['icon'];
                                  return Container(
                                    width: 80,
                                    margin: const EdgeInsets.only(right: 8),
                                    decoration: BoxDecoration(
                                      color: Colors.white,
                                      borderRadius: BorderRadius.circular(12),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Colors.grey.shade300,
                                          blurRadius: 4,
                                          offset: const Offset(2, 2),
                                        )
                                      ],
                                    ),
                                    padding: const EdgeInsets.all(8),
                                    child: Column(
                                      mainAxisAlignment:
                                      MainAxisAlignment.spaceEvenly,
                                      children: [
                                        Text(time),
                                        Image.network(
                                          'http://openweathermap.org/img/wn/$icon@2x.png',
                                          width: 40,
                                          height: 40,
                                        ),
                                        Text('$temp°C'),
                                      ],
                                    ),
                                  );
                                },
                              ),
                            ),
                          ],
                        ),

                        const SizedBox(height: 20),

                        // Dự báo theo ngày
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text('📅 Dự báo theo ngày',
                                style: TextStyle(
                                    fontSize: 20, fontWeight: FontWeight.bold)),
                            const SizedBox(height: 8),
                            ListView.builder(
                              shrinkWrap: true,
                              physics: const NeverScrollableScrollPhysics(),
                              itemCount: dailyForecast.length,
                              itemBuilder: (context, index) {
                                final day = dailyForecast[index];
                                return Card(
                                  elevation: 2,
                                  margin:
                                  const EdgeInsets.symmetric(vertical: 6),
                                  shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(10)),
                                  child: ListTile(
                                    leading: Image.network(
                                      'http://openweathermap.org/img/wn/${day['icon']}@2x.png',
                                      width: 50,
                                    ),
                                    title: Text(formatDay(day['date'])),
                                    subtitle: Text(
                                        '🌡 Cao: ${day['temp_max'].toStringAsFixed(0)}°C | Thấp: ${day['temp_min'].toStringAsFixed(0)}°C'),
                                  ),
                                );
                              },
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                ),
          ],
        ),
      ),
    );
  }
}
