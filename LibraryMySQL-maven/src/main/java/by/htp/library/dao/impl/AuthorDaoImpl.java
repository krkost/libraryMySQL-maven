package by.htp.library.dao.impl;

import static by.htp.library.dao.util.MySqlPropertyManager.getLogin;
import static by.htp.library.dao.util.MySqlPropertyManager.getPass;
import static by.htp.library.dao.util.MySqlPropertyManager.getUrl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.htp.library.entity.Author;
import by.htp.library.entity.Book;

public class AuthorDaoImpl {
	
	private static final String SELECT_AUTHOR_BYID = "SELECT * from author WHERE id_author = ?";
	
	public Author read(int id) {

		Author author = null;

		try (Connection conn = DriverManager.getConnection(getUrl(), getLogin(), getPass())) {

			PreparedStatement ps = conn.prepareStatement(SELECT_AUTHOR_BYID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				author = buildAuthor(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return author;
	}
	
	private Author buildAuthor(ResultSet rs) throws SQLException {
		Author author = new Author();
		author.setName(rs.getString("name"));
		author.setSurname(rs.getString("surname"));
		return author;
	}

}
