package application.pet.delivery.services.GeoUtils;

import application.pet.delivery.entities.DeliveryMan;
import application.pet.delivery.entities.Shop;
import application.pet.delivery.entities.User;
import org.springframework.stereotype.Component;

@Component
public class GeolocationToString {
    public String deliveryManGeoToString(DeliveryMan deliveryMan){
        return deliveryMan.getGeolocationY().toString().replace(',','.')
                +','+
                deliveryMan.getGeolocationX().toString().replace(',','.');
    }
    public String userGeoToString(User user){
        return user.getGeolocationLatitude().toString().replace(',','.')
                +','+
                user.getGeolocationLongitude().toString().replace(',','.');
    }
    public String shopGeoToString(Shop shop){
        return shop.getGeolocationY().toString().replace(',','.')
                +','+
                shop.getGeolocationX().toString().replace(',','.');
    }
}
