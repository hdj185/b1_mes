package com.B1team.b01.controller;

import com.B1team.b01.dto.CustomerDto;
import com.B1team.b01.dto.ProductDto;
import com.B1team.b01.dto.ShipmentDto;
import com.B1team.b01.repository.CustomerRepository;
import com.B1team.b01.repository.ProductRepository;
import com.B1team.b01.service.FinprodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@Transactional
@RequiredArgsConstructor
@RequestMapping("/shipment")
public class ShipmentController {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final FinprodService finprodService;
    @GetMapping("/shipment")
    public String shipment(String customerName,
                           String productName,
                           String orderId,
                           String startDate,
                           String endDate,
                           Model model) {
        //거래처 리스트
        List<CustomerDto> customerDtoList = CustomerDto.of(customerRepository.findBySort("수주처"));
        model.addAttribute("customerList", customerDtoList);

        //품목 리스트
        List<ProductDto> productDtoList = ProductDto.of(productRepository.findAll());
        model.addAttribute("productList", productDtoList);

        //출하 리스트
        List<ShipmentDto> shipmentDtoList = finprodService.getShipmentList(customerName, productName, orderId, startDate, endDate);
        model.addAttribute("shipmentList", shipmentDtoList);

        model.addAttribute("productName", productName);
        model.addAttribute("customerName", customerName);
        model.addAttribute("orderId", orderId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);


        return "shipment/shipment";
    }
}
