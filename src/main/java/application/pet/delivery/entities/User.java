package application.pet.delivery.entities;

import application.pet.delivery.enums.Role;
import application.pet.delivery.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Comparable<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "user_first_name")
    private String firstName;
    @Column(name = "user_last_name")
    private String lastName;
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_password")
    private String password;
    @Column(name = "user_birthday_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdayDate;
    @Column(name = "user_role")
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Column(name = "user_status")
    @Enumerated(value = EnumType.STRING)
    private Status status = Status.ACTIVE;
    @Column(name = "user_geolocation_x")
    private Double geolocationX;
    @Column(name = "user_geolocation_y")
    private Double geolocationY;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_product",
            joinColumns = @JoinColumn(name = "user_id_fk"),
            inverseJoinColumns = @JoinColumn(name = "product_id_fk")
    )
    private List<Product> products;

    public void addProductToCart(Product product){
        if(!products.contains(product))
            products.add(product);
    }

    public void removeProductFromCart(Product product){
        products.remove(product);
    }

    public void removeProductFromCart(int id){
        products.removeIf(product -> product.getId() == id);
    }

    public void removeAllProductsFromCart(){
        products.clear();
    }


    @Override
    public int compareTo(User user) {
        return this.getId().compareTo(user.getId());
    }
}
