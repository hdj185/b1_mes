package com.B1team.b01.service;

import com.B1team.b01.dto.NeedEaDto;
import com.B1team.b01.dto.NeedOrderDto;
import com.B1team.b01.dto.WorderDto;
import com.B1team.b01.dto.WplanDto;
import com.B1team.b01.entity.Finfo;
import com.B1team.b01.entity.Mprocess;
import com.B1team.b01.entity.Routing;
import com.B1team.b01.repository.FinfoRepository;
import com.B1team.b01.repository.MprocessRepository;
import com.B1team.b01.repository.RoutingRepository;
import com.B1team.b01.repository.WplanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
//@Service
@Component
public class MprocessService {
    private final MprocessRepository mprocessRepository;
    private final RoutingRepository routingRepository;
    private final FinfoRepository finfoRepository;
    private final MaterialsService materialsService;
    private final BomService bomService;
    private final WplanRepository wplanRepository;

    //시뮬레이션 - 납기일 예측(계산)
    public LocalDateTime caluculateDeadline(LocalDateTime orderDate, String productId, long orderCnt) {
        //orderDate : 수주 날짜(시작 날짜), productId : 품목id, orderCnt: 수주량(box)

        //모든 원자재 준비 완료 시간
        LocalDateTime materialReadyDate = orderDate;

        //필요 발주 목록 계산
        List<NeedOrderDto> needOrderList = bomService.calcBom(productId, bomService.boxToAmount(productId, orderCnt));

        //원자재 재고 충분
        if(!needOrderList.isEmpty()) {
            for(NeedOrderDto dto : needOrderList) {
                LocalDateTime readyDate = materialsService.calculateArrivalDate(orderDate, dto.getMtrId());  //원자재 입고 날짜

                //해당 원자재 입고 날짜가 materialReadyDate보다 이후이면 완료 시간 지정
                if(readyDate.isAfter(materialReadyDate))
                    materialReadyDate = readyDate;
            }
        }

        //모든 공정 다 돌렸을 때 시간
        List<WorderDto> worderDtos = calculateWorderDate(materialReadyDate, productId, orderCnt);     //모든 공정에 대한 기록
        LocalDateTime finishDate = worderDtos.get(worderDtos.size() - 1).getFinishDate();   //마지막 공정의 완료 시간

        return finishDate;
    }

    //시뮬레이션 - 작업 시간 계산(기존 작업을 확인 후 중복 값이 없을 때)
    public List<WorderDto> calculateWorderDate(LocalDateTime materialReadyDate, String productId, long orderCnt){
        List<WorderDto> worderDtos = calculateWorderTime(materialReadyDate, productId, orderCnt);
        LocalDateTime resultDate = worderDtos.get(worderDtos.size() - 1).getFinishDate();   //마지막 공정의 완료 시간

        //작업시간 계산 불러옴
        List<WplanDto> getResultDate = wplanRepository.workDate(resultDate);

        while(!getResultDate.isEmpty()) { //쿼리 값이 있다면 현재 공정 돌아가고 난 후에 돌릴 수 있음
            LocalDateTime endDateValue = getResultDate.get(0).getEndDate();
            worderDtos = calculateWorderTime(endDateValue, productId, orderCnt);
            resultDate = worderDtos.get(worderDtos.size() - 1).getFinishDate();   //마지막 공정의 완료 시간
            getResultDate = wplanRepository.workDate(resultDate);
        }

        return worderDtos;
    }

