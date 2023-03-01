package com.example.demo1;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(urlPatterns = {"/download"})
public class DownloadServlet extends HttpServlet {

    private static final int BUFFER_SIZE = 4096;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy tên file từ đường dẫn ảnh
        URL url = DownloadServlet.class.getProtectionDomain().getCodeSource().getLocation();
        String imagePath = "";
        String downloadDir = "";
        String fileName = request.getParameter("file");

        try {
            Path path = Paths.get(url.toURI());
            Path projectDir = path.getParent().getParent().getParent().getParent();
            String absolutePath = projectDir.toString();
            imagePath = absolutePath + "\\src\\main\\webapp\\public\\images\\" + fileName;
            downloadDir = absolutePath + "\\src\\main\\webapp\\public\\download\\" + fileName;
        } catch (URISyntaxException e) {
            // Xử lý ngoại lệ
            System.out.println(e.getMessage());
        }

        if (fileName == null) {
            response.sendRedirect("download.jsp?message=empty");
            return;
        }

        File file = new File(imagePath);
        if (!file.exists()) {
            response.sendRedirect("download.jsp?message=fail");
        } else {
            String speedParam = request.getParameter("speed");
            int downloadSpeed = 0;
            if (speedParam != null) {
                try {
                    downloadSpeed = Integer.parseInt(speedParam);
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            ServletOutputStream outputStream = response.getOutputStream();
            InputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[BUFFER_SIZE];
            if (downloadSpeed > 0) {
                long startTime = System.currentTimeMillis();
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    long expectedElapsedTime = (long) ((bytesRead / (downloadSpeed * 1024.0)) * 1000);
                    if (elapsedTime < expectedElapsedTime) {
                        try {
                            Thread.sleep(expectedElapsedTime - elapsedTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            File downloadFile = new File(downloadDir);
            try (InputStream inputStream1 = new FileInputStream(imagePath)) {
                OutputStream outputStream1 = new FileOutputStream(downloadFile);
                byte[] buffer1 = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream1.read(buffer1)) != -1) {
                    outputStream1.write(buffer1, 0, bytesRead);
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {
                response.sendRedirect("download.jsp?message=success");
            }
            inputStream.close();
            outputStream.close();
        }
    }

}
