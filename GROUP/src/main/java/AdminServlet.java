import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is logged in by verifying the email attribute in the session
        if (request.getSession().getAttribute("email") != null) {
            // Forward to the admin dashboard JSP
            request.getRequestDispatcher("userList.jsp").forward(request, response);
        } else {
            // Redirect to login page if not authorized
            response.sendRedirect("login.html");
        }
    }
}
