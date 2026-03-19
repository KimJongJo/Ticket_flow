package com.ticket.flow.DTO;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class TicketDto {

    private Long id;
    private LocalDateTime issuedAt;
    private Long seatId;
}
