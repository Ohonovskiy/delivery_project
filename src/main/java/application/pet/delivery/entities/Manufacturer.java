package application.pet.delivery.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

/**
 * Entity class representing manufacturer information.
 */
@Entity
@Data
@Table(name = "manufacturers")
public class Manufacturer {
    /**
     * The unique ID of the manufacturer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manufacturer_id")
    @Setter(AccessLevel.NONE)
    private Long id;

    /**
     * The name of the manufacturer.
     */
    @Column(name = "manufacturer_name")
    private String name;

    /**
     * Indicates whether the manufacturer is active or not.
     */
    @Column(name = "manufacturer_is_active")
    private boolean isActive;

    /**
     * The list of products associated with the manufacturer.
     */
    @OneToMany(mappedBy = "manufacturer")
    private List<Product> products;

}
