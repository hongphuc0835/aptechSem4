import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import '../models/order.dart';
import '../models/cart_item.dart';
import '../models/product.dart';

class DatabaseHelper {
  static final DatabaseHelper instance = DatabaseHelper._init();
  static Database? _database;

  DatabaseHelper._init();

  Future<Database> get database async {
    if (_database != null) return _database!;
    _database = await _initDB('shopping.db');
    return _database!;
  }

  Future<Database> _initDB(String filePath) async {
    final dbPath = await getDatabasesPath();
    final path = join(dbPath, filePath);

    return await openDatabase(
      path,
      version: 1,
      onCreate: _createDB,
    );
  }

  Future _createDB(Database db, int version) async {
    await db.execute('''
      CREATE TABLE orders (
        id TEXT PRIMARY KEY,
        totalPrice REAL NOT NULL,
        date TEXT NOT NULL
      )
    ''');

    await db.execute('''
      CREATE TABLE order_items (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        order_id TEXT NOT NULL,
        product_id INTEGER NOT NULL,
        product_name TEXT NOT NULL,
        product_price REAL NOT NULL,
        product_image_url TEXT NOT NULL,
        quantity INTEGER NOT NULL,
        FOREIGN KEY (order_id) REFERENCES orders(id)
      )
    ''');
  }

  Future<void> insertOrder(Order order) async {
    final db = await database;
    final batch = db.batch();

    batch.insert('orders', {
      'id': order.id,
      'totalPrice': order.totalPrice,
      'date': order.date.toIso8601String(),
    });

    for (var item in order.items) {
      batch.insert('order_items', {
        'order_id': order.id,
        'product_id': item.product.id,
        'product_name': item.product.name,
        'product_price': item.product.price,
        'product_image_url': item.product.imageUrl,
        'quantity': item.quantity,
      });
    }

    await batch.commit();
  }

  Future<List<Order>> getOrders() async {
    final db = await database;

    final orderMaps = await db.query('orders', orderBy: 'date DESC');
    final orders = <Order>[];

    for (var orderMap in orderMaps) {
      final items = await db.query(
        'order_items',
        where: 'order_id = ?',
        whereArgs: [orderMap['id']],
      );

      final cartItems = items.map((item) => CartItem(
        product: Product(
          id: item['product_id'] as int,
          name: item['product_name'] as String,
          price: item['product_price'] as double,
          imageUrl: item['product_image_url'] as String,
        ),
        quantity: item['quantity'] as int,
      )).toList();

      orders.add(Order(
        id: orderMap['id'] as String,
        items: cartItems,
        totalPrice: orderMap['totalPrice'] as double,
        date: DateTime.parse(orderMap['date'] as String),
      ));
    }

    return orders;
  }

  Future<void> close() async {
    final db = await database;
    await db.close();
    _database = null;
  }
}