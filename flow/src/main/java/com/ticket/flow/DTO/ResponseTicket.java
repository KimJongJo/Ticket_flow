package com.ticket.flow.DTO;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ResponseTicket {

    private Long id;
    private LocalDateTime issuedAt;
    private Long reservationId;
    private Long seatId;

}
