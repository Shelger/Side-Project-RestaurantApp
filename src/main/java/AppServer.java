import apis.FetchRestaurantServlet;
import apis.RestaurantServlet;
import apis.ShowInMap;
import org.apache.velocity.app.VelocityEngine;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import users.LoginServlet;
import users.RegisterServlet;

public class AppServer {
    public static final int PORT = 8080;

    public static void main(String[] args) {

        Server server = new Server(PORT);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        handler.setAttribute("velocity", velocityEngine);
        handler.addServlet(LoginServlet.class, "/login");
        handler.addServlet(RegisterServlet.class, "/register");
        handler.addServlet(RestaurantServlet.class, "/restaurants");
        handler.addServlet(FetchRestaurantServlet.class, "/restaurantsData");
        handler.addServlet(ShowInMap.class, "/map");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase("templates");
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { resourceHandler, handler });
        server.setHandler(handlers);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
