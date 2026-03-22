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

    // 조회할 때 해당 로우(Row)를 다른 트랜잭션이 건드리지 못하게 잠급니다.
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Query("select s from Seat s where s.id in :ids")
//    List<Seat> findAllByIdInWithLock(@Param("ids") List<Long> ids);
}
