package com.B1team.b01.repository;


import com.B1team.b01.entity.Worder;
import com.B1team.b01.entity.Wperform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface WorderRepository extends JpaRepository<Worder, String> , JpaSpecificationExecutor<Worder> {
    //@Query("select wp from Wplan wp where wp.state ='완료' and wp.orderId = :orderId")
    //List<Worder> findWordersByConditions(LocalDateTime startDate, LocalDateTime endDate);//작업지시 리스트 중에 공정예약 or 공정이 돌아가고 있는 것
    //List<Worder> findWordersByConditions();//작업지시 리스트 중에 공정예약 or 공정이 돌아가고 있는 것

    @Query("select wo from Worder wo where wo.processId = :processId and wo.wplanId = :wplanId")
    List<Worder> findByState(@Param("processId") String processId,
                             @Param("wplanId") String wplanId); //작업지시 테이블에서 특정 공정고유번호에 대한 컬럼 전체를 불러옴(진행)

   //포장작업 완료 데이터(출하관련)
    @Query("select wo from Worder wo where wo.wplanId = :wplanId and wo.processId = 'A110'")
    Worder findByEndProcess(@Param("wplanId") String wplanId);

    List<Worder> findAll();

    //설비 - 작업 지시에서 현재 가동 중인 설비 찾기
    @Query("SELECT w FROM Worder w WHERE w.facilityId = :fid AND w.startDate <= :currentDateTime AND w.finishDate >= :currentDateTime")
    List<Worder> findWordersByFacilityIdAndCurrentDateTime(@Param("fid") String fid,
                                                           @Param("currentDateTime")LocalDateTime currentDateTime);
}
