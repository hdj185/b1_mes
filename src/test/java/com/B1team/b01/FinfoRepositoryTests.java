package com.B1team.b01;

import com.B1team.b01.dto.FinfoDto;
import com.B1team.b01.entity.Finfo;
import com.B1team.b01.repository.FinfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class FinfoRepositoryTests {
    @Autowired private FinfoRepository finfoRepository;

    @Test
    void 레포지토리테스트() {
        List<FinfoDto> list = FinfoDto.of(finfoRepository.findFinfosByConditions(null, null, null));
        for(int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));
    }
}
