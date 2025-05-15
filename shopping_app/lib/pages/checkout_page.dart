import 'package:flutter/material.dart';
import '../models/cart_item.dart';
import '../models/order.dart';
import '../utils/database_helper.dart';
import 'dart:developer' as developer;

class CheckoutPage extends StatelessWidget {
  final List<CartItem> cart;

  const CheckoutPage({super.key, required this.cart});

  double get totalPrice {
    return cart.fold(
      0,
          (sum, item) => sum + item.product.price * item.quantity,
    );
  }

  Future<void> _saveOrder(BuildContext context) async {
    if (cart.isEmpty) {
      developer.log('Cart is empty, cannot save order');
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('🛒 Giỏ hàng trống!')),
      );
      return;
    }

    try {
      final order = Order(
        id: DateTime.now().millisecondsSinceEpoch.toString(),
        items: cart,
        totalPrice: totalPrice,
        date: DateTime.now(),
      );

      developer.log('Saving order: ${order.id}, items: ${order.items.length}, total: ${order.totalPrice}');
      await DatabaseHelper.instance.insertOrder(order);
      cart.clear(); // Clear cart
      developer.log('Order saved + cart cleared');

      // Show success message
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('✅ Thanh toán thành công!')),
      );

      // Navigate back to product list
      Navigator.pushNamedAndRemoveUntil(
        context,
        '/product-list',
            (route) => false,
      );
    } catch (e) {
      developer.log('Error saving order: $e');
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Lỗi khi lưu đơn hàng: $e')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    final colorScheme = Theme.of(context).colorScheme;

    return Scaffold(
      appBar: AppBar(
        title: const Text('💳 Thanh Toán'),
        centerTitle: true,
        backgroundColor: colorScheme.primaryContainer,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            const Align(
              alignment: Alignment.centerLeft,
              child: Text(
                'Tóm tắt đơn hàng',
                style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
              ),
            ),
            const SizedBox(height: 12),
            Expanded(
              child: cart.isEmpty
                  ? const Center(
                child: Text('🛍️ Không có sản phẩm nào trong đơn.'),
              )
                  : ListView.separated(
                itemCount: cart.length,
                separatorBuilder: (_, __) =>
                const Divider(height: 20, thickness: 1),
                itemBuilder: (context, index) {
                  final item = cart[index];
                  return ListTile(
                    leading: ClipRRect(
                      borderRadius: BorderRadius.circular(8),
                      child: Image.network(
                        item.product.imageUrl,
                        width: 50,
                        height: 50,
                        fit: BoxFit.cover,
                      ),
                    ),
                    title: Text(item.product.name),
                    subtitle: Text('Số lượng: ${item.quantity}'),
                    trailing: Text(
                      '\$${(item.product.price * item.quantity).toStringAsFixed(2)}',
                      style: const TextStyle(fontWeight: FontWeight.bold),
                    ),
                  );
                },
              ),
            ),
            const Divider(),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                const Text(
                  'Tổng cộng:',
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
                Text(
                  '\$${totalPrice.toStringAsFixed(2)}',
                  style: const TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                      color: Colors.green),
                ),
              ],
            ),
            const SizedBox(height: 24),
            ElevatedButton.icon(
              onPressed: cart.isEmpty ? null : () => _saveOrder(context),
              icon: const Icon(Icons.check_circle_outline),
              label: const Text('Xác nhận thanh toán'),
              style: ElevatedButton.styleFrom(
                minimumSize: const Size(double.infinity, 50),
                backgroundColor: Colors.green,
                foregroundColor: Colors.white,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
