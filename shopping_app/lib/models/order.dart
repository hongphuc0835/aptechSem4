import 'package:shopping_app/models/cart_item.dart';

class Order {
  final String id;
  final List<CartItem> items;
  final double totalPrice;
  final DateTime date;

  Order({
    required this.id,
    required this.items,
    required this.totalPrice,
    required this.date,
  });
}