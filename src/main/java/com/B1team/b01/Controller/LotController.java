package com.B1team.b01.controller;

import com.B1team.b01.dto.BomListDto;
import com.B1team.b01.entity.*;
import com.B1team.b01.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@Transactional
@RequiredArgsConstructor
@RequestMapping("/lot/*")
public class LotController {

    @Autowired
    private LotService lotService;
    @Autowired
    private ProductionService productionService;
    @Autowired
    private MprocessService mprocessService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WperformService wperformService;
    @Autowired
    private WplanService wplanService;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

/*
    //wplan 임시용
    @GetMapping("/production/production-plan")
    public String getWplanList(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

        model.addAttribute("updateState",wplanService.updateState()); //(*) 작업계획 업데이트 메소드

        List<Wplan> wlist = productionService.getAllWplan();
        model.addAttribute("wlist", wlist);

        List<Product> plist = productService.getAllProduct();
        model.addAttribute("plist",plist);

        return "/production/production-plan";
    }
    //wplan 임시용
    @PostMapping("/production/plansearch")
    public String wplanSearch(Model model,
                              @RequestParam(required = false) String id,
                              @RequestParam(required = false) String orderId,
                              @RequestParam(required = false) String state,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate min,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate max){
        LocalDateTime smin = null;
        LocalDateTime smax = null;
        if(min != null){
            smin= LocalDateTime.of(min, LocalTime.MIN);
        }

        if(max != null){
            smax = LocalDateTime.of(max, LocalTime.MAX);
        }

        if("".equals(orderId)){
            orderId=null;
        }
        if("".equals(state) ){
            state=null;
        }
        if("".equals(id)){
            id=null;
        }

        System.out.println("----------------------------------------");
        System.out.println(id);
        System.out.println(orderId);
        System.out.println(state);
        System.out.println(min);
        System.out.println(max);

        List<Wplan> searchlist = productionService.search(id,orderId,state, smin, smax);
        model.addAttribute("searchlist",searchlist);

        System.out.println(searchlist);
        List<Product> plist = productService.getAllProduct();
        model.addAttribute("plist",plist);

        return "production/production-plan";
    }
    //worder 임시용
    @GetMapping("/production/production-order")
    public String getOrderList(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        List<Worder> wlist = productionService.getAllWorder();
        model.addAttribute("wlist", wlist);

        List<Product> plist = productService.getAllProduct();
        model.addAttribute("plist",plist);

        List<Mprocess> mlist = mprocessService.getAllProcess();
        model.addAttribute("mlist",mlist);

        return "production/production-order";
    }
    //wperform 임시용
    @GetMapping("/production/production-performance")
    public String getWperformList(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        List<Wperform> wlist = wperformService.getAllWperform();
        model.addAttribute("wlist", wlist);

        List<Product> plist = productService.getAllProduct();
        model.addAttribute("plist",plist);

        return "production/production-performance";
    }

*/


    //LOT리스트 표출
    @GetMapping("/lot")
    public String lotList(Model model) {
        model.addAttribute("lotList", lotService.getLotList());
        return "/lot/lot";
    }

    //검색
    @GetMapping("/lotsearch")
    public String lotSearch(Model model,
                            @RequestParam(required = false) String id,
                            @RequestParam(required = false) String processId,
                            @RequestParam(required = false) String productId,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate min,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate max){
        LocalDateTime smin = null;
        LocalDateTime smax = null;
        if(min != null){
            smin= LocalDateTime.of(min, LocalTime.MIN);
        }

        if(max != null){
            smax = LocalDateTime.of(max, LocalTime.MAX);
        }

        if("".equals(id)){
            id = null;
        }
        if("".equals(processId) ){
            processId = null;
        }
        if("".equals(productId)){
            productId = null;
        }

        System.out.println("----------------------------------------");
        System.out.println(id);
        System.out.println(processId);
        System.out.println(productId);
        System.out.println(min);
        System.out.println(max);

        List<LOT> searchList = lotService.search(id,processId,productId,smin,smax);
        System.out.println("검색리스트" + searchList);
        model.addAttribute("searchList",searchList);


        return "/lot/lot";
    }


}