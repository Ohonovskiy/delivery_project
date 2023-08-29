package application.pet.delivery.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.aspectj.weaver.ast.Or;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entity class representing product information.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "products")
public class Product {

    /**
     * The unique ID of the product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    @Setter(AccessLevel.NONE)
    private Long id;

    /**
     * The name of the product.
     */
    @Column(name = "product_name")
    private String name;

    /**
     * The expiration date of the product. Nullable.
     */
    @Column(name = "product_expire_date")
    @Nullable
    private Date expireDate;

    /**
     * The price of the product.
     */
    @Column(name = "product_price")
    private Double price;

    /**
     * The quantity of the product. Nullable.
     */
    @Nullable
    @Column(name = "product_quantity")
    private Integer quantity;

    /**
     * The description of the product.
     */
    @Column(name = "product_description")
    private String description;

    /**
     * The photo of the product.
     */
    @Column(name = "product_image")
    private String image;

    /**
     * The manufacturer associated with the product.
     */
    @ManyToOne
    @JoinColumn(name = "manufacturer_id_fk")
    @NonNull
    @ToString.Exclude
    private Manufacturer manufacturer;

    /**
     * The list of users who have this product in their possession.
     */
    @ManyToMany(mappedBy = "products")
    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    private List<User> users = new ArrayList<>();

    /**
     * The list of shops associated with product.
     */
    @ManyToMany(mappedBy = "products")
    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    private List<Shop> shops = new ArrayList<>();

    @ManyToMany(mappedBy = "products")
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();
}
