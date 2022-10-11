package com.unwe.thesis.skylimit.web;

import com.unwe.thesis.skylimit.model.binding.ProductAddBindingModel;
import com.unwe.thesis.skylimit.model.binding.ProductUpdateBindingModel;
import com.unwe.thesis.skylimit.model.entity.enums.CategoryEnum;
import com.unwe.thesis.skylimit.model.service.ProductAddServiceModel;
import com.unwe.thesis.skylimit.model.service.ProductUpdateServiceModel;
import com.unwe.thesis.skylimit.model.view.ProductViewModel;
import com.unwe.thesis.skylimit.service.CategoryServiceImpl;
import com.unwe.thesis.skylimit.service.ProductServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ProductController {
    private final ProductServiceImpl productService;
    private final ModelMapper modelMapper;
    private final CategoryServiceImpl categoryService;

    public ProductController(ProductServiceImpl productService, ModelMapper modelMapper, CategoryServiceImpl categoryService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
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
        return "redirect:/products/" + productAddBindingModel.getCategory().toString().toLowerCase();
    }

    @GetMapping("/products/water")
    public String watterAttractions(Model model){
        model.addAttribute("waterProducts", this.productService.getWaterAttractions());
        return "water-attractions";
    }

    @GetMapping("/products/air")
    public String airAttractions(Model model){
        model.addAttribute("airProducts", this.productService.getAirAttractions());
        return "air-attractions";
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

    @PreAuthorize("@productServiceImpl.isAdmin(#principal.name)")
    @GetMapping("/products/{id}/edit")
    public String editOffer(@PathVariable Long id, Model model, Principal principal){
        ProductViewModel productViewModel = this.productService.findById(id);
        ProductUpdateBindingModel productUpdateBindingModel = this.modelMapper
                .map(productViewModel, ProductUpdateBindingModel.class);

        model.addAttribute("productUpdateBindingModel", productUpdateBindingModel);
        model.addAttribute("category", CategoryEnum.values());

        return "update";
    }


    @GetMapping("/products/{id}/edit/errors")
    public String editProductErrors(@PathVariable Long id, Model model){
        model.addAttribute("category", CategoryEnum.values());

        return "update";
    }

    @PreAuthorize("@productServiceImpl.isAdmin(#principal.name)")
    @PatchMapping("/products/{id}/edit")
    public String editOffer(@PathVariable Long id, @Valid ProductUpdateBindingModel productUpdateBindingModel,
                            BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("productUpdateBindingModel", productUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productUpdateBindingModel", bindingResult);

            return "redirect:/products/" + id + "/edit/errors";
        }

        ProductUpdateServiceModel productUpdateServiceModel = this.modelMapper
                .map(productUpdateBindingModel, ProductUpdateServiceModel.class);
        this.productService.updateProduct(productUpdateServiceModel);
        productUpdateServiceModel.setId(id);

        return "redirect:/products/" + id + "/details";
    }

    @PreAuthorize("@productServiceImpl.isAdmin(#principal.name)")
    @DeleteMapping("/products/{id}")
    public String deleteProduct(@PathVariable Long id, Principal principal){
        ProductViewModel product = this.productService.findById(id);
        this.productService.deleteProduct(id);
        return "redirect:/products/" + product.getCategory().toString().toLowerCase();
    }

    @GetMapping("/products/{id}/json")
    public @ResponseBody ProductViewModel getProductToJson(@PathVariable Long id){
        return this.productService.findById(id);
    }
}
