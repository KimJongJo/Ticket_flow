package com.ticket.flow.Service;

import com.ticket.flow.Entity.Seat;
import com.ticket.flow.Repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService{

    private final SeatRepository seatRepository;

    @Override
    public Seat createSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    @Override
    public List<Seat> findByEventId(Long eventId) {
        return seatRepository.findByEventId(eventId);
    }
}
