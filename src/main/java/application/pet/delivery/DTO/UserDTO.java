package application.pet.delivery.DTO;

import application.pet.delivery.enums.Role;
import application.pet.delivery.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    /**
     * The ID of the user.
     */
    private Long id;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The birthdate of the user.
     */
    private Date birthdayDate;

    /**
     * The role of the user (e.g., admin, user, deliveryMan, shop).
     */
    private Role role;

    /**
     * The status of the user (e.g., active, banned).
     */
    private Status status;

    /**
     * The list of products associated with the user.
     */
    private List<ProductDTO> products;
}
