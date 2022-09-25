package com.unwe.thesis.skylimit.web;

import com.unwe.thesis.skylimit.model.binding.ProductAddBindingModel;
import com.unwe.thesis.skylimit.model.service.ProductAddServiceModel;
import com.unwe.thesis.skylimit.model.view.ProductViewModel;
import com.unwe.thesis.skylimit.service.ProductServiceImpl;
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

@Controller
public class ProductController {
    private final ProductServiceImpl productService;
    private final ModelMapper modelMapper;

    public ProductController(ProductServiceImpl productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/products/add")
    public String addProduct(){

        return "product-add";
    }

    @ModelAttribute
    public ProductAddBindingModel productAddBindingModel(){
        return new ProductAddBindingModel();
    }

    @PostMapping("/products/add")
    public String addProductPost(@Valid ProductAddBindingModel productAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors() || this.productService.existsByName(productAddBindingModel.getName())){
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);

            return "redirect:/products/add";
        }

        this.productService.addProduct(this.modelMapper.map(productAddBindingModel, ProductAddServiceModel.class));
        return "index";
    }

    @GetMapping("/products/water")
    public String watterAttractions(Model model){
        model.addAttribute("waterProducts", this.productService.getWaterAttractions());
        return "water-attractions";
    }

    @GetMapping("/products/air")
    public String airAttractions(Model model){
        model.addAttribute("skyProducts", this.productService.getAirAttractions());
        return "sky-attractions";
    }

    @GetMapping("/products/city")
    public String cityAttractions(Model model){
        model.addAttribute("cityProducts", this.productService.getCityAttractions());
        return "city-attractions";
    }

    @GetMapping("/products/land")
    public String landAttractions(Model model){
        model.addAttribute("landProducts", this.productService.getLandAttractions());
        return "land-attractions";
    }

    @GetMapping("/products/{id}/details")
    public String details(@PathVariable Long id, Model model){
        ProductViewModel product =  this.productService.findById(id);
        model.addAttribute("product", product);
        return "details";
    }
}
