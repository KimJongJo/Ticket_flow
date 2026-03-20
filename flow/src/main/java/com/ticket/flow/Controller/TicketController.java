package com.ticket.flow.Controller;

import com.ticket.flow.DTO.ResponseTicket;
import com.ticket.flow.Service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<ResponseTicket>> getTicketInfo(@RequestParam Long reservationId){

        return ResponseEntity.ok(ticketService.getTicketInfo(reservationId));
    }

}
