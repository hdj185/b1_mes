package com.B1team.b01.Controller;

import com.B1team.b01.dto.FacilityStatusDto;
import com.B1team.b01.dto.FinfoDto;
import com.B1team.b01.entity.Finfo;
import com.B1team.b01.repository.FinfoRepository;
import com.B1team.b01.service.FinfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@Transactional
@RequiredArgsConstructor
@RequestMapping("/facility")
public class FinfoController {
    private final FinfoRepository finfoRepository;
    private final FinfoService finfoService;
    //설비 현황
    @GetMapping("/facility-status")
    public String facilityStatus(Model model,
                                 String fname,
                                 String state,
                                 String id){
        //설비명 목록
        List<String> nameList = finfoService.getFacilityNameList();
        model.addAttribute("nameList", nameList);

        //설비 현황 리스트
        List<FacilityStatusDto> statusList = finfoService.getStatusList(fname, state, id);
        model.addAttribute("statusList", statusList);

        model.addAttribute("fname", fname);
        model.addAttribute("state", state);
        model.addAttribute("id", id);
        return "facility/facility-status";
    }

    //설비 정보
    @GetMapping("/facility-information")
    public String facilityInformation(Model model,
                                      String fname,
                                      String flocation) {
        //설비명 목록
        List<String> nameList = finfoService.getFacilityNameList();
        model.addAttribute("nameList", nameList);

        //설비 위치 목록
        List<String> locationList = finfoService.getLocationList();
        model.addAttribute("locationList", locationList);

        //설비 정보 리스트
        List<Finfo> entitys = finfoRepository.findFinfosByConditions(fname, flocation, null);
        List<FinfoDto> finfoList = FinfoDto.of(entitys);
        model.addAttribute("finfoList", finfoList);

        model.addAttribute("fname", fname);
        model.addAttribute("flocation", flocation);
        return "facility/facility-information";
    }
}
