package application.pet.delivery.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    private User user;

    @ManyToMany(mappedBy = "orders")
    private List<Product> products = new ArrayList<>();

    @OneToOne
    @Nullable
    private DeliveryMan deliveryMan;
}
