package com.B1team.b01.controller;

import com.B1team.b01.entity.*;
import com.B1team.b01.repository.WorderSpecifications;
import com.B1team.b01.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/production/*")
public class ProductionController {
    @Autowired
    private ProductionService productionService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WperformService wperformService;
    @Autowired
    private MprocessService mprocessService;
    @Autowired
    private WorderService worderService;
    @Autowired
    private WplanService wplanService;


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

    @GetMapping("/production/production-plan")
    public String getWplanList(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

        wplanService.updateState(); //작업계획 업데이트 메소드

        List<Wplan> wlist = productionService.getAllWplan();
        model.addAttribute("wlist", wlist);

        List<Product> plist = productService.getAllProduct();
        model.addAttribute("plist",plist);

        return "production/production-plan";
    }

    @GetMapping("/production/production-performance")
    public String getWperformList(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        List<Wperform> wlist = wperformService.getAllWperform();
        model.addAttribute("wlist", wlist);

        List<Product> plist = productService.getAllProduct();
        model.addAttribute("plist",plist);

        return "production/production-performance";
    }

    @PostMapping("/plansearch")
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

    @PostMapping("/performsearch")
    public String wperformSearch(Model model,
                                 @RequestParam(required = false) String productId,
                                 @RequestParam(required = false) String orderId,
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
        if("".equals(productId)){
            productId=null;
        }
        if("".equals(orderId) ){
            orderId=null;
        }
        List<Wperform> searchlist = wperformService.search(productId,orderId,smin,smax);
        model.addAttribute("searchlist",searchlist);

        List<Product> plist = productService.getAllProduct();
        model.addAttribute("plist",plist);

        return "production/production-performance";
    }

    @PostMapping("/ordersearch")
    public String worderSearch(Model model,
                                 @RequestParam(required = false) String id,
                                 @RequestParam(required = false) String pid,
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
            id=null;
        }
        if("".equals(pid) ){
            pid=null;
        }
        List<Worder> searchlist = worderService.search(id,pid,smin,smax);
        model.addAttribute("searchlist",searchlist);
        System.out.println(searchlist);

        List<Product> plist = productService.getAllProduct();
        model.addAttribute("plist",plist);

        List<Mprocess> mlist = mprocessService.getAllProcess();
        model.addAttribute("mlist",mlist);

        return "production/production-order";
    }
}
