package com.ticket.flow.Entity;

import com.ticket.flow.Enum.ReservationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Reservation {
    // 예약에서 알아야 할 정보들
    // 1. 예약자
    // 2. 티켓(복수 가능)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "reservation")
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

}
