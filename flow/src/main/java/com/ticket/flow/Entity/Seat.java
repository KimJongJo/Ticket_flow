package com.ticket.flow.Entity;

import com.ticket.flow.Enum.SeatStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Seat {
    // 좌석에서 알아야 할 정보
    // 1. 이벤트

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

}
