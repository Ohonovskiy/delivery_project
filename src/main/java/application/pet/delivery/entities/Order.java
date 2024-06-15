package application.pet.delivery.entities;

import application.pet.delivery.enums.order.Status;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "order_status")
    @Enumerated(value = EnumType.STRING)
    private Status status = Status.WAITING;

    @Column(name = "order_place_time")
    @Setter(AccessLevel.NONE)
    private Timestamp placeTime = new Timestamp(System.currentTimeMillis());
    @Column(name = "order_complete_time")
    private Timestamp completeTime;

    @Transient
    private Double price = 0d;

    @ManyToOne
    @JoinColumn(name = "user_id_fk")
    @ToString.Exclude
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id_fk"),
            inverseJoinColumns = @JoinColumn(name = "product_id_fk")
    )
    private List<Product> products = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_man_id_fk")
    @ToString.Exclude
    private DeliveryMan deliveryMan;
}
