import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String INSERT_USER_SQL = "INSERT INTO users (name, email, password, country) VALUES (?, ?, ?, ?)";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Retrieve form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String country = request.getParameter("country");

        // Load JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Connect to the database and insert the user
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/group1", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, country);

            int result = preparedStatement.executeUpdate();

            // Set response content type
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            if (result > 0) {
                // Registration successful
                out.println("<html><body>");
                out.println("<h2>Registration Successful</h2>");
                out.println("<p>Name: " + name + "</p>");
                out.println("<p>Email: " + email + "</p>");
                out.println("<p>Country: " + country + "</p>");
                out.println("<a href='userList.jsp'>User List</a>");
                out.println("</body></html>");
            } else {
                // Registration failed
                out.println("<html><body>");
                out.println("<h2>Registration Failed</h2>");
                out.println("<a href='register.html'>Try Again</a>");
                out.println("</body></html>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Redirect to an error page
            response.sendRedirect("error.jsp?message=" + e.getMessage().replace(" ", "+"));
        }
    }
}
