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

	// ��Ӑ���G���[�R�[�h
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
			// InitialContext�������ɗ�O����
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
		response.setContentType("text/html; charset=Windows-31J");
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");

		try {
			con = ds.getConnection();
			Statement st = con.createStatement();

			// SAMPLE2 �e�[�u���̈ꗗ�\�� ( �X�V�O )
			out.println("<h1>SAMPLE2 �e�[�u���̈ꗗ�\�� ( �X�V�O )</h1><br>");
			ResultSet rs_before = st.executeQuery("SELECT * FROM SAMPLE2");
			while (rs_before.next()) {
				out.println(rs_before.getString(1));
				out.println(rs_before.getString(2));
				out.println("<br>");
			}
			out.println("<br>");

			// insert ���s
			insert(st);
			con.commit();

			// SAMPLE2 �e�[�u���̈ꗗ�\�� ( �X�V�� )
			ResultSet rs_after = st.executeQuery("SELECT * FROM SAMPLE2");
			out.println("<h1>SAMPLE2 �e�[�u���̈ꗗ�\�� ( �X�V�� )</h1><br>");
			while (rs_after.next()) {
				out.println(rs_after.getString(1));
				out.println(rs_after.getString(2));
				out.println("<br>");
			}
			rs_before.close();
			rs_after.close();
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

	// insert �𔭍s����
	// ��Ӑ���G���[�����������ꍇ�A�Ώۍs�ɑ΂��� update �����s����
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

	// update �𔭍s����
	private void update(Statement st) {
		try {
			int result = st
					.executeUpdate("UPDATE SAMPLE2 SET NAME='aheahe' WHERE ID=3");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
