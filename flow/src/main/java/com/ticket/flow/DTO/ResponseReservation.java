package com.ticket.flow.DTO;

import com.ticket.flow.Entity.Guest;
import com.ticket.flow.Enum.ReservationStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseReservation {

    private Long id;
    private ReservationStatus status;
    private LocalDateTime createdAt;
    private List<TicketDto> tickets;
    private Long userId;

}
