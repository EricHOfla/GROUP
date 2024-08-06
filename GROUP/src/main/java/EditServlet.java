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

@WebServlet("/editurl")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String query = "UPDATE users SET name=?, email=?, password=?, country=? WHERE id=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Set content type
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Get the id of record
        int id = Integer.parseInt(req.getParameter("id"));
        // Get the edit data we want to edit
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String country = req.getParameter("country");

        // Load JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        // Generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/group1", "root", "");
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, country);
            ps.setInt(5, id);

            int count = ps.executeUpdate();
            // Check if the record was updated successfully
            if (count == 1) {
                out.println("<html><body>");
                out.println("<h2>Record Updated Successfully</h2>");
                out.println("<a href='userList.jsp'>User List</a>");
                out.println("</body></html>");
            } else {
                out.println("<html><body>");
                out.println("<h2>Record Not Updated</h2>");
                out.println("<a href='userList.jsp'>User List</a>");
                out.println("</body></html>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            out.println("<html><body>");
            out.println("<h2>Error: " + se.getMessage().replace(" ", "+") + "</h2>");
            out.println("<a href='userList.jsp'>User List</a>");
            out.println("</body></html>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h2>Error: " + e.getMessage().replace(" ", "+") + "</h2>");
            out.println("<a href='userList.jsp'>User List</a>");
            out.println("</body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
