package application.pet.delivery.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Data Transfer Object (DTO) class representing product information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    /**
     * The ID of the product.
     */
    private Long id;

    /**
     * The name of the product.
     */
    private String name;

    /**
     * The expiration date of the product.
     */
    private Date expireDate;

    /**
     * The price of the product.
     */
    private Double price;

    /**
     * The manufacturer information associated with the product.
     */
    private ManufacturerDTO manufacturerDTO;
}
