package by.htp.library.run;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.htp.library.dao.BookDao;
import by.htp.library.dao.impl.AuthorDaoImpl;
import by.htp.library.dao.impl.BookDaoImpl;
import by.htp.library.entity.Author;
import by.htp.library.entity.Book;

public class MainLibraryController {

	public static void main(String[] args) {

		BookDao dao = new BookDaoImpl();
		Date date = new Date(1970, 11, 23);
		Author auth = new Author("David", "White", date);
		Book bookAdd = new Book("NewBook", auth);
		
		dao.add(bookAdd);
	//	dao.delete(12);
		List<Book> bookList = dao.list();
		System.out.println(bookList);
		

	}

}
