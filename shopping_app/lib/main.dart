import 'package:flutter/material.dart';
import 'pages/product_list_page.dart';
import 'pages/order_history_page.dart';

void main() {
  runApp(const ShoppingApp());
}

class ShoppingApp extends StatelessWidget {
  const ShoppingApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Shopping App',
      theme: ThemeData(
        useMaterial3: true,
        primarySwatch: Colors.blue,
        scaffoldBackgroundColor: Colors.grey[100],
      ),
      initialRoute: '/product-list',
      routes: {
        '/product-list': (context) => const ProductListPage(),
        '/order-history': (context) => const OrderHistoryPage(),
      },
    );
  }
}