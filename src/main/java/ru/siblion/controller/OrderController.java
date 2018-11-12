package ru.siblion.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import ru.siblion.repository.order.OrderRepo;
import ru.siblion.tacos.Order;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private OrderRepo orderRepo;

    @Autowired
    public OrderController(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    /**
     * Здесь в модель клался новый объект new Order().
     * Но нужно работать с тем объектом, который уже существует в сессии.
     */
    @GetMapping("/current")
    public String orderForm(Model model) {
       // model.addAttribute("order", new Order());
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors,
                               SessionStatus sessionStatus) {
        if (errors.hasErrors()){
            System.out.println(errors);
            return "orderForm";
        }
        log.info("Order submitted: " + order);
        orderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}