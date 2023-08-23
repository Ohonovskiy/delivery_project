package application.pet.delivery.services.GeoUtils.googleMapsApi;

import application.pet.delivery.entities.DeliveryMan;
import application.pet.delivery.entities.Shop;
import application.pet.delivery.entities.User;
import application.pet.delivery.entities.googleMaps.MarkerInfo;
import application.pet.delivery.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarkerDataService {

    private final ShopService shopService;

    @Autowired
    public MarkerDataService(ShopService shopService) {
        this.shopService = shopService;
    }

    public List<MarkerInfo> getMarkersFromShops(List<Shop> shops){
        List<MarkerInfo> markerInfos = new ArrayList<>();
        MarkerInfo markerInfo = new MarkerInfo();
        for (Shop shop : shops){
            markerInfo.setTitle(shop.getName());
            markerInfo.setLongitude(shop.getGeolocationY());
            markerInfo.setLatitude(shop.getGeolocationX());
        }

        return markerInfos;
    }

    public MarkerInfo entityToMarker(Object obj){
        if(obj instanceof DeliveryMan){
            return new MarkerInfo(
                    ((DeliveryMan) obj).getGeolocationY(),
                    ((DeliveryMan) obj).getGeolocationX(),
                    ((DeliveryMan) obj).getFirstName());
        } else if (obj instanceof User){
            return new MarkerInfo(
                    ((User) obj).getGeolocationY(),
                    ((User) obj).getGeolocationX(),
                    ((User) obj).getFirstName());
        } else if(obj instanceof Shop){
            return new MarkerInfo(
                    ((Shop) obj).getGeolocationY(),
                    ((Shop) obj).getGeolocationX(),
                    ((Shop) obj).getName());
        } else
            throw new IllegalArgumentException(String.format("You cannot cast %s to MarkerInfo", obj.getClass().getName()));
    }

    public List<MarkerInfo> generateMarkersForDeliveryman(DeliveryMan deliveryMan){
        List<MarkerInfo> markersData = new ArrayList<>();

        markersData.add(entityToMarker(deliveryMan));

        if(deliveryMan.hasOrder()){
            for(Shop shop : shopService.sortShopsByDistance(deliveryMan, deliveryMan.getOrder())){
                markersData.add(entityToMarker(shop));
            }
            markersData.add(entityToMarker(deliveryMan.getOrder().getUser()));
        }

        return markersData;
    }
}
