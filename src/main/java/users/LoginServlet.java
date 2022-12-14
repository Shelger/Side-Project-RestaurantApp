package users;


import database.DbHandler;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if(username != null) {
            response.sendRedirect("/user");
        } else {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            VelocityEngine ve = (VelocityEngine) request.getServletContext().getAttribute("velocity");
            VelocityContext context = new VelocityContext();
            String path = request.getServletPath();
            context.put("path", path);
            Template template = ve.getTemplate("templates/login.html");
            StringWriter sw = new StringWriter();
            template.merge(context, sw);
            out.println(sw);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("pass");
        HttpSession session = request.getSession();
        DbHandler dbHandler = DbHandler.getInstance();
        boolean flag = dbHandler.authenticate(user, pass);
        if (flag) {
            session.setAttribute("username", user);
            response.sendRedirect("/restaurants");
        }
        else
            response.sendRedirect("/login");
    }
}
