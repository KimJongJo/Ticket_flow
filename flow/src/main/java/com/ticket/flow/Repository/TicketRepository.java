package com.ticket.flow.Repository;

import com.ticket.flow.Entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByReservationId(Long reservationId);
}
