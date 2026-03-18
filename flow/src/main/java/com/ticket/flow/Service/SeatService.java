package com.ticket.flow.Service;

import com.ticket.flow.Entity.Seat;

import java.util.List;

public interface SeatService {
    Seat createSeat(Seat seat);

    List<Seat> findByEventId(Long eventId);
}
