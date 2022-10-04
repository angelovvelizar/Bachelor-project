package com.unwe.thesis.skylimit.web;

import com.unwe.thesis.skylimit.model.binding.OrderDetailBindingModel;
import com.unwe.thesis.skylimit.model.view.ProductViewModel;
import com.unwe.thesis.skylimit.model.view.UserViewModel;
import com.unwe.thesis.skylimit.service.OrderServiceImpl;
import com.unwe.thesis.skylimit.service.ProductServiceImpl;
import com.unwe.thesis.skylimit.service.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class OrderController {
    private final ProductServiceImpl productService;
    private final ModelMapper modelMapper;
    private final UserServiceImpl userService;
    private final OrderServiceImpl orderService;

    public OrderController(ProductServiceImpl productService, ModelMapper modelMapper, UserServiceImpl userService, OrderServiceImpl orderService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.orderService = orderService;
    }


    @GetMapping("/orders/{id}/checkout")
    public String checkout(@PathVariable Long id, Model model, Principal principal) {
        ProductViewModel product = this.productService.findById(id);
        model.addAttribute("product", product);

        UserViewModel buyer = this.userService.findByUsername(principal.getName());
        model.addAttribute("buyer", buyer);

        return "checkout";
    }

    @PostMapping("/orders/{id}/checkout")
    public String checkoutPost (@PathVariable Long id, @Valid OrderDetailBindingModel orderDetailBindingModel,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("orderDetailBindingModel", orderDetailBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderDetailBindingModel", bindingResult);

            return "redirect:/orders/" + id + "/checkout";
        }

        orderDetailBindingModel.setOrderDate(LocalDateTime.now());
        orderDetailBindingModel.setAmount(this.productService.findById(id).getPrice());
        orderDetailBindingModel.setBuyer(principal.getName());
        orderDetailBindingModel.setProductId(id);

        this.orderService.registerOrder(orderDetailBindingModel);


        return "thank-you";
    }


    @ModelAttribute
    public OrderDetailBindingModel orderDetailBindingModel() {
        return new OrderDetailBindingModel();
    }
}
