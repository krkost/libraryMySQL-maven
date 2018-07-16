package by.htp.library.dao.impl;

import static by.htp.library.dao.util.MySqlPropertyManager.*;

import java.sql.Connection;
import java.sql.Date;
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
	private static final String ADD_BOOK = "INSERT INTO book (title, id_author)"
			+ "VALUES (?, (SELECT id_author FROM author WHERE name=? AND surname=? AND birthdate=?))";
	private static final String DELETE_BOOK_BYID = "DELETE FROM book WHERE id_book = ?";
	private static final String SELECT_BOOKS_LIST = "SELECT * FROM book JOIN author ON book.id_author=author.id_author ORDER BY book.title";

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
		try (Connection conn = DriverManager.getConnection(getUrl(), getLogin(), getPass())) {
			PreparedStatement ps = conn.prepareStatement(SELECT_BOOKS_LIST);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bookList.add(buildBook(rs));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return bookList;
	}

	@Override
	public int add(Book book) {
		
		int result = 0;
		
		try (Connection conn = DriverManager.getConnection(getUrl(), getLogin(), getPass())) {
			String title = book.getTitle();
			
			Date birthday = book.getAuthor().getBirthDate();

			PreparedStatement ps = conn.prepareStatement(ADD_BOOK);
			
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor().getName());
			ps.setString(3, book.getAuthor().getSurname());
			ps.setDate(4, book.getAuthor().getBirthDate());
			ps.executeUpdate();
			conn.close();

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return result;
	}

	@Override
	public void delete(int id_book) {
		try (Connection conn = DriverManager.getConnection(getUrl(), getLogin(), getPass())) {
			PreparedStatement ps = conn.prepareStatement(DELETE_BOOK_BYID);
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
