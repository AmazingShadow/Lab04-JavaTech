<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: hvhuy
  Date: 3/1/2023
  Time: 5:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    String message = request.getParameter("message");
    if (message.equals("success")) {
        message = "success";
    } else if(message.equals("fail")){
        message = "file not found";
    } else {
        message = "file is empty";
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Download</title>
</head>
<body>
<h1>This is Download</h1>
<h1>
    <%= message %>
</h1>

<script>
</script>
</body>
</html>
