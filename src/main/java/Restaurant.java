import com.google.gson.annotations.SerializedName;

public class Restaurant {
    private String name;
    @SerializedName(value = "place_id")
    private String id;
    private int priceLevel;
    private double rating;

//    private String[] types;
//    private String location;
//    private double latitude, longitude;
//    private List<Review> reviews;


    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", priceLevel=" + priceLevel +
                ", rating=" + rating +
                '}';
    }
}
