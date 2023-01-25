package restaurantInfo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.util.ArrayList;
import java.util.List;

public class RestaurantData {
    private double curLatitude, curLongitude;
    private List<Restaurant> list;
    private long distance = 1000;

    public RestaurantData(double lat, double lon) {
        this.curLatitude = lat;
        this.curLongitude = lon;
        list = new ArrayList<Restaurant>();
        addRestaurants();
    }

    public void addRestaurants () {
        String host = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?keyword=restaurant" +
                "&location=" + curLatitude + "," + curLongitude +
                "&radius=" + distance +
                "&key=";

//        String charset = "UTF-8";
        HttpResponse<JsonNode> response = Unirest.get(host)
                .asJson();
//        System.out.println(response.getStatus());
        JsonParser jp = new JsonParser();
        JsonObject jsonObject = (JsonObject) jp.parse(response.getBody().toString());
        JsonArray arr = jsonObject.getAsJsonArray("results");
        for(JsonElement elem : arr) {
            JsonObject tmp = elem.getAsJsonObject();
            JsonObject geometry = tmp.get("geometry").getAsJsonObject();
            JsonObject location = geometry.get("location").getAsJsonObject();
            int price_level = 1;
            if(tmp.get("price_level") != null) price_level = tmp.get("price_level").getAsInt();
            list.add(new Restaurant(tmp.get("name").getAsString(),
                    tmp.get("place_id").getAsString(),
                    price_level,
                    tmp.get("rating").getAsDouble(),
                    tmp.get("vicinity").getAsString(),
                    location.get("lat").getAsDouble(),
                    location.get("lng").getAsDouble()
                    ));
        }
    }

    public void printRestaurants() {
        for(Restaurant r: list) System.out.println(r);
    }

    public List<Restaurant> getList() {
        return list;
    }

    public static void main(String[] args) {
        RestaurantData rd = new RestaurantData(32.987145999999996, -96.92448);
        rd.addRestaurants();
        rd.printRestaurants();
    }
}
