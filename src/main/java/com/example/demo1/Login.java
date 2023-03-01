package com.example.demo1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/login"})
public class Login extends HttpServlet {

    HashMap<String, String> listAccount = new HashMap<>();

    @Override
    public void init() throws ServletException {
        System.out.println("Bat dau voi Servlet!!!");
        listAccount.put("admin@gmail.com", "123456");
        listAccount.put("admin1@gmail.com", "123456");
        listAccount.put("admin2@gmail.com", "123456");
        listAccount.put("admin3@gmail.com", "123456");
        listAccount.put("admin4@gmail.com", "123456");

    }
    @Override
    public void destroy() {
        System.out.println("Ket Thuc voi Servlet!!!");
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.service(req, resp);
        System.out.println("Phuong thuc cua request " + req.getMethod());// tar về phuong thức tương ứng
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html"); //dạng trả về html
        PrintWriter write = resp.getWriter();
        write.println("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\n" +
                "        integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">\n" +
                "  <title>Login</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "  <div class=\"row justify-content-center\">\n" +
                "    <div class=\"col-md-8\">\n" +
                "      <h4 class=\"my-5 text-primary text-center\">Đăng nhập</h4>\n" +
                "      <form action=\"/login\" method=\"POST\">\n" +
                "        <div class=\"mb-3\">\n" +
                "          <label for=\"email\" class=\"form-label\">Email address</label>\n" +
                "          <input type=\"email\" class=\"form-control\" id=\"email\" name=\"email\" aria-describedby=\"emailHelp\">\n" +
                "          <div id=\"emailHelp\" class=\"form-text\">We'll never share your email with anyone else.</div>\n" +
                "        </div>\n" +
                "        <div class=\"mb-3\">\n" +
                "          <label for=\"password\" class=\"form-label\">Password</label>\n" +
                "          <input type=\"password\" class=\"form-control\" id=\"password\" name=\"password\">\n" +
                "        </div>\n" +
                "        <div class=\"mb-3 form-check\">\n" +
                "          <input type=\"checkbox\" class=\"form-check-input\" id=\"exampleCheck1\">\n" +
                "          <label class=\"form-check-label\" for=\"exampleCheck1\">Check me out</label>\n" +
                "        </div>\n" +
                "        <button type=\"submit\" class=\"btn btn-primary\">Submit</button>\n" +
                "      </form>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "\n" +
                "</html>");
        write.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (listAccount.get(email).equals(password)) {
            Cookie cookie = new Cookie("email", email);
            cookie.setMaxAge(30);
            resp.addCookie(cookie);
            resp.sendRedirect("/welcome");
        } else {
            resp.sendRedirect("/welcome");
        }
    }
}
