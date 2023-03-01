package com.example.demo1;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/welcome"})
public class Welcome extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter write = resp.getWriter();

        String email = "";
        Cookie[] cookies = req.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("email")) {
                email = cookie.getValue();
            }
        }
        if (email.equals("")) {
            write.println("Sai tài khoản hoặc mật khẩu!");
//            resp.sendRedirect("/login");
        } else {
            write.println("Chào mừng " + email + " đã quay trở lại!");
        }
    }
}
