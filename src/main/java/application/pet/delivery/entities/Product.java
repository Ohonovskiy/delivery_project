package application.pet.delivery.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

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
     * The manufacturer associated with the product.
     */
    @ManyToOne
    @JoinColumn(name = "product_manufacturer_id")
    @NonNull
    private Manufacturer manufacturer;

    /**
     * The list of users who have this product in their possession.
     */
    @ManyToMany(mappedBy = "products")
    @Setter(AccessLevel.NONE)
    private List<User> users;
}
