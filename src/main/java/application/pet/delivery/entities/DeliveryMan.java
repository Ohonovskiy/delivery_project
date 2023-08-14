package application.pet.delivery.entities;

import application.pet.delivery.enums.Role;
import application.pet.delivery.enums.Status;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "delivery_men")
public class DeliveryMan {
    @Id
    @Column(name = "delivery_man_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "delivery_man_first_name")
    private String firstName;

    @Column(name = "delivery_man_last_name")
    private String lastName;

    @Column(name = "delivery_man_email")
    private String email;

    @Column(name = "delivery_man_password")
    private String password;

    @Column(name = "delivery_man_birthday_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdayDate;

    @Column(name = "delivery_man_role")
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.ROLE_DELIVERY_MAN;

    @Column(name = "delivery_man_status")
    @Enumerated(value = EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Column(name = "delivery_man_geolocation_x")
    private Double geolocationX;

    @Column(name = "delivery_man_geolocation_y")
    private Double geolocationY;

    @OneToOne
    @JoinColumn(name = "order_id_fk")
    @Nullable
    private Order order;

    public void setOrder(Order newOrder){
        this.order = newOrder;
        newOrder.setDeliveryMan(this);
    }

    public void removeOrder(){
        this.order = null;
    }
}
