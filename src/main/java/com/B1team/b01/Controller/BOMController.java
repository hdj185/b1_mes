package com.B1team.b01.controller;

import com.B1team.b01.dto.BomListDto;
import com.B1team.b01.entity.Materials;
import com.B1team.b01.entity.Product;
import com.B1team.b01.service.BomService;
import com.B1team.b01.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@Controller
@Transactional
@RequiredArgsConstructor
@RequestMapping("/item/*")
public class BOMController {

    @Autowired
    private BomService bomService;

    @Autowired
    private ProductService productService;

    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @GetMapping("/bom")
    public String BOM(Model model
            , @RequestParam(required = false) String productName
            , @RequestParam(required = false) String mtrName) {

        List<BomListDto> bomList = bomService.BomList(productName, mtrName);


        List<Product> Product = bomService.getProduct();
        List<Materials> Materials = bomService.getMaterials();

        model.addAttribute("bomList",bomList);
        model.addAttribute("Product",Product);
        model.addAttribute("Materials",Materials);

        return "/item/bom";
    }


    @Transactional
    public String generateId(String head, String seqName) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        BigDecimal sequenceValue = (BigDecimal) entityManager.createNativeQuery("SELECT " + seqName + ".NEXTVAL FROM dual").getSingleResult();
        String id = head + sequenceValue;
        return id;
    }

    @PostMapping("/register")
    public String insertProduct(Product product) {

        product.setId(generateId("P", "product_seq"));
        productService.insertProduct(product);

        return "redirect:/item/product";
    }
}