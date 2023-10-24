package com.B1team.b01;

import com.B1team.b01.entity.Materials;
import com.B1team.b01.repository.MaterialsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
public class MaterialsRepositoryTests {
    @Autowired private MaterialsRepository materialsRepository;

    @Test
    void 레포지토리테스트() {
        //양배추 불러오기
        Optional<Materials> optional = materialsRepository.findById("MTR27");

        //비었는지 테스트
        if(optional.isPresent()) {
            Materials materials = optional.get();
            System.out.println(materials.toString());
        } else {
            System.out.println("비었습니다.");
        }
    }
}
