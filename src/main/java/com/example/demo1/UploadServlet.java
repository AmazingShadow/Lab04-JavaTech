package com.example.demo1;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static java.nio.charset.Charset.isSupported;

@WebServlet(urlPatterns = {"/upload"})
@MultipartConfig
public class UploadServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher  = request.getRequestDispatcher("upload.html");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Part filePart = request.getPart("file");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String fileExtension = getFileExtension(fileName);

            if (!isSupportedExtension(fileExtension)) {
                out.println("Unsupported file extension");
                return;
            }

            boolean overrideIfExists = request.getParameter("overrideIfExists") != null;
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            if (Files.exists(filePath) && !overrideIfExists) {
                out.println("File already exists");
                return;
            }

            File uploadsDir = new File(getServletContext().getRealPath("/") + UPLOAD_DIR);
            if (!uploadsDir.exists()) {
                uploadsDir.mkdir();
            }
            Path savePath = Paths.get(uploadsDir + File.separator + fileName);
            Files.deleteIfExists(savePath);
            Files.copy(filePart.getInputStream(), savePath);

            String downloadLink = request.getContextPath() + "/" + UPLOAD_DIR + fileName;
            out.println("File uploaded. Click <a href='" + downloadLink + "'>here</a> to visit file.");
        } catch (Exception ex) {
            out.println("Error: " + ex.getMessage());
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    private boolean isSupportedExtension(String extension) {
        String[] supportedExtensions = {"txt", "doc", "docx", "img", "pdf", "rar", "zip"};
        for (String supportedExtension : supportedExtensions) {
            if (supportedExtension.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}
