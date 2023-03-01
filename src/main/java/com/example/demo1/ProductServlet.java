package com.example.demo1;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import pojo.Product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@WebServlet(urlPatterns = {"/ProductService"})
public class ProductServlet extends HttpServlet {

    List<Product> products = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        System.out.println("Bat dau voi Servlet!!!");
        products.add(new Product(1, "Iphone 11", 549));
        products.add(new Product(2, "Iphone 13 Pro", 999));
        products.add(new Product(3, "Iphone 13 Promax", 1199));
        products.add(new Product(4, "Iphone 13 Promax", 1299));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JsonObject responseObject = new JsonObject();

        if (request.getParameter("id") == null) {
            if (products.size() == 0) {
                responseObject.addProperty("code", 1);
                responseObject.addProperty("message", "Không có sản phẩm nào");
                responseObject.add("data", null);
            } else {
                responseObject.addProperty("code", 0);
                responseObject.addProperty("message", "Đọc sản phẩm thành công!");
                responseObject.add("data", gson.toJsonTree(products));
            }
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            Optional product = products.stream().filter(p -> p.getId() == id).findFirst();
            if (product.isEmpty()) {
                responseObject.addProperty("code", 2);
                responseObject.addProperty("message", "Không tìm thấy sản phẩm nào với mã số " + id);
            } else {
                responseObject.addProperty("code", 0);
                responseObject.addProperty("message", "Lấy dữ liệu sản phẩm có mã số  " + id + " thành công");
                responseObject.add("data", gson.toJsonTree(product));
            }
        }

        // Gửi JSON về cho client
        response.getWriter().write(responseObject.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JsonObject responseObject = new JsonObject();

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String price = request.getParameter("price");

        if (id.equals("") || name.equals("") || price.equals("")) {
            responseObject.addProperty("code", 1);
            responseObject.addProperty("message", "Vui lòng nhập đầy đủ thông tin");
            response.getWriter().write(responseObject.toString());
            return;
        } else {
            int productID = 0;
            double productPrice = 0.0;
            try {
                productID = Integer.parseInt(id);
                productPrice = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                responseObject.addProperty("code", 3);
                responseObject.addProperty("message", "Tham số truyền vào không hợp lệ");
                response.getWriter().write(responseObject.toString());
                return;
            }

            // kiểm tra sản phẩm đã tồn tại hay chưa
            boolean check = false;
            for (Product product : products) {
                if (product.getId() == productID) {
                    check = true;
                    break;
                }
            }

            if (check) {
                responseObject.addProperty("code", 2);
                responseObject.addProperty("message", "Sản phẩm có id = " + productID + " đã tồn tại");
            } else {
                Product newProduct = new Product(productID, name, productPrice);
                products.add(newProduct);
                responseObject.addProperty("code", 0);
                responseObject.addProperty("message", "Thêm mới sản phẩm thành công");
                responseObject.add("data", gson.toJsonTree(newProduct));
            }
        }
        // Gửi JSON về cho client
        response.getWriter().write(responseObject.toString());
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JsonObject responseObject = new JsonObject();
        // Gửi JSON về cho client

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String price = request.getParameter("price");

        if (id.equals("") || name.equals("") || price.equals("")) {
            responseObject.addProperty("code", 1);
            responseObject.addProperty("message", "Vui lòng nhập đầy đủ thông tin");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(responseObject.toString());
            return;
        } else {
            int productID = 0;
            double productPrice = 0.0;
            try {
                productID = Integer.parseInt(id);
                productPrice = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                responseObject.addProperty("code", 3);
                responseObject.addProperty("message", "Tham số truyền vào không hợp lệ");
                response.getWriter().write(responseObject.toString());
                return;
            }

            // kiểm tra sản phẩm đã tồn tại hay chưa
            Product p = new Product();
            boolean check = false;
            for (Product product : products) {
                if (product.getId() == productID) {
                    p = product;
                    check = true;
                    break;
                }
            }

            if (check) {
                p.setName(name);
                p.setPrice(productPrice);
                responseObject.addProperty("code", 0);
                responseObject.addProperty("message", "Cập nhật sản phẩm có id = " + productID + " thành công");
                responseObject.add("data", gson.toJsonTree(p));
            } else {
                responseObject.addProperty("code", 2);
                responseObject.addProperty("message", "Sản phẩm có id = " + productID + " không tồn tại");
            }

        }
        response.getWriter().write(responseObject.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JsonObject responseObject = new JsonObject();

        String id = request.getParameter("id");
        if (id.equals("")) {
            responseObject.addProperty("code", 1);
            responseObject.addProperty("message", "Vui lòng nhập đầy đủ thông tin");
            response.getWriter().write(responseObject.toString());
            return;
        } else {
            int productID = 0;
            try {
                productID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                responseObject.addProperty("code", 3);
                responseObject.addProperty("message", "Tham số truyền vào không hợp lệ");
                response.getWriter().write(responseObject.toString());
                return;
            }

            Product p = new Product();
            boolean check = false;
            for (Product product : products) {
                if (product.getId() == productID) {
                    p = product;
                    check = true;
                    break;
                }
            }

            if (check) {
                products.remove(p);
                responseObject.addProperty("code", 0);
                responseObject.addProperty("message", "Xoá sản phẩm có id =" + productID + " thành công");
                responseObject.add("data", gson.toJsonTree(p));
            } else {
                responseObject.addProperty("code", 2);
                responseObject.addProperty("message", "Sản phẩm có id = " + productID + " không tồn tại");
            }

        }
        response.getWriter().write(responseObject.toString());
    }
}
