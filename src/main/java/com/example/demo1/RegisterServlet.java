package com.example.demo1;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/register.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
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

        String error = "";
        if (name.equals("")) {
            error = "Vui lòng nhập tên";
        } else if (email.equals("")) {
            error = "Vui lòng nhập email";
        } else if (birthDay.equals("")) {
            error = "Vui lòng chọn ngày sinh";
        } else if (birthtime.equals("")) {
            error = "Vui lòng chọn giờ sinh";
        } else if (male == null && female == null) {
            error = "Vui lòng chọn giới tính";
        } else if (country.equals("default")) {
            error = "Vui lòng chọn quốc gia";
        } else if (vscode == null && sublimeText == null && eclipse == null && atom == null && intellij == null) {
            error = "Vui lòng chọn ít nhất 1 IDE";
        } else if (message.equals("")) {
            error = "Vui lòng nhập lời nhắn";
        }

        if (error.length() > 0) {
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<title>Title</title>");
            out.println("</head>\n");
            out.println("<h1>"+error+"</h1>");
            out.println("</html>");
            return;
        }
        request.getRequestDispatcher("information.jsp").forward(request, response);
    }
}
