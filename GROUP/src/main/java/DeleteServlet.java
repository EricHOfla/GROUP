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

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final String QUERY = "DELETE FROM users WHERE id=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Set content type for HTML response
        res.setContentType("text/html");

        res.getWriter();

        // Get the id parameter from the request
        int id = Integer.parseInt(req.getParameter("id"));

        // Load JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Establish connection and execute the delete operation
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/group1", "root", "");
             PreparedStatement ps = con.prepareStatement(QUERY)) {

            ps.setInt(1, id);
            int count = ps.executeUpdate();

            // Check if the record was deleted successfully
            if (count == 1) {
                // Redirect to userList.jsp with a success message
                res.sendRedirect("userList.jsp?message=Record+Deleted+Successfully");
            } else {
                // Redirect to userList.jsp with an error message
                res.sendRedirect("userList.jsp?message=Record+Not+Deleted");
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
