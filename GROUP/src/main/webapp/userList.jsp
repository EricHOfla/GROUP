<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
            background-color: #3b86ff;
            color: #ffffff;
            border-radius: 4px;
            width: 80%;
            margin: 3px auto;
            padding: 20px 0px;
        }
        table {
            width: 80%;
            margin: 0 auto;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 5px;
            overflow: hidden;
        }
        th, td {
            padding: 12px 15px;
            border: 1px solid #ddd;
            text-align: center;
        }
        th {
            background-color: #007bff;
            color: #fff;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        a {
            text-decoration: none;
            color: #007bff;
            font-weight: bold;
        }
        a:hover {
            text-decoration: underline;
        }
        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .nav-link {
            display: inline-block;
            margin-top: 20px;
            text-align: center;
            background-color: #007bff;
            color: #fff;
            padding: 10px 15px;
            margin: 2% 10%;
            border-radius: 5px;
        }
        .nav-link:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>User List</h2>
        <%
            String url = "jdbc:mysql://localhost:3306/group1";
            String user = "root";
            String password = "";
            String query = "SELECT * FROM users";
            int num=1;
            
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try (Connection con = DriverManager.getConnection(url, user, password);
                     PreparedStatement ps = con.prepareStatement(query);
                     ResultSet rs = ps.executeQuery()) {
        %>
                <table>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Country</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    <%
                        while (rs.next()) {
                            int id = rs.getInt(1);
                            String name = rs.getString(2);
                            String email = rs.getString(3);
                            String country = rs.getString(4);
                    %>
                        <tr>
                            <td><%= num %></td>
                            <td><%= name %></td>
                            <td><%= email %></td>
                            <td><%= country %></td>
                            <td><a href="edit.jsp?id=<%= id %>">Edit</a></td>
                            <td><a href="delete?id=<%= id %>">Delete</a></td>
                        </tr>
                    <%
                    num ++;
                        }
                    %>
                </table>
        <%
            }
            } catch (ClassNotFoundException | SQLException e) {
        %>
                <h1><%= e.getMessage() %></h1>
        <%
            }
        %>
        <a href="index.html" class="nav-link">Register</a>
        <a href="LogoutServlet" class="nav-link">Logout</a>
    </div>
</body>
</html>
