package application.pet.delivery.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Entity
@Data
@Table(name = "manufacturers")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manufacturer_id")
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(name = "manufacturer_name")
    private String name;
    @Column(name = "manufacturer_is_active")
    private boolean isActive;
    @OneToMany(mappedBy = "manufacturer")
    private List<Product> products;

}
