package com.ticket.flow.Service;

import com.ticket.flow.DTO.ResponseTicket;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TicketService {
    List<ResponseTicket> getTicketInfo(Long reservationId);
}
