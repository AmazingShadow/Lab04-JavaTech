package com.example.demo1;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.net.URISyntaxException;
import java.net.URL;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(urlPatterns = {"/image2"})
public class ImageServlet2 extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("image2.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // Lấy tên file từ đường dẫn ảnh
        URL url = ImageServlet2.class.getProtectionDomain().getCodeSource().getLocation();
        String imagePath = "";
        String downloadPath = "";
        String fileName = "";
        try {
            Path path = Paths.get(url.toURI());
            Path projectDir = path.getParent().getParent().getParent().getParent();
            String absolutePath = projectDir.toString();
            imagePath = absolutePath + "\\src\\main\\webapp\\public\\images\\image1.jpg";

            fileName = imagePath.substring(imagePath.lastIndexOf("\\") + 1);

            downloadPath = absolutePath + "\\src\\main\\webapp\\public\\download\\" + fileName;
        } catch (URISyntaxException e) {
            // Xử lý ngoại lệ
            System.out.println(e.getMessage());
        }
        // Thiết lập header trả về response
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentType("image/jpeg");

        // Copy dữ liệu từ file vào response output stream
        try (InputStream inputStream = new FileInputStream(imagePath)) {
            OutputStream outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        // Lưu ảnh vào đường dẫn downloadPath
        File downloadFile = new File(downloadPath);
        try (InputStream inputStream = new FileInputStream(imagePath)) {
            OutputStream outputStream = new FileOutputStream(downloadFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}
