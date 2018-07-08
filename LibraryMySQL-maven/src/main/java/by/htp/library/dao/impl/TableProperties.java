package by.htp.library.dao.impl;

import static by.htp.library.dao.util.MySqlPropertyManager.getLogin;
import static by.htp.library.dao.util.MySqlPropertyManager.getPass;
import static by.htp.library.dao.util.MySqlPropertyManager.getUrl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableProperties {

	public static int countRows(String table) {

		int count = 0;
		try (Connection con = DriverManager.getConnection(getUrl(), getLogin(), getPass())) {
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery("SELECT COUNT(*) FROM " + table);
			while (res.next()) {
				count = res.getInt(1);
			}
		} catch (SQLException s) {
			System.out.println("SQL statement is not executed!");
		}

		return count;
	}

}
