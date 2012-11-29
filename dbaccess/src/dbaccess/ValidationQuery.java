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
 * Servlet implementation class ValidationQuery
 */
public class ValidationQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection con = null;
	DataSource ds = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ValidationQuery() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		try {
			InitialContext ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/oracle");
			// con = ds.getConnection();
		} catch (NamingException e) {
			// InitialContextê∂ê¨éûÇ…ó·äOî≠ê∂
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<body>");

		try {
			con = ds.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM SAMPLE");

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
}
