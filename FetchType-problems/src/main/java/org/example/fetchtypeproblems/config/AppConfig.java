//package org.example.fetchtypeproblems.config;
//
//import org.example.fetchtypeproblems.model.Author;
//import org.example.fetchtypeproblems.model.Book;
//import org.example.fetchtypeproblems.model.Category;
//import org.example.fetchtypeproblems.repository.AuthorRepository;
//import org.example.fetchtypeproblems.repository.BookRepository;
//import org.example.fetchtypeproblems.repository.CategoryRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Set;
//
//@Configuration
//public class AppConfig {
//
//    @Bean
//    @Transactional
//    public CommandLineRunner dataLoader(
//            AuthorRepository authorRepository,
//            BookRepository bookRepository,
//            CategoryRepository categoryRepository) {
//        return args -> {
////            // Kiểm tra nếu dữ liệu đã tồn tại thì không chèn thêm
////            if (categoryRepository.count() > 0 || authorRepository.count() > 0 || bookRepository.count() > 0) {
////                System.out.println(" Dữ liệu đã tồn tại. Bỏ qua việc chèn thêm.");
////                return;
////            }
//
//            System.out.println("🚀 Đang tạo dữ liệu mẫu...");
//
//            // 🏷️ Tạo Category
//            Category fiction = new Category(); fiction.setName("Fiction");
//            Category fantasy = new Category(); fantasy.setName("Fantasy");
//            Category history = new Category(); history.setName("History");
//            Category science = new Category(); science.setName("Science");
//            Category philosophy = new Category(); philosophy.setName("Philosophy");
//
//            categoryRepository.saveAll(List.of(fiction, fantasy, history, science, philosophy));
//
//            // ✍️ Tạo Author
//            Author author1 = new Author(); author1.setName("Le Cong Hong Phuc");
//            Author author2 = new Author(); author2.setName("Le Dinh Nguyen");
//            Author author3 = new Author(); author3.setName("Tran Minh Vu");
//            Author author4 = new Author(); author4.setName("J.K. Rowling");
//            Author author5 = new Author(); author5.setName("George Orwell");
//            Author author6 = new Author(); author6.setName("Isaac Newton");
//            Author author7 = new Author(); author7.setName("Albert Einstein");
//            Author author8 = new Author(); author8.setName("Charles Darwin");
//            Author author9 = new Author(); author9.setName("Plato");
//            Author author10 = new Author(); author10.setName("Confucius");
//
//            authorRepository.saveAll(List.of(author1, author2, author3, author4, author5,
//                    author6, author7, author8, author9, author10));
//
//            // 📚 Tạo danh sách Book
//            List<Book> books = List.of(
//                    new Book("Harry Potter and the Sorcerer's Stone", "Fantasy novel about a young wizard.", fantasy, Set.of(author4)),
//                    new Book("A Game of Thrones", "First book in A Song of Ice and Fire series.", fantasy, Set.of(author1)),
//                    new Book("The Lord of the Rings", "Epic fantasy novel.", fantasy, Set.of(author3)),
//                    new Book("Fantastic Beasts and Where to Find Them", "Companion book to the Harry Potter series.", fiction, Set.of(author4)),
//                    new Book("1984", "Dystopian novel.", fiction, Set.of(author5)),
//                    new Book("Sapiens: A Brief History of Humankind", "A book about the history of human beings.", history, Set.of(author2)),
//                    new Book("The Republic", "Philosophy book by Plato.", philosophy, Set.of(author9)),
//                    new Book("Principia Mathematica", "Mathematical laws of motion.", science, Set.of(author6)),
//                    new Book("The Prince", "Political strategy book.", philosophy, Set.of(author10)),
//                    new Book("The Theory of Relativity", "Einstein's famous theory.", science, Set.of(author7))
//            );
//
//            bookRepository.saveAll(books);
//
//            System.out.println("✅ Sample data loaded successfully! 10 books, 10 authors, 5 categories.");
//        };
//    }
//}
