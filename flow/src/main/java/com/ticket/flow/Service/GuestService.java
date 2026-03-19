package com.ticket.flow.Service;

import com.ticket.flow.DTO.JoinGuestRequest;
import com.ticket.flow.Entity.Guest;

public interface GuestService {
    Guest createGuest(JoinGuestRequest guestRequest);
}
