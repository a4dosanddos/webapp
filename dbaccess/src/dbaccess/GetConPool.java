package dbaccess;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class GetConPool
 */
public class GetConPool extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DataSource dataSource = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetConPool() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		try {
			InitialContext context = new InitialContext();
			dataSource = (DataSource) context
					.lookup("java:comp/env/jdbc/oracle");
		} catch (NamingException e) {
			;
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int numActive = ((org.apache.tomcat.dbcp.dbcp.BasicDataSource) dataSource)
				.getNumActive(); // 使用中のコネクション数
		int maxActive = ((org.apache.tomcat.dbcp.dbcp.BasicDataSource) dataSource)
				.getMaxActive(); // 最大コネクション数

		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("numActive : " + numActive);
		out.println("<br>");
		out.println("maxActive : " + maxActive);
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
