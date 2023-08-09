package application.pet.delivery.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) class representing manufacturer information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerDTO {

    /**
     * The ID of the manufacturer.
     */
    private Long id;

    /**
     * The name of the manufacturer.
     */
    private String name;
}
