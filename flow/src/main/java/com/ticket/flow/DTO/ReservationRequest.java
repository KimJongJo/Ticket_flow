package com.ticket.flow.DTO;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ReservationRequest {

    private Long userId;
    private Long eventId;
    private List<Long> seatIds;
}
