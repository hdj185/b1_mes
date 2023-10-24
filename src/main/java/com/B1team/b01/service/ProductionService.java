package com.B1team.b01.service;

import com.B1team.b01.entity.Worder;
import com.B1team.b01.entity.Wplan;
import com.B1team.b01.repository.ProductRepository;
import com.B1team.b01.repository.ProductionRepository;
import com.B1team.b01.repository.ProductionSpecifications;
import com.B1team.b01.repository.WorderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductionService {
    @Autowired
    private ProductionRepository productionRepository;
    @Autowired
    private WorderRepository worderRepository;


    public List<Wplan> getAllWplan(){
        return productionRepository.findAll();
    }

    public List<Worder> getAllWorder(){return worderRepository.findAll();}

    public List<Wplan> search(String id, String orderId, String state, LocalDateTime min, LocalDateTime max){
        Specification<Wplan> specification = Specification.where(null);

        if(id != null){
            specification = specification.and(ProductionSpecifications.searchId(id));
        }
        if(orderId != null){
            specification = specification.and(ProductionSpecifications.searchOrderId(orderId));
        }
        if(state != null){
            specification = specification.and(ProductionSpecifications.searchState(state));
        }
        if(min !=null || max !=null){
            specification = specification.and(ProductionSpecifications.searchDate(min, max));
        }

        return productionRepository.findAll(specification);
    }
}
