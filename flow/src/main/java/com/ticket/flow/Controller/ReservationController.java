package com.ticket.flow.Controller;

import com.ticket.flow.DTO.ReservationRequest;
import com.ticket.flow.DTO.ResponseReservation;
import com.ticket.flow.Entity.Reservation;
import com.ticket.flow.Service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // 예약 기능
    @PostMapping
    public ResponseEntity<ResponseReservation> createReservation(@RequestBody ReservationRequest request){

        ResponseReservation savedReservation = reservationService.createReservation(request);

        URI location = URI.create("/reservations/" + savedReservation.getId());

        return ResponseEntity
                .created(location)
                .body(savedReservation);

    }


}
