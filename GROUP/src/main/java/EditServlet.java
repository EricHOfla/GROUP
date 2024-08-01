import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editurl")
public class EditServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String query = "update users set name=?,email=?,country=? where id=?";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //set content type
        res.setContentType("text/html");
        //get the id of record
        int id = Integer.parseInt(req.getParameter("id"));
        //get the edit data we want to edit
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        //LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        //generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/group1", "root", "");
        	PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, country);
            ps.setInt(4, id);
            int count = ps.executeUpdate();
            // Check if the record was deleted successfully
            if (count == 1) {
                // Redirect to userList.jsp with a success message
                res.sendRedirect("userList.jsp?message=Record+Updated+Successfully");
            } else {
                // Redirect to userList.jsp with an error message
                res.sendRedirect("userList.jsp?message=Record+Not+Updated");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            // Redirect to userList.jsp with an error message
            res.sendRedirect("userList.jsp?message=" + se.getMessage().replace(" ", "+"));
        } catch (Exception e) {
            e.printStackTrace();
            // Redirect to userList.jsp with an error message
            res.sendRedirect("userList.jsp?message=" + e.getMessage().replace(" ", "+"));
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
