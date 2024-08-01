

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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/group1", "root", "");
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, password);
            

            rs = pst.executeQuery();
            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("email", email);
               
                    response.sendRedirect("AdminServlet");
                } 
             else {
                // Invalid credentials, redirect back to login with error message
                response.sendRedirect("login.html?error=invalid");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.html?error=error");
        } finally {
            // Clean up resources
            if (rs != null) try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            if (pst != null) try { pst.close(); } catch (Exception e) { e.printStackTrace(); }
            if (conn != null) try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
