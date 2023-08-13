package application.pet.delivery.entities;

import application.pet.delivery.enums.Role;
import application.pet.delivery.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Entity class representing user information.
 */
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Comparable<User> {

    /**
     * The unique ID of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "user_id")
    private Long id;

    /**
     * The first name of the user.
     */
    @Column(name = "user_first_name")
    private String firstName;

    /**
     * The last name of the user.
     */
    @Column(name = "user_last_name")
    private String lastName;

    /**
     * The email address of the user.
     */
    @Column(name = "user_email")
    private String email;

    /**
     * The password of the user.
     */
    @Column(name = "user_password")
    private String password;

    /**
     * The birthdate of the user.
     */
    @Column(name = "user_birthday_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdayDate;

    /**
     * The role of the user (e.g., admin, user, deliveryMan, shop).
     */
    @Column(name = "user_role")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    /**
     * The status of the user (e.g., active, banned).
     */
    @Column(name = "user_status")
    @Enumerated(value = EnumType.STRING)
    private Status status = Status.ACTIVE;

    /**
     * The X-coordinate of the user's geolocation.
     */
    @Column(name = "user_geolocation_x")
    private Double geolocationX;

    /**
     * The Y-coordinate of the user's geolocation.
     */
    @Column(name = "user_geolocation_y")
    private Double geolocationY;

    /**
     * The list of products associated with the user's cart.
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_product",
            joinColumns = @JoinColumn(name = "user_id_fk"),
            inverseJoinColumns = @JoinColumn(name = "product_id_fk")
    )
    private List<Product> products;

    /**
     * Adds a product to the user's cart.
     *
     * @param product The product to be added to the cart.
     */
    public void addProductToCart(Product product){
        if(!products.contains(product)){
            products.add(product);
            product.getUsers().add(this);
        }
    }

    /**
     * Removes a product from the user's cart.
     *
     * @param product The product to be removed from the cart.
     */
    public void removeProductFromCart(Product product){
        products.remove(product);
        product.getUsers().remove(this);
    }

    /**
     * Removes a product from the user's cart based on its ID.
     *
     * @param id The ID of the product to be removed from the cart.
     */
    public void removeProductFromCart(Long id){
        products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .ifPresent(product -> product.getUsers().remove(this));

        products.removeIf(product -> product.getId().equals(id));
    }

    /**
     * Removes all products from the user's cart.
     */
    public void removeAllProductsFromCart(){
        products.forEach(product -> product.getUsers().remove(this));
        products.clear();
    }

    /**
     * Compares users based on their ID.
     */
    @Override
    public int compareTo(User user) {
        return this.getId().compareTo(user.getId());
    }
}
