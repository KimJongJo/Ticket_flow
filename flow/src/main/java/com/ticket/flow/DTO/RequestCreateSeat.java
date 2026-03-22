package com.ticket.flow.DTO;

import com.ticket.flow.Enum.SeatStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class RequestCreateSeat {

    private String seatNumber;
    private SeatStatus status;
    private Long eventId;

}
