package com.ticket.flow.Service;

import com.ticket.flow.DTO.ResponseTicket;
import com.ticket.flow.Repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;

    @Override
    public List<ResponseTicket> getTicketInfo(Long reservationId) {

        return ticketRepository.findAllByReservationId(reservationId)
                .stream().map(ticket -> ResponseTicket.builder()
                        .id(ticket.getId())
                        .issuedAt(ticket.getIssuedAt())
                        .reservationId(ticket.getReservation().getId())
                        .seatId(ticket.getSeat().getId())
                        .build())
                .collect(Collectors.toList());
    }
}
