package com.ticket.flow.Service;

import com.ticket.flow.DTO.RequestCreateSeat;
import com.ticket.flow.Entity.Event;
import com.ticket.flow.Entity.Seat;
import com.ticket.flow.Repository.EventRepository;
import com.ticket.flow.Repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService{

    private final SeatRepository seatRepository;
    private final EventRepository eventRepository;

    @Override
    public Seat createSeat(RequestCreateSeat seat) {

        Event event = eventRepository.findById(seat.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("입력한 ID의 이벤트가 존재하지 않음"));

        return seatRepository.save(Seat.builder()
                .seatNumber(seat.getSeatNumber())
                .status(seat.getStatus())
                .event(event)
                .build());
    }

    @Override
    public List<Seat> findByEventId(Long eventId) {
        return seatRepository.findByEventId(eventId);
    }
}
