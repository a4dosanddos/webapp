package dbaccess;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class InsertUpdate
 */
public class InsertUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 一意制約エラーコード
	private static final int ORA_00001 = 1;

	private Connection con = null;
	DataSource ds = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InsertUpdate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		try {
			InitialContext ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/oracle");
			// con = ds.getConnection();
		} catch (NamingException e) {
			// InitialContext生成時に例外発生
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");

		try {
			con = ds.getConnection();
			Statement st = con.createStatement();

			// insert 発行
			insert(st);
			con.commit();

			// SAMPLE2 テーブルの一覧表示
			ResultSet rs = st.executeQuery("SELECT * FROM SAMPLE2");
			while (rs.next()) {
				out.println(rs.getString(1));
				out.println(rs.getString(2));
				out.println("<br>");
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		out.println("</body>");
		out.println("</html>");
	}

	// insert を発行する
	// 一意制約エラーが発生した場合、対象行に対して update を実行する
	private void insert(Statement st) {
		try {
			int result = st
					.executeUpdate("INSERT INTO SAMPLE2 VALUES (3, 'oro')");
		} catch (SQLException e) {
			if (e.getErrorCode() == ORA_00001) {
				update(st);
			}
		}
	}

	// update を発行する
	private void update(Statement st) {
		try {
			int result = st
					.executeUpdate("UPDATE SAMPLE2 SET NAME='ahe' WHERE ID=3");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
