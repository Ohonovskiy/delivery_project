package application.pet.delivery.controllers.deliveryMan;

import application.pet.delivery.entities.DeliveryMan;
import application.pet.delivery.entities.Order;
import application.pet.delivery.services.DeliveryManService;
import application.pet.delivery.services.OrderService;
import application.pet.delivery.services.GeoUtils.googleMapsApi.DirectionsApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping("/delivery")
@PreAuthorize("hasAuthority('delivery_man')")
public class DeliveryController {
    private final OrderService orderService;
    private final DeliveryManService deliveryManService;
    private final DirectionsApiClient directionsApiClient;
    private DeliveryMan currentDeliveryMan;

    @Autowired
    public DeliveryController(OrderService orderService, DeliveryManService deliveryManService, DirectionsApiClient directionsApiClient) {
        this.orderService = orderService;
        this.deliveryManService = deliveryManService;
        this.directionsApiClient = directionsApiClient;
    }

    @GetMapping("/test1")
    public String test() throws Exception {
        setCurrentDeliveryMan();

        String[] tripInfo = directionsApiClient.getTripInfo(currentDeliveryMan, currentDeliveryMan.getOrder());

        System.out.println(tripInfo[0]); // start
        System.out.println(tripInfo[1]); // destination
        System.out.println(tripInfo[2]); // int in meters
        System.out.println(tripInfo[3]); // String km

        return "delivery/index";
    }

    @GetMapping
    public String index(){
        return "delivery/index";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        setCurrentDeliveryMan();

        model.addAttribute("hasOrder", currentDeliveryMan.hasOrder());
        model.addAttribute("order", currentDeliveryMan.getOrder());

        return "delivery/profile";
    }

    @GetMapping("/availableOrders")
    public String availableOrders(Model model) throws Exception {
        setCurrentDeliveryMan();

        HashMap<Order, String> orderOrderInfo = new HashMap<>();

        for(Order order : orderService.getAllUnsignedOrders()){
            orderOrderInfo.put(order, directionsApiClient.getTripInfo(currentDeliveryMan, order)[3]);
        }

        model.addAttribute("orderAndInfo", orderOrderInfo);
        model.addAttribute("hasOrder", currentDeliveryMan.hasOrder());

        return "delivery/orders";
    }

    @PostMapping("/takeOrder")
    public String takeOrder(@RequestParam("orderId") Long orderId){
        setCurrentDeliveryMan();

        Optional<Order> optionalOrder = orderService.getById(orderId);

        if(optionalOrder.isPresent()){
            if(optionalOrder.get().getDeliveryMan() != null)
                return "delivery/orderIsTaken";

            currentDeliveryMan.setOrder(optionalOrder.get());

            deliveryManService.save(currentDeliveryMan);
        }

        return "redirect:availableOrders";
    }

    @PostMapping("/cancelDelivery")
    public String cancelOrder(){
        setCurrentDeliveryMan();

        System.out.println(currentDeliveryMan.getOrder() == null);

        currentDeliveryMan.removeOrder();
        deliveryManService.save(currentDeliveryMan);

        return "redirect:profile";
    }

    @PostMapping("/completeDelivery")
    public String completeDelivery(){
        setCurrentDeliveryMan();

        currentDeliveryMan.completeOrder();
        deliveryManService.save(currentDeliveryMan);

        return "redirect:profile";
    }

    private void setCurrentDeliveryMan(){
        currentDeliveryMan = deliveryManService
                .getByEmail(SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName())
                .orElseThrow(() -> new RuntimeException("Delivery man not found"));
    }
}
