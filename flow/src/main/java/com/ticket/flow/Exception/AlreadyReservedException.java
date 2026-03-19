package com.ticket.flow.Exception;

import lombok.Getter;

import java.util.List;

@Getter
public class AlreadyReservedException extends RuntimeException{

    public AlreadyReservedException(){
        super("이미 예약된 좌석이 포함되어 있습니다.");
    }
}
