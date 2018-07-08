package by.htp.library.dao.impl;

import static by.htp.library.dao.util.MySqlPropertyManager.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import by.htp.library.dao.BookDao;
import by.htp.library.entity.Author;
import by.htp.library.entity.Book;

public class BookDaoImpl implements BookDao {

	private static final String SELECT_BOOK_BYID = "SELECT * from book WHERE id_book = ?";

	@Override
	public Book read(int id) {

		Book book = null;
		try (Connection conn = DriverManager.getConnection(getUrl(), getLogin(), getPass())) {
			PreparedStatement ps = conn.prepareStatement(SELECT_BOOK_BYID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				book = buildBook(rs);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return book;
	}

	@Override
	public List<Book> list() {

		List<Book> bookList = new LinkedList<>();
		for (int i = 1; i <= TableProperties.countRows("book"); i++) {
			bookList.add(read(i));
		}
		return bookList;
	}

	@Override
	public int add(Book book) {
		Statement stmt = null;
		String title = null;
		int id_book = 4, id_author = 3;
		try (Connection conn = DriverManager.getConnection(getUrl(), getLogin(), getPass())) {
			title = book.getTitle();
			System.out.print("\nInserting records into table...");
			
			String sql = "INSERT INTO book (id_book, title, id_author)" +
			        "VALUES (?, ?, ?)";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, TableProperties.countRows("book")+1);
			ps.setString(2, book.getTitle());
	        ps.setInt(3, 3);
	        ps.executeUpdate(); 


		} catch (SQLException se) {
			se.printStackTrace();
		}
		return 4;
	}

	@Override
	public void delete(int id_book) {
		try (Connection conn = DriverManager.getConnection(getUrl(), getLogin(), getPass())) {
			String query = "DELETE FROM book WHERE id_book = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id_book);
			ps.execute();

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Book book) {

	}

	private Book buildBook(ResultSet rs) throws SQLException {
		AuthorDaoImpl authDao = new AuthorDaoImpl();
		Author author = authDao.read(rs.getInt("id_author"));
		Book book = new Book();
		book.setTitle(rs.getString("title"));
		book.setAuthor(author);
		return book;
	}

}
