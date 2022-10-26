import com.google.gson.*;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.util.ArrayList;
import java.util.List;

public class RestaurantData {
    private double curLatitude, curLongitude;
    private List<Restaurant> list;
    private long distance;

    public RestaurantData(double lat, double lon, long distance) {
        this.curLatitude = lat;
        this.curLongitude = lon;
        this.distance = distance;
        list = new ArrayList<Restaurant>();
        addRestaurants();
    }

    public void addRestaurants () {
        String host = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?keyword=restaurant" +
                "&location=" + curLatitude + "," + curLongitude +
                "&radius=" + distance;

//        String charset = "UTF-8";
        HttpResponse<JsonNode> response = Unirest.get(host)
                .asJson();
//        System.out.println(response.getStatus());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonObject jsonObject = (JsonObject) jp.parse(response.getBody().toString());
        JsonArray arr = jsonObject.getAsJsonArray("results");
        for(JsonElement elem : arr) {
            list.add(gson.fromJson(elem, Restaurant.class));
        }
//        String prettyJsonString = gson.toJson(je);
//        System.out.println(prettyJsonString);
    }

    public void printRestaurants() {
        for(Restaurant r: list) System.out.println(r);
    }

    public static void main(String[] args) {
        RestaurantData rd = new RestaurantData(32.987145999999996, -96.92448, 500);
        rd.printRestaurants();
    }

}
