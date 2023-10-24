package com.B1team.b01.service;

import com.B1team.b01.dto.PorderOutputDto;
import com.B1team.b01.entity.Porder;
import com.B1team.b01.entity.Stock;
import com.B1team.b01.repository.PorderDetailRepository;
import com.B1team.b01.repository.PorderRepository;
import com.B1team.b01.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PorderService {
    private final PorderRepository porderRepository;
    private final PorderDetailRepository porderDetailRepository;
    private final StockRepository stockRepository;



    //발주 현황 - 검색 시 Entity - Dto 변환
    public List<PorderOutputDto> getPorderList(String start,
                                               String end,
                                               String mtrName,
                                               String customerName,
                                               String state,
                                               String startArriva,
                                               String endArrival) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDate = start == null || "".equals(start)? null : LocalDate.parse(start, formatter).atStartOfDay();
        LocalDateTime endDate = end == null || "".equals(end) ? null : LocalDate.parse(end, formatter).atTime(23, 59, 59);
        LocalDateTime startArrivalDate = start == null || "".equals(startArriva)? null : LocalDate.parse(startArriva, formatter).atStartOfDay();
        LocalDateTime endArrivalDate = end == null || "".equals(endArrival) ? null : LocalDate.parse(endArrival, formatter).atTime(23, 59, 59);

        List<Porder> porders = porderRepository.findPordersByConditons(startDate, endDate, mtrName, customerName, state, LocalDateTime.now(), startArrivalDate, endArrivalDate);
        return PorderOutputDto.of(porders);
    }

}
