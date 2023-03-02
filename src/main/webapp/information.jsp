<%--
  Created by IntelliJ IDEA.
  User: hvhuy
  Date: 3/2/2023
  Time: 2:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%
  String name = request.getParameter("name");
  String email = request.getParameter("email");
  String birthDay = request.getParameter("birthday");
  String birthtime = request.getParameter("birthtime");
  String male = request.getParameter("male");
  String female = request.getParameter("female");
  String country = request.getParameter("country");
  String vscode = request.getParameter("vscode");
  String sublimeText = request.getParameter("sublime-text");
  String eclipse = request.getParameter("eclipse");
  String atom = request.getParameter("atom");
  String intellij = request.getParameter("intellij");
  String toeic = request.getParameter("toeic");
  String message = request.getParameter("message");

  String gender = "";
  if (male != null) {
      gender = "male";
  } else {
      gender = "female";
  }

%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<%--  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"--%>
<%--        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">--%>
    <title>Informaton</title>
</head>
<body>
<table border="2">
  <tr>
    <td>Họ tên</td>
    <td><%= name %></td>
  </tr>
  <tr>
    <td>Email</td>
    <td><%= email%></td>
  </tr>
  <tr>
    <td>Ngày sinh</td>
    <td><%= birthDay %></td>
  </tr>
  <tr>
    <td>Giờ sinh</td>
    <td><%= birthtime %></td>
  </tr>
  <tr>
    <td>Giới tính</td>
    <td><%= gender %></td>
  </tr>
  <tr>
    <td>Quốc gia</td>
    <td><%= country %></td>
  </tr>
  <tr>
    <td>IDE yêu thích</td>
    <td colspan="5">
      <% if (vscode != null) {%>
      <%= vscode %> <br>
      <% } %>
      <% if (sublimeText != null) {%>
      <%= sublimeText %> <br>
      <% } %>
      <% if (eclipse != null) {%>
      <%= eclipse %> <br>
      <% } %>
      <% if (atom != null) {%>
      <%= atom %> <br>
      <% } %>
      <% if (intellij != null) {%>
      <%= intellij %> <br>
      <% } %>
    </td>
  </tr>
  <tr>
    <td>Điểm TOEIC</td>
    <td><%= toeic %></td>
  </tr>
  <tr>
    <td>Giới thiệu bản thân</td>
    <td><%= message %></td>
  </tr>
</table>

</body>
</html>
