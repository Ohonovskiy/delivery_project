package application.pet.delivery.controllers.deliveryMan;

import application.pet.delivery.entities.DeliveryMan;
import application.pet.delivery.entities.Order;
import application.pet.delivery.enums.order.Status;
import application.pet.delivery.services.DeliveryManService;
import application.pet.delivery.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/delivery")
@PreAuthorize("hasAuthority('delivery_man')")
public class DeliveryController {
    private final OrderService orderService;
    private final DeliveryManService deliveryManService;
    private DeliveryMan currentDeliveryMan;

    @Autowired
    public DeliveryController(OrderService orderService, DeliveryManService deliveryManService) {
        this.orderService = orderService;
        this.deliveryManService = deliveryManService;
    }

    @GetMapping
    public String index(){
        return "delivery/index";
    }

    @GetMapping("/deliveryman")
    public String profile(){
        return "delivery/profile";
    }

    @GetMapping("/availableOrders")
    public String availableOrders(Model model){
        setCurrentDeliveryMan();

        model.addAttribute("orders", orderService.getAllUnsignedOrders());
//        model.addAttribute("hasOrder", currentDeliveryMan.hasOrder());
        model.addAttribute("hasOrder", false);

        return "delivery/orders";
    }

    @PostMapping("/takeOrder")
    public String takeOrder(@RequestParam("orderId") Long orderId){
        setCurrentDeliveryMan();

        Optional<Order> optionalOrder = orderService.getById(orderId);

        if(optionalOrder.isPresent()){
//            if(optionalOrder.get().getDeliveryMan() != null)
//                return "delivery/orderIsTaken";

            currentDeliveryMan.setOrder(optionalOrder.get());

            System.out.println(currentDeliveryMan.getOrder().getId());

            orderService.save(optionalOrder.get());
            deliveryManService.save(currentDeliveryMan);
        }

        return "redirect:availableOrders";
    }

    @PostMapping("/cancelDelivery")
    public String cancelOrder(){
        setCurrentDeliveryMan();

//        currentDeliveryMan.getOrder().removeDeliveryman();

        currentDeliveryMan.removeOrder();

        return "redirect:deliveryman";
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