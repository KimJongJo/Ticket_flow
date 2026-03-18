package com.ticket.flow.Service;

import com.ticket.flow.Entity.Event;

import java.util.List;

public interface EventService {
    Event createEvent(Event event);

    List<Event> findAll();

    Event findById(Long id);
}
