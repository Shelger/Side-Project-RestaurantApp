package apis;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import restaurantInfo.Restaurant;
import restaurantInfo.RestaurantData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FetchRestaurantServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("username") == null) response.sendRedirect("/login");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = response.getWriter();

        String lat = request.getParameter("lat");
        String lng = request.getParameter("lng");
        System.out.println(lat);
        System.out.println(lng);
        if(lat != null && lng != null) {
            RestaurantData restaurantData = new RestaurantData(Double.parseDouble(lat), Double.parseDouble(lng));
            List<Restaurant> list = restaurantData.getList();
            JsonArray resJson = new JsonArray();
            for (Restaurant restaurant : list) {
                JsonObject rt = new JsonObject();
                rt.addProperty("name", restaurant.getName());
                rt.addProperty("id", restaurant.getName());
                rt.addProperty("price", restaurant.getPriceLevel());
                rt.addProperty("rating", restaurant.getRating());
                rt.addProperty("location", restaurant.getLocation());
                rt.addProperty("lat", restaurant.getLatitude());
                rt.addProperty("lng", restaurant.getLongitude());
                resJson.add(rt);
            }
            writer.println(resJson);
        }
    }
}
