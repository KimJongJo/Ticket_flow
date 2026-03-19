package com.ticket.flow.Controller;

import com.ticket.flow.DTO.JoinGuestRequest;
import com.ticket.flow.Entity.Guest;
import com.ticket.flow.Service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @PostMapping
    public ResponseEntity<Guest> createGuest(@RequestBody JoinGuestRequest guestRequest){

        Guest guest = guestService.createGuest(guestRequest);

        URI location = URI.create("/guests/" + guest.getId());

        return ResponseEntity
                .created(location)
                .body(guest);

    }
}
