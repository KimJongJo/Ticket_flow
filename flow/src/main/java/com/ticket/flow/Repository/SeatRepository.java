package com.ticket.flow.Repository;

import com.ticket.flow.Entity.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByEventId(Long eventId);

    // 사용자들의 read는 허용, write는 대기
    // 좌석 목록을 봐야하기 때문에 해당 좌석을 예약하려고 할 때만 락 걸기
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Seat s where s.id in :ids")
    List<Seat> findAllByIdInWithLock(@Param("ids") List<Long> ids);
}
