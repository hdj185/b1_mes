package com.B1team.b01;

import com.B1team.b01.dto.NeedEaDto;
import com.B1team.b01.service.MprocessService;
import com.B1team.b01.dto.WorderDto;
import com.B1team.b01.entity.Finfo;
import com.B1team.b01.entity.Mprocess;
import com.B1team.b01.entity.Routing;
import com.B1team.b01.repository.FinfoRepository;
import com.B1team.b01.repository.MprocessRepository;
import com.B1team.b01.repository.RoutingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MprocessRepositoryTests {
    @Autowired private MprocessRepository mprocessRepository;
    @Autowired private RoutingRepository routingRepository;
    @Autowired private FinfoRepository finfoRepository;
    @Autowired private MprocessService mprocessService;

    @Test
    void 납기일예측테스트() {
        LocalDateTime deadline = mprocessService.caluculateDeadline(LocalDateTime.now(), "p21", 10);
        System.out.println("deadline=" + deadline);
    }

    @Test
    void 공정테스트() {
        //모든 공정을 불러오기
        List<String> list = new ArrayList<>(); //processId list
        for(int i = 0; i < 14; i++) {
            list.add("A" + (i + 15));
        }

        List<Mprocess> mprocesses = mprocessRepository.findAllById(list);

        String msg = "---------------------------------------\n";
        for(int i = 0; i < mprocesses.size(); i++) {
            msg += mprocesses.get(i).toString() + "\n";
        }
        msg += "---------------------------------------\n";
        System.out.println(msg);
    }

    @Test
    void 라우팅_공정_테스트() {
        String msg = "---------------------------------------\n";

        //라우팅에서 양배추즙 공정 흐름 얻기
        List<Routing> routings = routingRepository.findByProductIdOrderByOrder("p21");
        List<String> list = new ArrayList<>();
        for(int i = 0; i < routings.size(); i++) {
            list.add(routings.get(i).getProcessId());
            msg += routings.get(i).toString() + "\n";
        }

        msg += "---------------------------------------\n";

        //라우팅 정보 기반으로 공정 정보 받기
        List<Mprocess> mprocesses = mprocessRepository.findAllById(list);
        for(int i = 0; i < mprocesses.size(); i++)
            msg += mprocesses.get(i).toString() + "\n";

        msg += "---------------------------------------\n";

        //////////////////////////////공정별 처리//////////////////////////////
        List<WorderDto> worderDtos = new ArrayList<>(); //작업지시 dto를 list형태로

        //기준 시간 - 시작 시간은 자재 준비 일자
        LocalDateTime startDate = LocalDateTime.of(2023, 5, 24, 10, 0);
        LocalDateTime finishDate = LocalDateTime.of(2023, 5, 24, 10, 0);

        int cnt = 1; //order 판정 기준

        //같은 공정에서 설비 개수 세기
        long lastOrder = routings.get(routings.size() - 1).getOrder();
        int[] orderCnt = new int[(int)lastOrder];
        for(int i = 0 ; i < routings.size(); i++) {
            long currentOrderCnt = routings.get(i).getOrder() - 1;
            orderCnt[(int)currentOrderCnt]++;
        }
        //공정별 처리
        for(int i = 0; i < mprocesses.size(); i++) {
            WorderDto dto = new WorderDto();        //이번 공정의 작업지시dto
            Mprocess process = mprocesses.get(i);   //이번 공정 정보

            //currentFacilityCnt : 같은 공정의 설비 개수(예: 혼합+살균 탱크 2개)
            long currentOrderCnt = routings.get(i).getOrder() - 1;
            int currentFacilityCnt = orderCnt[(int)currentOrderCnt];

            //임시로 필요 용량 넣기
            double capacity = 0;
            switch(process.getName()) {
                case "전처리": capacity = 1000 / currentFacilityCnt; break;  //양배추 1000kg / 설비 개수
                case "추출": case "혼합+살균": capacity = 1600 / currentFacilityCnt; break; //양배추 추출액 1600kg / 설비 개수
                case "충진(파우치)": case "검사": capacity = 20010 / currentFacilityCnt;   //양배추즙 파우치 20010개 / 설비 개수
                case "포장" : capacity = 667 / currentFacilityCnt;   //수주받은 양배추즙 667Box / 설비 개수
            }

            //같은 공정 이미 계산했는지 확인
            if(routings.get(i).getOrder() != cnt++) {
                cnt--;
//                msg += process.getName() + " 시작 시간 : " + startDate + ", 종료 시간 : " + finishDate + ", order = " + routings.get(i).getOrder() + "\n";
            } else {
                //이전 작업 완료 시간을 현재 공정의 작업 시작 시작으로 설정
                startDate = finishDate;

                //작업 시작 시간 - 점심시간 & 퇴근 시간 고려
                if(startDate.getHour() >= 12 && startDate.getHour() < 13)
                    startDate = startDate.withHour(13).withMinute(0).withSecond(0).withNano(0);
                else if(startDate.getHour() < 9)
                    startDate = startDate.withHour(9).withMinute(0).withSecond(0).withNano(0);
                else if(startDate.getHour() >= 18)
                    startDate = startDate.plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0);

                //작업 완료 시간 세팅
                finishDate = startDate;

                if(process.getName().equals("원료계량")) {
                    finishDate = finishDate.plusMinutes(process.getLeadtime() + process.getProdtime());
                } else if(process.getName().equals("혼합+살균")) {
                    double min = (process.getLeadtime() + process.getProdtime()) * (capacity / process.getCapa() + (capacity % process.getCapa() > 0 ? 1 : 0));
                    long nanos = (long) (min * 60 * 1000000000);
                    finishDate = finishDate.plusNanos(nanos);
                } else if(process.getName().equals("식힘")) {
                    finishDate = finishDate.plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0);
                } else {
                    double min = process.getLeadtime() + process.getTimeUnit() * capacity / process.getCapa();
                    long nanos = (long) (min * 60 * 1000000000);
                    finishDate = finishDate.plusNanos(nanos);
    //                System.out.println(process.getName() + "의 process.getTimeUnit()=" + process.getTimeUnit());
    //                System.out.println(process.getName() + "의 capacity=" + capacity);
    //                System.out.println(process.getName() + "의 process.getCapa()=" + process.getCapa());
    //                System.out.println(process.getName() + "의 min=" + min);
    //                System.out.println(process.getName() + "의 nanos=" + nanos);
                }
            }

            dto.setProcessId(process.getId());  //공정 고유번호
            dto.setFacilityId(process.getFacilityId()); //설비정보 고유번호
            dto.setStartDate(startDate);        //작업 시작 일자
            dto.setFinishDate(finishDate);      //작업 완료 일자

            worderDtos.add(dto);

            msg += process.getName() + " 시작 시간 : " + startDate + ", 종료 시간 : " + finishDate + ", order = " + routings.get(i).getOrder() + ", 용량 = " + capacity + "\n";
        }

        System.out.println(msg);

        for(int i = 0; i < worderDtos.size(); i++)
            System.out.println(worderDtos.get(i).toString());
    }

    @Test
    void 작업시간계산테스트() {
        LocalDateTime materialReadyDate = LocalDateTime.of(2023, 5, 24, 10, 0);
        List<WorderDto> list = mprocessService.calculateWorderDate(materialReadyDate, "p21", 2000);
        for(int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));
    }

    @Test
    void 설비정보받기() {
        //라우팅에서 공정 흐름 얻기
        List<Routing> routings = routingRepository.findByProductIdOrderByOrder("p21");

        //공정Id만 list로
        List<String> producctIdlist = new ArrayList<>();    //공정Id 리스트
        for(int i = 0; i < routings.size(); i++) {
            producctIdlist.add(routings.get(i).getProcessId());
        }

        //라우팅에 따른 공정 리스트 받기
        List<Mprocess> mprocesses = mprocessRepository.findAllById(producctIdlist);

        //설비명만 list로 바꾸기 + 같은 공정의 설비 개수 세기
        List<String> facilityNameList = new ArrayList<>();  //설비명 리스트
        for(int i = 0; i < mprocesses.size(); i++) {
            facilityNameList.add(mprocesses.get(i).getFacilityId());
        }

        //공정에 따른 설비 리스트 받기
        List<Finfo> facilities = finfoRepository.findByNameIn(facilityNameList);

        //같은 공정에서 설비 개수 세기
        int[] facilityCnt = new int[mprocesses.size()]; //같은 공정의 설비 개수
        int processCnt = -1;
        String tmpStr = "";
        for(int i = 0 ; i < facilities.size(); i++) {
            if(tmpStr.equals(facilities.get(i).getName())) {
                facilityCnt[processCnt]++;
                System.out.println("facilityCnt[" + processCnt + "]=" + facilityCnt[processCnt] + ", facilitieName" + facilities.get(i).getName());
            } else {
                facilityCnt[++processCnt]++;
                tmpStr = facilities.get(i).getName();
                System.out.println("facilityCnt[" + (processCnt) + "]=" + facilityCnt[processCnt] + ", facilitieName" + facilities.get(i).getName());
            }
        }

        for(int i = 0; i < mprocesses.size(); i++) {
            System.out.println("설비 개수=" + facilityCnt[i] + " " + mprocesses.get(i).toString());
        }
    }

    //수작업 공정 시간 처리(점심&퇴근 시간 고려)
    @Test
    void 점심퇴근고려() {
        LocalDateTime start = LocalDateTime.of(2023, 5, 23, 13, 0, 0, 0);
        LocalDateTime finish = LocalDateTime.of(2023, 5, 25, 0, 30, 0, 0);
        LocalDateTime time = mprocessService.calculateAdjustedFinishTime(start, finish);
        System.out.println("시작시간 = " + start);
        System.out.println("완료시간 = " + finish);
        System.out.println("result = " + time); //예상 result = 2023-05-25 13:20
    }
}
