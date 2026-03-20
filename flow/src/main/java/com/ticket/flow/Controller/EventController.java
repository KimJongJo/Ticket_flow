package com.ticket.flow.Controller;

import com.ticket.flow.DTO.EventCreateRequest;
import com.ticket.flow.DTO.ResponseEvent;
import com.ticket.flow.Entity.Event;
import com.ticket.flow.Service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // 이벤트 생성
    @PostMapping
    public ResponseEntity<ResponseEvent> createEvent(@RequestBody EventCreateRequest event) {
        ResponseEvent savedEvent = eventService.createEvent(event);

        URI location = URI.create("/events/" + savedEvent.getId());

        return ResponseEntity
                .created(location)
                .body(savedEvent);

    }

    // 이벤트 전체 조회
    @GetMapping
    public ResponseEntity<List<Event>> getEvents(){

        return ResponseEntity.ok().body(eventService.findAll());
    }

    // id로 특정 이벤트 조회
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id){

        return ResponseEntity.ok().body(eventService.findById(id));

    }

}
