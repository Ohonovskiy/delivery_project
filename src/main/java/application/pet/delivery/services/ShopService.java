package application.pet.delivery.services;

import application.pet.delivery.entities.DeliveryMan;
import application.pet.delivery.entities.Order;
import application.pet.delivery.entities.Product;
import application.pet.delivery.entities.Shop;
import application.pet.delivery.repositories.ShopRepository;
import application.pet.delivery.services.GeoUtils.DistanceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service class for managing shop-related operations.
 */
@Service
@Transactional
public class ShopService {
    private final ShopRepository shopRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    /**
     * Retrieves a shop optional object by its ID.
     *
     * @param id The ID of the shop.
     * @return The optional shop entity.
     */
    @Transactional(readOnly = true)
    public Optional<Shop> getById(Long id){
        return Optional.of(shopRepository.getReferenceById(id));
    }

    @Transactional(readOnly = true)
    public List<Shop> getAll(){
        return shopRepository.findAll();
    }

    public List<Shop> sortShopsByDistance(DeliveryMan deliveryMan, Order order){
        Set<Shop> uniqueShops = new HashSet<>();

        for (Product product : order.getProducts()) {
            uniqueShops.addAll(product.getShops());
        }

        List<Shop> shops = new ArrayList<>(uniqueShops);

        shops.sort(Comparator.comparingDouble(shop -> DistanceCalculator.calculateDistance(
                deliveryMan.getGeolocationY(), deliveryMan.getGeolocationX(),
                shop.getGeolocationY(), shop.getGeolocationX()
        )));

        return shops;
    }

    /**
     * Removes a shop from the database.
     *
     * @param shop The shop entity to be removed.
     */
    public void remove(Shop shop){
        shopRepository.delete(shop);
    }

    /**
     * Removes a shop from the database based on its ID.
     *
     * @param id The ID of the shop to be removed.
     */
    public void remove(Long id){
        shopRepository.deleteById(id);
    }

    /**
     * Saves a shop to the database.
     *
     * @param shop The shop entity to be saved.
     */
    public void save(Shop shop){
        shopRepository.save(shop);
    }
}
