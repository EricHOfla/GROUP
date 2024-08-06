<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    // Check if the user is authenticated
    HttpSession sessionn = request.getSession(false); // Get the session if it exists
    if (session == null || sessionn.getAttribute("email") == null) {
        // Redirect to login page if the user is not authenticated
        response.sendRedirect("login.html");
        return; // Exit the script
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User List</title>
    
</head>
<body>
    <div class="container">
        <h2>User List</h2>
        <%
            String url = "jdbc:mysql://localhost:3306/group1";
            String user = "root";
            String pass = "";
            String query = "SELECT * FROM users";
            int num = 1;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try (Connection con = DriverManager.getConnection(url, user, pass);
                     PreparedStatement ps = con.prepareStatement(query);
                     ResultSet rs = ps.executeQuery()) {
        %>
                <table border=1>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Password</th>
                        <th>Country</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    <%
                        while (rs.next()) {
                            int id = rs.getInt(1);
                            String name = rs.getString(2);
                            String email = rs.getString(3);
                            String password = rs.getString(4);
                            String country = rs.getString(5);
                    %>
                        <tr>
                            <td><%= num %></td>
                            <td><%= name %></td>
                            <td><%= email %></td>
                            <td><%= password %></td>
                            <td><%= country %></td>
                            <td><a href="edit.jsp?id=<%= id %>">Edit</a></td>
                            <td><a href="delete?id=<%= id %>">Delete</a></td>
                        </tr>
                    <%
                            num++;
                        }
                    %>
                </table>
        <%
            }
            } catch (ClassNotFoundException | SQLException e) {
        %>
                <h1>Error: <%= e.getMessage() %></h1>
        <%
            }
        %>
        <a href="index.html" class="nav-link">Register</a>
        <a href="LogoutServlet" class="nav-link">Logout</a>
    </div>
</body>
</html>
