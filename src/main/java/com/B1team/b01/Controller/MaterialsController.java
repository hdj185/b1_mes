package com.B1team.b01.Controller;

import com.B1team.b01.dto.*;
import com.B1team.b01.repository.CustomerRepository;
import com.B1team.b01.repository.MaterialsRepository;
import com.B1team.b01.repository.ProductRepository;
import com.B1team.b01.repository.StockRepository;
import com.B1team.b01.service.MaterialsService;
import com.B1team.b01.service.PinoutService;
import com.B1team.b01.service.PorderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/materials")
public class MaterialsController {
    private final PorderService porderService;
    private final PinoutService pinoutService;
    private final MaterialsRepository materialsRepository;
    private final StockRepository stockRepository;
    private final CustomerRepository customerRepository;
    //발주 현황
    @GetMapping("/porder-status")
    public String placeAnOrderStatus(String startDate,
                                     String endDate,
                                     String mtrName,
                                     String customerName,
                                     String state,
                                     String startArrivalDate,
                                     String endArrivalDate,
                                     Model model) {
        //거래처 리스트
        List<CustomerDto> customerDtoList = CustomerDto.of(customerRepository.findBySort("발주처"));
        model.addAttribute("customerList", customerDtoList);

        //품목 리스트
        List<MaterialsDto> materialsDtoList = MaterialsDto.of(materialsRepository.findAll());
        model.addAttribute("materialList", materialsDtoList);

        //기본 발주 조회 리스트
        List<PorderOutputDto> porderList = porderService.getPorderList(startDate, endDate, mtrName, customerName, state, startArrivalDate, endArrivalDate);
        model.addAttribute("porderList", porderList);

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("mtrName", mtrName);
        model.addAttribute("customerName", customerName);
        model.addAttribute("state", state);
        model.addAttribute("startArrivalDate", startArrivalDate);
        model.addAttribute("endArrivalDate", endArrivalDate);

        return "/materials/porder-status";
    }

    //자재 입출 현황
    @GetMapping("/material-inoutput")
    public String materialInOutPut(Model model,
                                   String sort,
                                   String startDate,
                                   String endDate,
                                   String mtrName) {
        //거래처 리스트
        List<CustomerDto> customerDtoList = CustomerDto.of(customerRepository.findBySort("발주처"));
        model.addAttribute("customerList", customerDtoList);

        //품목 리스트
        List<MaterialsDto> materialsDtoList = MaterialsDto.of(materialsRepository.findAll());
        model.addAttribute("materialList", materialsDtoList);

        //자재 입출 현황 리스트
        List<PinoutOutputDto> pinoutOutputDtoList = pinoutService.getPinoutList(sort, startDate, endDate, mtrName);
        model.addAttribute("pinoutList", pinoutOutputDtoList);

        System.out.println("컨트롤러 리스트:" + pinoutOutputDtoList);

        model.addAttribute("sort", sort);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("mtrName", mtrName);
        return "/materials/material-inoutput";
    }

    //자재 재고 현황
    @GetMapping("/material-inventory")
    public String materialInventory(Model model, String mtrName) {
        List<MaterialStockDto> list = stockRepository.findMaterialStock(mtrName);
        model.addAttribute("list", list);
        model.addAttribute("mtrName", mtrName);
        return "/materials/material-inventory";
    }
}
