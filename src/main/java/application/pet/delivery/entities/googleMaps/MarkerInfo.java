package application.pet.delivery.entities.googleMaps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkerInfo {
    private double latitude;
    private double longitude;
    private String title;
}
