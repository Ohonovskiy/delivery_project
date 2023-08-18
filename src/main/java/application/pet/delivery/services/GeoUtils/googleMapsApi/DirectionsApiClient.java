package application.pet.delivery.services.GeoUtils.googleMapsApi;

import application.pet.delivery.entities.DeliveryMan;
import application.pet.delivery.entities.Order;
import application.pet.delivery.entities.Shop;
import application.pet.delivery.services.GeoUtils.GeolocationToString;
import application.pet.delivery.services.ShopService;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DirectionsApiClient {
    private static final String API_KEY = "AIzaSyB484J_o5zls0LGtwLiIKauvGgSssRZiKE";
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/directions/json";
    private static final GeolocationToString geolocationToString = new GeolocationToString();
    private static final AddressRearranger addressRearranger = new AddressRearranger();

    private final ShopService shopService;

    @Autowired
    public DirectionsApiClient(ShopService shopService) {
        this.shopService = shopService;
    }

    public String[] getTripInfo(String start, String destination) throws Exception {

        String requestUrl = BASE_URL +
                "?origin=" + start +
                "&destination=" + destination +
                "&key=" + API_KEY;

        return getInfo(requestUrl);
    }

    public String[] getTripInfo(DeliveryMan deliveryMan) throws Exception {
        Order order = deliveryMan.getOrder();
        StringBuilder wayPoints = new StringBuilder();

        List<Shop> shops = shopService.sortShopsByDistance(order, deliveryMan);

        for (Shop shop : shops) {
            wayPoints
                    .append("via")
                    .append(':')
                    .append(geolocationToString.shopGeoToString(shop))
                    .append("%7C");
        }
        wayPoints.delete(wayPoints.length() - 3, wayPoints.length());

        String requestUrl = BASE_URL +
                "?origin=" + geolocationToString.deliveryManGeoToString(deliveryMan) +
                "&waypoints=" + wayPoints +
                "&destination=" + geolocationToString.userGeoToString(order.getUser()) +
                "&key=" + API_KEY;

        System.out.println(requestUrl);

        return getInfo(requestUrl);
    }

    private String[] getInfo(String requestUrl) throws Exception {
        HttpGet httpGet = new HttpGet(requestUrl);

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            String response = EntityUtils.toString(httpClient.execute(httpGet).getEntity());
            JSONObject jsonObject = new JSONObject(response);
            JSONArray routesArray = jsonObject.getJSONArray("routes");

            if (routesArray.length() > 0) {
                JSONObject routeObject = routesArray.getJSONObject(0);
                String startAddress = addressRearranger.rearrangeAddress(routeObject.getJSONArray("legs").getJSONObject(0).getString("start_address"));
                String endAddress = addressRearranger.rearrangeAddress(routeObject.getJSONArray("legs").getJSONObject(0).getString("end_address"));
                int distanceMeters = routeObject.getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getInt("value");

                return new String[]{startAddress, endAddress, distanceMeters + ""};

            } else {
                throw new RuntimeException("Can't find route...");
            }
        }
    }

}
