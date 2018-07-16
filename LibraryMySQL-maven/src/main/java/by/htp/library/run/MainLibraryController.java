package by.htp.library.run;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.htp.library.dao.BookDao;
import by.htp.library.dao.impl.BookDaoImpl;
import by.htp.library.entity.Author;
import by.htp.library.entity.Book;

public class MainLibraryController {

	public static void main(String[] args) {

		BookDao dao = new BookDaoImpl();
		List<Book> bookList = dao.list();

//		Date date = new Date(1978, 01, 23);
//		Author auth = new Author("Chloe", "Cooper", date);
//		Book bookAdd = new Book("NewBook", auth);
//		dao.add(bookAdd);
		System.out.println(bookList);

	}

}
