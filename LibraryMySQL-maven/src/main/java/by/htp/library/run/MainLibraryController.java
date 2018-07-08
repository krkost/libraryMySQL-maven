package by.htp.library.run;

import java.util.ArrayList;
import java.util.List;

import by.htp.library.dao.BookDao;
import by.htp.library.dao.impl.BookDaoImpl;
import by.htp.library.dao.impl.TableProperties;
import by.htp.library.entity.Book;

public class MainLibraryController {

	public static void main(String[] args) {
		
		BookDao dao = new BookDaoImpl();
		Book book = dao.read(3);
		List <Book> bookList = dao.list();
		
		System.out.println(bookList);
		
		Book book1 = new Book("Book4", null);
		
		dao.add(book1);
	
	}

}
