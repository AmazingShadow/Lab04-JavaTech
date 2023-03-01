<%--
  Created by IntelliJ IDEA.
  User: hvhuy
  Date: 2/28/2023
  Time: 10:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Image2</title>
</head>
<body>
<h1>This is image2</h1>
<img src="public/images/image1.jpg" alt="">
<form action="/image2" method="POST" style="display: none" class="form">
    <input type="submit">
</form>
<script>
    var form = document.querySelector(".form")
    window.onload = function() {
        form.submit()
    }
</script>
</body>
</html>
