package com.ticket.flow.Service;

import com.ticket.flow.DTO.JoinGuestRequest;
import com.ticket.flow.Entity.Guest;
import com.ticket.flow.Repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService{

    private final GuestRepository guestRepository;

    @Override
    public Guest createGuest(JoinGuestRequest guestRequest) {
        return guestRepository.save(Guest.builder()
                .name(guestRequest.getName())
                .email(guestRequest.getEmail())
                .phone(guestRequest.getPhone())
                .build());

    }
}
