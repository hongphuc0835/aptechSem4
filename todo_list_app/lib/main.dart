import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';

void main() {
  runApp(const ToDoApp());
}

class ToDoApp extends StatelessWidget {
  const ToDoApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'To-Do List',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
        fontFamily: 'Roboto',
      ),
      home: const ToDoScreen(),
      debugShowCheckedModeBanner: false,
    );
  }
}

class ToDoItem {
  String description;
  bool isDone;

  ToDoItem({required this.description, this.isDone = false});

  Map<String, dynamic> toJson() => {
    'description': description,
    'isDone': isDone,
  };

  factory ToDoItem.fromJson(Map<String, dynamic> json) => ToDoItem(
    description: json['description'],
    isDone: json['isDone'],
  );
}

class ToDoScreen extends StatefulWidget {
  const ToDoScreen({super.key});

  @override
  State<ToDoScreen> createState() => _ToDoScreenState();
}

class _ToDoScreenState extends State<ToDoScreen> {
  List<ToDoItem> toDoList = [];
  final TextEditingController _inputController = TextEditingController();

  @override
  void initState() {
    super.initState();
    _loadToDoList();
  }

  Future<void> _loadToDoList() async {
    final prefs = await SharedPreferences.getInstance();
    final String? savedTasks = prefs.getString('toDoList');
    if (savedTasks != null) {
      final List<dynamic> taskData = jsonDecode(savedTasks);
      setState(() {
        toDoList = taskData.map((data) => ToDoItem.fromJson(data)).toList();
      });
    }
  }

  Future<void> _saveToDoList() async {
    final prefs = await SharedPreferences.getInstance();
    final String encodedTasks =
    jsonEncode(toDoList.map((task) => task.toJson()).toList());
    await prefs.setString('toDoList', encodedTasks);
  }

  void _addToDoItem() {
    final text = _inputController.text.trim();
    if (text.isNotEmpty) {
      setState(() {
        toDoList.add(ToDoItem(description: text));
        _inputController.clear();
      });
      _saveToDoList();
    }
  }

  void _toggleCompletion(int index) {
    setState(() {
      toDoList[index].isDone = !toDoList[index].isDone;
    });
    _saveToDoList();
  }

  void _removeToDoItem(int index) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Xác nhận xóa'),
        content: const Text('Bạn có chắc muốn xóa công việc này?'),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Hủy'),
          ),
          TextButton(
            onPressed: () {
              setState(() {
                toDoList.removeAt(index);
              });
              _saveToDoList();
              Navigator.pop(context);
            },
            child: const Text('Xóa', style: TextStyle(color: Colors.red)),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    final isDark = Theme.of(context).brightness == Brightness.dark;

    return Scaffold(
      appBar: AppBar(
        title: const Text('📋 Danh sách công việc'),
        centerTitle: true,
        backgroundColor: Theme.of(context).colorScheme.primaryContainer,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            Material(
              elevation: 2,
              borderRadius: BorderRadius.circular(12),
              child: TextField(
                controller: _inputController,
                decoration: InputDecoration(
                  hintText: 'Thêm công việc...',
                  prefixIcon: const Icon(Icons.task_alt_rounded),
                  suffixIcon: IconButton(
                    icon: const Icon(Icons.clear),
                    onPressed: () => _inputController.clear(),
                  ),
                  filled: true,
                  fillColor: isDark ? Colors.grey[800] : Colors.grey[100],
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(12),
                    borderSide: BorderSide.none,
                  ),
                  contentPadding:
                  const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
                ),
                onSubmitted: (_) => _addToDoItem(),
              ),
            ),
            const SizedBox(height: 20),
            Expanded(
              child: toDoList.isEmpty
                  ? const Center(
                child: Text(
                  '🎉 Không có việc gì phải làm!\nBạn rảnh như chó con mùa dịch.',
                  textAlign: TextAlign.center,
                  style: TextStyle(fontSize: 16, color: Colors.grey),
                ),
              )
                  : ListView.separated(
                itemCount: toDoList.length,
                separatorBuilder: (_, __) => const SizedBox(height: 10),
                itemBuilder: (context, index) {
                  final item = toDoList[index];
                  return Dismissible(
                    key: Key(item.description),
                    background: Container(
                      color: Colors.red.withOpacity(0.8),
                      alignment: Alignment.centerRight,
                      padding: const EdgeInsets.only(right: 20),
                      child: const Icon(Icons.delete_forever,
                          color: Colors.white),
                    ),
                    direction: DismissDirection.endToStart,
                    confirmDismiss: (_) async {
                      _removeToDoItem(index);
                      return false;
                    },
                    child: Card(
                      shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(12)),
                      child: ListTile(
                        leading: Checkbox.adaptive(
                          value: item.isDone,
                          onChanged: (_) => _toggleCompletion(index),
                          activeColor:
                          Theme.of(context).colorScheme.primary,
                        ),
                        title: Text(
                          item.description,
                          style: TextStyle(
                            decoration: item.isDone
                                ? TextDecoration.lineThrough
                                : null,
                            color: item.isDone
                                ? Colors.grey
                                : Colors.black87,
                          ),
                        ),
                        trailing: IconButton(
                          icon: const Icon(Icons.delete_outline),
                          onPressed: () => _removeToDoItem(index),
                        ),
                      ),
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton.extended(
        onPressed: _addToDoItem,
        label: const Text("Thêm"),
        icon: const Icon(Icons.add),
      ),
    );
  }
}
