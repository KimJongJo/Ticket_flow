package com.ticket.flow.Service;

import com.ticket.flow.DTO.EventCreateRequest;
import com.ticket.flow.DTO.ResponseEvent;
import com.ticket.flow.Entity.Event;

import java.util.List;

public interface EventService {
    ResponseEvent createEvent(EventCreateRequest event);

    List<Event> findAll();

    Event findById(Long id);
}
