import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Servlet annotation to define the URL pattern for the servlet
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Method to handle POST requests
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve email and password parameters from the request
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Check if email or password is missing, if so, redirect to login page with an error message
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            response.sendRedirect("login.html?error=missing");
            return;
        }

        // Declare database connection, prepared statement, and result set
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/group1", "root", "");
            // Prepare SQL query to check for user with given email and password
            pst = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
            pst.setString(1, email);
            pst.setString(2, password);

            // Execute query and check if user exists
            rs = pst.executeQuery();
            if (rs.next()) {
                // User exists, create session and set email attribute
                HttpSession session = request.getSession();
                session.setAttribute("email", email);
                // Redirect to AdminServlet
                response.sendRedirect("AdminServlet");
            } else {
                // User does not exist, redirect to login page with an invalid error message
                response.sendRedirect("login.html?error=invalid");
            }
        } catch (Exception e) {
            // Print stack trace and redirect to login page with error message
            e.printStackTrace();
            response.sendRedirect("login.html?error=error&message=" + e.getMessage());
        } finally {
            // Close result set, prepared statement, and connection in the finally block
            if (rs != null) try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            if (pst != null) try { pst.close(); } catch (Exception e) { e.printStackTrace(); }
            if (conn != null) try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
