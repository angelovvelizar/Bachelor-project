package com.unwe.thesis.skylimit.web;

import com.unwe.thesis.skylimit.model.binding.OrderDetailBindingModel;
import com.unwe.thesis.skylimit.model.view.ProductViewModel;
import com.unwe.thesis.skylimit.service.ProductServiceImpl;
import com.unwe.thesis.skylimit.service.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

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

        //TODO: find the user with principal name and add his information in checkout form, do the same with the product
        return "checkout";
    }






    @ModelAttribute
    public OrderDetailBindingModel orderDetailBindingModel(){
        return new OrderDetailBindingModel();
    }
}
