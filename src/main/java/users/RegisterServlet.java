package users;

import database.DbHandler;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        String usernameParam = request.getParameter("username");

        String password = request.getParameter("pass");

        DbHandler dbHandler = DbHandler.getInstance();

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if(dbHandler.existedUser(usernameParam)) {
            response.getWriter().println("Username: " + usernameParam + " has already been used! Plz Try another.");
        } else if(!password.matches(regex)) {
            response.getWriter().println("Password needs at least 8 characters, 1 uppercase letter, 1 lowercase, 1 number and 1 special character.");
        } else {
            dbHandler.register(usernameParam, password);
            response.sendRedirect("/login");
        }
    }
}
