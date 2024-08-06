<%@ page import="java.sql.*, javax.servlet.*, javax.servlet.http.*" %>
<%@ page import="javax.servlet.jsp.*" %>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="ISO-8859-1">
      <title>Book Registration</title>
      <link rel="stylesheet" href="style.css">
   
      <style>
         body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            padding: 20px;
         }

         .container-fluid {
            max-width: 600px;
            margin: auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
         }

         .card {
            padding: 20px;
         }

         h2 {
            background-color: #3b86ff;
            color: #ffffff;
            padding: 10px;
            border-radius: 4px;
         }
         .text-center{
                align-items: center;
                text-align: center;
         }

         .table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
         }

         .table th, .table td {
            border: 1px solid #dee2e6;
            padding: 8px;
            text-align: left;
         }

         .table tr:nth-child(even) {
            background-color: #f2f2f2;
         }

         .table-hover tbody tr:hover {
            background-color: #e9ecef;
         }

         input[type="text"] {
            width: 97%;
            padding: 8px;
            border: 1px solid #ced4da;
            border-radius: 4px;
            outline: none;
         }

         input[type="submit"], input[type="reset"] {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
         }

         input[type="submit"] {
            background-color: #007bff;
            color: #ffffff;
         }

         input[type="submit"]:hover {
            background-color: #0056b3;
         }

         input[type="reset"] {
            background-color: #6c757d;
            color: #ffffff;
            margin: 0 80%;
         }

         input[type="reset"]:hover {
            background-color: #5a6268;
         }

         a {
            display: inline-block;
            margin-top: 10px;
            color: #007bff;
            text-decoration: none;
         }

       
         .atag{
               margin: -90px 30rem;
                
         }
      </style>
   </head>
   
    <% 
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            out.println("<h1>Invalid ID</h1>");
            return;
        }
        
       
        
        int id = Integer.parseInt(idStr);
        String query = "SELECT * FROM users WHERE id=?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/group1", "root", "");
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
    %>
                <body class="container-fluid card">
      <h2 class="text-center">User Update</h2>
      <form action="editurl?id=<%= id %>" method="post">
         <table class="table table-hover">
                        <tr>
                            <td>Name</td>
                            <td><input type="text" name="name" value="<%= rs.getString(2) %>"></td>
                        </tr>
                        <tr>
                            <td>Email</td>
                            <td><input type="text" name="email" value="<%= rs.getString(3) %>"></td>
                        </tr>
                         <tr>
                            <td>Password</td>
                            <td><input type="text" name="password" value="<%= rs.getString(4) %>"></td>
                        </tr>
                        <tr>
                            <td>Country</td>
                            <td><input type="text" name="country" value="<%= rs.getString(5) %>"></td>
                        </tr>
                        <tr>
                            <td><input type="submit" value="Update"></td>
                            <td><input type="reset" value="Cancel"></td>
                        </tr>
                    </table>
                </form>
    <%
            } else {
                out.println("<h1>No record found with ID " + id + "</h1>");
            }
        } catch (SQLException se) {
            out.println("<h1>" + se.getMessage() + "</h1>");
            se.printStackTrace();
        } catch (Exception e) {
            out.println("<h1>" + e.getMessage() + "</h1>");
            e.printStackTrace();
        } finally {
            // Close resources
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    %>
    <a href="userList.jsp">User List</a>
    <a class="atag" href="index.html">Register</a>
</body>
</html>
