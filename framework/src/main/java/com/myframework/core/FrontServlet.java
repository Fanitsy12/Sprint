package com.myframework.core;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

@Info(auteur = "Fitahiana", 
version = 2.5, projet = "srpint",
    uid = "hafa-2025-001",
    date = "2025-10-19",
    contact = "andrainafanitsy@gmail.com")
public class FrontServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
       
        String path = request.getPathInfo(); 

        if (path == null || path.equals("/") || path.isEmpty()) {
            path = "/index";
        }

        String cleanPath = normalizePath(path);
        
    
        if (serveFromResources(cleanPath, request, response)) {
            return;
        }
     
        if (serveFromWebapp(cleanPath, request, response)) {
            return;
        }
      
        serveDynamicContent(cleanPath, response);
    }

    private String normalizePath(String path) {

        String clean = path.startsWith("/") ? path.substring(1) : path;
        
      
        if (clean.endsWith(".html")) {
            clean = clean.substring(0, clean.length() - 5);
        }
        
        return clean.isEmpty() ? "index" : clean;
    }

    private boolean serveFromResources(String cleanPath, HttpServletRequest request, HttpServletResponse response) {
        try {
            String resourcePath = "/pages/" + cleanPath + ".html";
            InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
            
            if (resourceStream != null) {
                // Copier le contenu du fichier resource vers la réponse
                BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));
                PrintWriter out = response.getWriter();
                
                String line;
                while ((line = reader.readLine()) != null) {
                    out.println(line);
                }
                
                reader.close();
                resourceStream.close();
                return true;
            }
        } catch (IOException e) {
          
            e.printStackTrace();
        }
        return false;
    }

    private boolean serveFromWebapp(String cleanPath, HttpServletRequest request, HttpServletResponse response) {
        try {
            String webappPath = "/" + cleanPath + ".html";
            RequestDispatcher dispatcher = request.getRequestDispatcher(webappPath);
            
            if (dispatcher != null) {
                dispatcher.forward(request, response);
                return true;
            }
        } catch (Exception e) {
           
        }
        return false;
    }

    private void serveDynamicContent(String cleanPath, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("    <title>Page Dynamique - " + cleanPath + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <h1>Page : " + cleanPath + "</h1>");
       
        out.println("    <p>Le fichier " + cleanPath + ".html n'a pas été trouvé dans resources/pages/ ni dans webapp/</p>");
        out.println("</body>");
        out.println("</html>");
    }
}