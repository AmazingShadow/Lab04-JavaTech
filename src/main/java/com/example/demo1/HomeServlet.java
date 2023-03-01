package com.example.demo1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {""})
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        if (page == null) {
            response.getWriter().println("<h1>Welcome to our website<h1>");
        }
        switch(page) {
            case "about":
                request.getRequestDispatcher("/about.jsp").forward(request, response);
                break;
            case "contact":
                request.getRequestDispatcher("/contact.jsp").forward(request, response);
                break;
            case "help":
                request.getRequestDispatcher("/help.jsp").forward(request, response);
                break;
        }
    }
}
