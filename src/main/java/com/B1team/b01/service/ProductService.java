package com.B1team.b01.service;

import com.B1team.b01.entity.Product;
import com.B1team.b01.repository.MaterialsRepository;
import com.B1team.b01.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MaterialsRepository materialsRepositoryRepository;


    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    @Transactional
    public void insertProduct(Product product) {
//        Product product = pdto.toEntity();
        productRepository.save(product);
    }

    public List<Product> ProductList(String productName, String productId, String productSort) {
        List<Object[]> productList = productRepository.getProductList(productName, productId, productSort);
        List<Product> products = new ArrayList<>();

        for (Object[] obj : productList) {
            Product product = new Product();
            product.setName((String) obj[0]);
            product.setId((String) obj[1]);
            product.setPrice((Long) obj[2]);
            product.setSort((String) obj[3]);
            product.setLocation((String) obj[4]);

            products.add(product);
        }

        return products;
    }
}
