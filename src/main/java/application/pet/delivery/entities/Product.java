package application.pet.delivery.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(name = "product_name")
    private String name;
    @Column(name = "product_expire_date")
    @Nullable
    private Date expireDate;
    @Column(name = "product_price")
    private Double price;

    @Nullable
    @Column(name = "product_quantity")
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "product_manufacturer_id")
    @NonNull
    private Manufacturer manufacturer;

    @ManyToMany(mappedBy = "products")
    @Setter(AccessLevel.NONE)
    private List<User> users;
}
