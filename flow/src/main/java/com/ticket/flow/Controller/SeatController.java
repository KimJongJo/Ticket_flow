package com.ticket.flow.Controller;

import com.ticket.flow.Entity.Seat;
import com.ticket.flow.Service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    // 좌석 생성
    @PostMapping
    public ResponseEntity<Seat> createSeat(@RequestBody Seat seat){

        Seat savedSeat = seatService.createSeat(seat);

        URI location = URI.create("/seats/" + savedSeat.getId());

        return ResponseEntity
                .created(location)
                .body(savedSeat);

    }

    @GetMapping
    public ResponseEntity<List<Seat>> getSeats(@RequestParam Long eventId){

        return ResponseEntity.ok().body(seatService.findByEventId(eventId));
    }


}
