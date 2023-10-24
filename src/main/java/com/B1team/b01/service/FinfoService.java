package com.B1team.b01.service;

import com.B1team.b01.dto.FacilityStatusDto;
import com.B1team.b01.dto.FinfoDto;
import com.B1team.b01.entity.Finfo;
import com.B1team.b01.entity.Worder;
import com.B1team.b01.repository.FinfoRepository;
import com.B1team.b01.repository.WorderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
//@Service
@Component
public class FinfoService {
    private final FinfoRepository finfoRepository;
    private final WorderRepository worderRepository;

    //설비명 목록 받기
    public List<String> getFacilityNameList() {
        List<Finfo> finfoList = finfoRepository.findAll();
        Set<String> set = new HashSet<>();
        for(Finfo info : finfoList) {
            set.add(info.getName());
        }
        return new ArrayList<>(set);
    }

    //설비 위치 목록 받기
    public List<String> getLocationList() {
        List<Finfo> finfoList = finfoRepository.findAll();
        Set<String> set = new HashSet<>();
        for(Finfo info : finfoList) {
            set.add(info.getLocation());
        }
        return new ArrayList<>(set);
    }

    //설비 현황 리스트 받기
    public List<FacilityStatusDto> getStatusList(String name, String state, String id) {
        List<FacilityStatusDto> list = new ArrayList<>();
        List<Finfo> entitys = finfoRepository.findFinfosByConditions(name, null, id);
        LocalDateTime now = LocalDateTime.now();

        for(Finfo entity : entitys) {
            FacilityStatusDto dto = FacilityStatusDto.of(entity);
            dto.setState(isRunning(dto.getId(), now));
            if(state == null || "".equals(state) || state.equals(dto.getState()))
                list.add(dto);
        }

        return list;
    }

    //현재 가동 중인 설비인지 확인
    public String isRunning(String facilityId, LocalDateTime currentTime) {
        List<Worder> list = worderRepository.findWordersByFacilityIdAndCurrentDateTime(facilityId, LocalDateTime.now());
        return list.isEmpty() ? "미가동" : "가동중";
    }

//    public List<FinfoDto> getSearchList(String name, String location) {
//        List<Finfo> list = finfoRepository.findFinfosByConditions(name, location, null);
//        return FinfoDto.of(list);
//    }
}
