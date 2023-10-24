package com.B1team.b01.controller;

import com.B1team.b01.entity.Customer;
import com.B1team.b01.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer/*")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    public static final String rsort = "수주처";
    public static final String psort = "발주처";

    @GetMapping("/customer/rorder-customer")
    public String rorderCustomer(Model model) {
        model.addAttribute("clist",customerService.getPorderList(rsort));

        return "/customer/rorder-customer";
    }

    @GetMapping("/customer/porder-customer")
    public String porderCustomer(Model model) {
        model.addAttribute("clist",customerService.getPorderList(psort));

        return "/customer/porder-customer";
    }

    @PostMapping("/pordersearch")
    public String porderSearch(Model model,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String id,
                               @RequestParam(required = false) String businessNumber){
        if("".equals(name)){
            name=null;
        }
        if("".equals(id)){
            id=null;
        }
        if("".equals(businessNumber)){
            businessNumber=null;
        }

        System.out.println(name);
        System.out.println(id);
        System.out.println(businessNumber);

        List<Customer> slist = customerService.search(name,id,businessNumber,psort);
        System.out.println(slist);
        model.addAttribute("slist",slist);

        return "/customer/porder-customer";
    }

    @PostMapping("/rordersearch")
    public String rorderSearch(Model model,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String id,
                               @RequestParam(required = false) String businessNumber){
        if("".equals(name)){
            name=null;
        }
        if("".equals(id)){
            id=null;
        }
        if("".equals(businessNumber)){
            businessNumber=null;
        }

        System.out.println(name);
        System.out.println(id);
        System.out.println(businessNumber);

        List<Customer> slist = customerService.search(name,id,businessNumber,rsort);
        System.out.println(slist);
        model.addAttribute("slist",slist);

        return "/customer/rorder-customer";
    }

    @PostMapping("/pcustomer-insert")
    public String pcustomerInsert(@RequestParam String businessNumber,
                                  @RequestParam String name,
                                  @RequestParam String tel,
                                  @RequestParam String fax,
                                  @RequestParam String sample6_address,
                                  @RequestParam String sample6_detailAddress){
        String address = sample6_address + " " +  sample6_detailAddress;
        Customer customer = new Customer();
        customer.setId("PCU" + customerService.getNextCustomerSeq());
        customer.setSort(psort);
        customer.setName(name);
        customer.setAddress(address);
        customer.setTel(tel);
        customer.setBusinessNumber(businessNumber);
        customer.setFax(fax);

        customerService.insertCustomer(customer);

        return "redirect:/customer/porder-customer";
    }

    @PostMapping("/rcustomer-insert")
    public String rcustomerInsert(@RequestParam String businessNumber,
                                  @RequestParam String name,
                                  @RequestParam String tel,
                                  @RequestParam String fax,
                                  @RequestParam String sample6_address,
                                  @RequestParam String sample6_detailAddress){
        String address = sample6_address + " " + sample6_detailAddress;
        Customer customer = new Customer();
        customer.setId("RCU" + customerService.getNextCustomerSeq());
        customer.setSort(rsort);
        customer.setName(name);
        customer.setAddress(address);
        customer.setTel(tel);
        customer.setBusinessNumber(businessNumber);
        customer.setFax(fax);

        customerService.insertCustomer(customer);
        return "redirect:/customer/rorder-customer";
    }
}
