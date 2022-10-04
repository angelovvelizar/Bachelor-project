package com.unwe.thesis.skylimit.service;

import com.unwe.thesis.skylimit.model.binding.OrderDetailBindingModel;
import com.unwe.thesis.skylimit.model.entity.OrderEntity;
import com.unwe.thesis.skylimit.repository.OrderRepository;
import com.unwe.thesis.skylimit.repository.ProductRepository;
import com.unwe.thesis.skylimit.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl {
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(ModelMapper modelMapper, OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    public void registerOrder(OrderDetailBindingModel orderDetailBindingModel) {
        OrderEntity order = this.modelMapper.map(orderDetailBindingModel, OrderEntity.class);
        order.setBuyer(this.userRepository.findByUsername(orderDetailBindingModel.getBuyer()).orElse(null));
        order.setProduct(this.productRepository.findById(orderDetailBindingModel.getProductId()).orElse(null));

        this.orderRepository.save(order);

    }
}
