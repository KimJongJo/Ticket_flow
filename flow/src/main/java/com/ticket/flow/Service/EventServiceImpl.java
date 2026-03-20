package com.ticket.flow.Service;

import com.ticket.flow.DTO.EventCreateRequest;
import com.ticket.flow.DTO.ResponseEvent;
import com.ticket.flow.Entity.Event;
import com.ticket.flow.Repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;

    @Override
    public ResponseEvent createEvent(EventCreateRequest event) {

        Event savedEvent = eventRepository.save(
                Event.builder()
                        .title(event.getTitle())
                        .eventTime(event.getEventTime())
                        .venue(event.getVenue())
                        .build());

        return ResponseEvent.builder()
                .id(savedEvent.getId())
                .title(savedEvent.getTitle())
                .eventTime(savedEvent.getEventTime())
                .venue(savedEvent.getVenue())
                .build();
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("이벤트 없음"));
    }
}
