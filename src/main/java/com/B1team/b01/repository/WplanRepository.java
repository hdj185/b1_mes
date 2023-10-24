package com.B1team.b01.repository;

import com.B1team.b01.dto.WplanDto;
import com.B1team.b01.entity.Wplan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WplanRepository extends JpaRepository<Wplan, String> {

    //작업계획 등록 관련, 같은 수주번호로 먼저 등록된 작업계획이 있는지 확인하기
    @Query("select wp from Wplan wp where wp.orderId = :orderId")
    Optional<Wplan> findByPlanOrderId(String orderId);

    //작업계획 등록 관련, 같은 수주번호로 먼저 등록된 작업계획이 있는지 확인하기
    @Query("select wp from Wplan wp where wp.orderId = :orderId")
    Wplan findByPlanOrderId2(String orderId);

    //작업지시 관련 작업계획 조회하기
    @Query("select wp from Wplan wp where wp.orderId = :orderId")
    WplanDto findByOrderId(@Param("orderId") String orderId);

    //작업실적 관련 작업계획 조회하기
    @Query("select wp from Wplan wp where wp.state ='완료' and wp.orderId = :orderId")
    List<Wplan> findByWplanState(@Param("orderId") String orderId);


    @Query("SELECT w FROM Wplan w WHERE :workDate BETWEEN w.startDate AND w.endDate order by w.endDate desc")
    List<WplanDto> workDate(@Param("workDate") LocalDateTime workDate);

    //작업계획 업데이트

    @Modifying
    @Query(value = "UPDATE Wplan w set w.state = '진행중' where w.endDate = :localDateTime")
    void updateIngState(@Param("localDateTime") LocalDateTime localDateTime);

    //작업계획 업데이트

    @Modifying
    @Query(value = "UPDATE Wplan w set w.state = '진행대기' where w.startDate = :localDateTime")
    void updateWaitState(@Param("localDateTime") LocalDateTime localDateTime);

    //작업계획 업데이트

    @Modifying
    @Query("UPDATE Wplan w set w.state = '완료' where w.endDate = :localDateTime")
    void updateEndState(@Param("localDateTime") LocalDateTime localDateTime);

    //계획 시간 가지고오기
    @Query("SELECT w.startDate FROM Wplan w")
    List<LocalDateTime> selectStartTime();

    @Query("SELECT w.endDate FROM Wplan w")
    List<LocalDateTime> selectEndTime();

}

