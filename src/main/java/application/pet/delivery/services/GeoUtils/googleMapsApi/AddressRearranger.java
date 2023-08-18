package application.pet.delivery.services.GeoUtils.googleMapsApi;

import org.springframework.stereotype.Component;

@Component
public class AddressRearranger {
    public String rearrangeAddress(String address){
        String[] addressComponents = address.split(",");
        int numComponents = addressComponents.length;

        if(numComponents == 6){
            String city = addressComponents[numComponents - 4];
            String state = addressComponents[numComponents - 3];
            String street = addressComponents[numComponents - 6];
            String number = addressComponents[numComponents - 5];

            return state + "," + city + ", " + street + "," + number;
        }
        return "";
    }
}
