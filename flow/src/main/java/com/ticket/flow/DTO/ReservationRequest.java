package com.ticket.flow.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReservationRequest {

    private Long userId;
    private Long eventId;
    private List<Long> seatIds;
}
