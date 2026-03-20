package com.ticket.flow.DTO;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ResponseEvent {

    private Long id;
    private String title;
    private LocalDateTime eventTime;
    private String venue;
}
