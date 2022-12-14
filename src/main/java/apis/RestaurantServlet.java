package apis;

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

public class RestaurantServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("username") == null) response.sendRedirect("/login");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = response.getWriter();

        VelocityEngine ve = (VelocityEngine) request.getServletContext().getAttribute("velocity");
        VelocityContext context = new VelocityContext();

        Template template = ve.getTemplate("templates/restaurants.html");
        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        writer.println(sw);

    }
}
