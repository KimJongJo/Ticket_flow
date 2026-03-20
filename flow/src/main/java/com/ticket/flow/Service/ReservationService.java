package com.ticket.flow.Service;

import com.ticket.flow.DTO.ReservationRequest;
import com.ticket.flow.DTO.ResponseReservation;
import com.ticket.flow.Entity.Reservation;

public interface ReservationService {

    ResponseReservation createReservation(ReservationRequest request);

}
