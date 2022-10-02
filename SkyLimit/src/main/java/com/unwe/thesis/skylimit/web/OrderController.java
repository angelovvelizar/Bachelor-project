package com.unwe.thesis.skylimit.web;

import com.unwe.thesis.skylimit.model.binding.OrderDetailBindingModel;
import com.unwe.thesis.skylimit.model.view.ProductViewModel;
import com.unwe.thesis.skylimit.model.view.UserViewModel;
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

import java.security.Principal;

@Controller
public class OrderController {
    private final ProductServiceImpl productService;
    private final ModelMapper modelMapper;
    private final UserServiceImpl userService;

    public OrderController(ProductServiceImpl productService, ModelMapper modelMapper, UserServiceImpl userService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @GetMapping("/orders/{id}/checkout")
    public String checkout(@PathVariable Long id, Model model, Principal principal){
        ProductViewModel product = this.productService.findById(id);
        model.addAttribute("product", product);

        UserViewModel buyer = this.userService.findByUsername(principal.getName());
        model.addAttribute("buyer", buyer);

        //TODO: find the user with principal name and add his information in checkout form, do the same with the product
        return "checkout";
    }

    @PostMapping("/orders/{id}/checkout")
    public String checkoutPost (@PathVariable Long id, OrderDetailBindingModel orderDetailBindingModel,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal){
        //TODO: do validation and find a way to connect the user and the order and save it in database



        return "index";
    }






    @ModelAttribute
    public OrderDetailBindingModel orderDetailBindingModel(){
        return new OrderDetailBindingModel();
    }
}
