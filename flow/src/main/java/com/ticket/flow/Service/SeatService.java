package com.ticket.flow.Service;

import com.ticket.flow.DTO.RequestCreateSeat;
import com.ticket.flow.Entity.Seat;

import java.util.List;

public interface SeatService {
    Seat createSeat(RequestCreateSeat seat);

    List<Seat> findByEventId(Long eventId);
}
