package application.pet.delivery.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a shop entity in the application.
 */
@Entity
@Data
@Table(name = "shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "shop_id")
    private Long id;

    /**
     * The name of the shop.
     */
    @Column(name = "shop_name")
    private String name;

    /**
     * The X-coordinate of the shop's geolocation.
     */
    @Column(name = "shop_geolocation_x")
    private Double geolocationX;

    /**
     * The Y-coordinate of the shop's geolocation.
     */
    @Column(name = "shop_geolocation_y")
    private Double geolocationY;

    @Setter(AccessLevel.NONE)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "shop_product",
            joinColumns = @JoinColumn(name = "shop_id_fk", referencedColumnName = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id_fk", referencedColumnName = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    /**
     * Adds a product to the shop's list of products.
     *
     * @param product The product to be added.
     */
    public void addProduct(Product product){
        products.add(product);
        product.getShops().add(this);
    }

    /**
     * Adds a list of products to the shop's list of products.
     *
     * @param productsList The list of products to be added.
     */
    public void addProducts(List<Product> productsList){
        productsList.removeIf(product -> products.contains(product));
        productsList.forEach(product -> product.getShops().add(this));
        products.addAll(productsList);
    }

    /**
     * Removes a product from the shop's list of products.
     *
     * @param product The product to be removed.
     */
    public void removeProduct(Product product){
        products.remove(product);
        product.getShops().remove(this);
    }

    /**
     * Removes a product from the shop's list of products based on its ID.
     *
     * @param id The ID of the product to be removed.
     */
    public void removeProduct(Long id){
        products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .ifPresent(product -> product.getShops().remove(this));

        products.removeIf(product -> product.getId().equals(id));
    }
}