    //시뮬레이션 - 작업 시간 계산(순수)
    public List<WorderDto> calculateWorderTime(LocalDateTime materialReadyDate, String productId, long orderCnt){
        //매개변수 materialReadyDate : 모든 원자재가 준비 완료되는 시간 / productId : 제품 고유번호
        List<WorderDto> dtoList = new ArrayList<>();   //반환할 작업지시(Worder) dto List

        //라우팅에서 공정 흐름 얻기
        List<Routing> routings = routingRepository.findByProductIdOrderByOrder(productId);

        //공정Id만 list로
        List<String> producctIdlist = new ArrayList<>();    //공정Id 리스트
        for(int i = 0; i < routings.size(); i++) {
            producctIdlist.add(routings.get(i).getProcessId());
        }

        //라우팅에 따른 공정 리스트 받기
        List<Mprocess> mprocesses = mprocessRepository.findAllById(producctIdlist);


        //기준 시간 - 1. 원료계량의 시작 시간은 자재 준비 일자
        LocalDateTime startDate = materialReadyDate;     //기준이 될 시간
        LocalDateTime finishDate = startDate;

        //기준 용량 계산
        NeedEaDto ea = bomService.calcEa(productId, orderCnt);
        System.out.println("neadEaDto=" + ea);

        //공정별 처리
        for(int i = 0; i < mprocesses.size(); i++) {
            Mprocess process = mprocesses.get(i);   //이번 공정 정보
            List<Finfo> facilities = finfoRepository.findByName(process.getFacilityId()); //공정의 설비 정보
            System.out.println(process);

            //임시로 필요 용량 넣기
            double output = 0;  //생산량
            long cycle = 1;   //가동 회수
            switch(process.getName()) {
                case "전처리":
                    output = ea.getMaterialWeight(); break;    //양배추 1000,000g
                case "추출": case "혼합+살균":
                    output = ea.getLiquidWeight();  break;      //양배추 추출액 1600,000g
                case "충진(파우치)": case "검사": output = ea.getAmount(); break;   //양배추즙 파우치 20010개
                case "포장" : output = ea.getBox();  break;   //수주받은 양배추즙 667Box
            }
            output /= facilities.size();    //설비 개수만큼 생산량 분배

            //전처리~혼합+살균까지 capa보다 많으면 같은 공정 다시 돌리기
            if(process.getName().equals("전처리") || process.getName().equals("추출") || process.getName().equals("혼합+살균"))
                cycle = (long) (output / process.getCapa() + (output % process.getCapa() > 0 ? 1 : 0));

            System.out.println("cycle=" + cycle);
            while (cycle-- > 0) {
            /*
            //설비가 2개 이상이며, 용량이 홀수이고, 개수로 나눠떨어지는 종류(box, 개)인 경우 +1
            if(facilities.size() % 2 != 1 &&
                    capacity % 2 != 0 &&
                    (process.getName().equals("충진(파우치)") || process.getName().equals("검사") || process.getName().equals("포장")))
                capacity++;

            capacity /= facilities.size();  //설비 개수만큼 나누기
             */

                //cycle이 1보다 크면(설비 capa보다 필요 생산량이 많으면) 설비 capa만큼, 아니면 필요생산량(output)만큼
                double capacity = cycle > 1 ? process.getCapa() : output;
                output -= process.getCapa();    //output에서 생산량만큼 제외

                //이전 작업 완료 시간을 현재 공정의 작업 시작 시작으로 설정 (점심시간 & 퇴근시간 고려)
                startDate = calculateAdjustedStartTime(finishDate);

                //작업 완료 시간 세팅
                finishDate = calculateProcessFinishTime(startDate, process, capacity);

                //주말 판정 (작업 시간 도중 주말이 끼어있으면 그만큼 뒤로 밀려남)
                for (LocalDateTime start = startDate; start.isBefore(finishDate) || start.isEqual(finishDate); start = start.plusDays(1)) {
                    if (start.getDayOfWeek() == DayOfWeek.SATURDAY || start.getDayOfWeek() == DayOfWeek.SUNDAY) {
                        finishDate = finishDate.plusDays(1);
                    }
                }

                //TODO: CAPA보다 많은 양인 경우 한번 더 들어감. 이 경우 작업지시 더 들어가도록 쪼개기
                //작업 지시 dto 추가
                for (int j = 0; j < facilities.size(); j++) {
                    WorderDto dto = new WorderDto(process.getId(), facilities.get(j).getId(), startDate, finishDate);        //이번 공정의 작업지시dto
                    dtoList.add(dto);
                }
            }
        }

        return dtoList;
    }

    //시뮬레이션 - 작업 시간 계산 - 공정별 작업 완료 시간 계산
    public LocalDateTime calculateProcessFinishTime(LocalDateTime date, Mprocess process, double capacity) {
        LocalDateTime result;

        switch(process.getName()) {
            case "원료계량":
                result = date.plusMinutes(process.getLeadtime() + process.getProdtime());
                break;
            case "혼합+살균":
                double min = (process.getLeadtime() + process.getProdtime());
                long nanos = (long) (min * 60 * 1000000000);
                result = date.plusNanos(nanos);
                break;
            case "식힘":
                result = date.plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0);
                break;
            default:
                double defaultMin = process.getLeadtime() + process.getTimeUnit() * (double) capacity / process.getCapa();
                long defaultNanos = (long) (defaultMin * 60 * 1000000000);
                result = date.plusNanos(defaultNanos);
                break;
        }

        //수작업인 작업은 작업 외 시간(점심&퇴근) 고려
        if(process.getName().equals("원료계량") || process.getName().equals("포장")) {
            result = calculateAdjustedFinishTime(date, result);
        }

        return result;
    }

    //시뮬레이션 - 작업 시간 계산 - (작업 시작 시간) 점심시간 & 퇴근시간 고려
    public LocalDateTime calculateAdjustedStartTime(LocalDateTime date) {
        if (date.getHour() >= 12 && date.getHour() < 13)    //점심시간
            return date.withHour(13).withMinute(0).withSecond(0).withNano(0);
        else if (date.getHour() < 9)    //출근 시간 이전
            return date.withHour(9).withMinute(0).withSecond(0).withNano(0);
        else if (date.getHour() >= 18)  //퇴근 시간 이후
            return date.plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0);
        else return date;
    }

    //시뮬레이션 - 작업 시간 계산 - (작업 완료 시간) 점심시간 & 퇴근시간 고려
    //TODO: 점심&퇴근으로 중간에 작업이 중단되었을 때 리드타임 한번 더 추가할지 말지 결정하기(현재는 추가X)
    public LocalDateTime calculateAdjustedFinishTime(LocalDateTime start, LocalDateTime finish) {
        long durationDays = ChronoUnit.HOURS.between(start, finish) / 8;   //작업 시간을 시간 단위로 했을 때(분, 초 단위는 버림) 하루 8시간 작업 기준으로 일수 나누기
        long durationNanos = ChronoUnit.NANOS.between(start, finish) - durationDays * 8 * 60 * 60 * 1000000000; //위에서 버려진 시간의 나머지(9시간 미만)
        LocalDateTime standardTime = start.plusDays(durationDays);          //작업 일수 더한 후 나머지 시간 더하기 전
        LocalDateTime finishTime = standardTime.plusNanos(durationNanos);   //작업 일수 더한 후 나머지 시간 더했을 때

        while(standardTime.isBefore(finishTime)) {
            int hour = standardTime.getHour();
            if(hour < 9 || hour >= 18 || hour == 12) {
                finishTime = finishTime.plusHours(1);
            }
            standardTime = standardTime.plusHours(1);
        }

        return finishTime;
    }

    public List<Mprocess> getAllProcess(){
        return mprocessRepository.findAll();
    }
}
