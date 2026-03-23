package com.ticket.flow.Service;

import com.ticket.flow.DTO.ReservationRequest;
import com.ticket.flow.DTO.ResponseReservation;
import com.ticket.flow.DTO.TicketDto;
import com.ticket.flow.Entity.Guest;
import com.ticket.flow.Entity.Reservation;
import com.ticket.flow.Entity.Seat;
import com.ticket.flow.Entity.Ticket;
import com.ticket.flow.Enum.ReservationStatus;
import com.ticket.flow.Enum.SeatStatus;
import com.ticket.flow.Exception.AlreadyReservedException;
import com.ticket.flow.Repository.GuestRepository;
import com.ticket.flow.Repository.ReservationRepository;
import com.ticket.flow.Repository.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;
    private final GuestRepository guestRepository;

    @Override
    @Transactional
    public ResponseReservation createReservation(ReservationRequest request) {

        // 예약을 할 때 좌석의 상태가 이미 예약된 상태인지 확인을 해서 AVAILAVLE 상태면 예약 처리후 RESERVED로 상태 변경
        // 좌석을 조회할 때 for문으로 조회하면 좌석 개수만큼 DB조회를 하기 때문에 List 조회 방식으로 DB에 한번만 조회하기
        List<Seat> seats = seatRepository.findAllByIdInWithLock(request.getSeatIds()); // -> IN 사용

        // 상태 검증
        // anyMatch => 하나라도 맞으면 즉시 true 반환
        // allMatch => 모든 조건이 맞아야 true
        // 예외 처리
        if(seats.stream().anyMatch(seat -> seat.getStatus() == SeatStatus.RESERVED)){
            throw new AlreadyReservedException();
        }

        // 예약 처리
        // Transactional 어노테이션을 쓰면 메소드 종료 시점에 더티 체킹으로 DB에 반영
        seats.forEach(seat -> seat.setStatus(SeatStatus.RESERVED));

        // 예약 객체 생성
        Guest guest = guestRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("사용자 없음"));
        
        Reservation reservation = Reservation.builder()
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.now())
                .guest(guest)
                .tickets(new ArrayList<>())
                .build();
                
        // 티켓 생성
        for(Seat seat : seats){
            Ticket ticket = Ticket.builder()
                    .issuedAt(LocalDateTime.now())
                    .reservation(reservation)
                    .seat(seat)
                    .build();

            reservation.getTickets().add(ticket);
        }

        Reservation savedReservation = reservationRepository.save(reservation);

        return ResponseReservation.builder()
                .id(savedReservation.getId())
                .status(savedReservation.getStatus())
                .createdAt(savedReservation.getCreatedAt())
                .userId(savedReservation.getGuest().getId())
                .tickets(savedReservation.getTickets().stream().map(ticket -> TicketDto.builder()
                        .id(ticket.getId())
                        .issuedAt(ticket.getIssuedAt())
                        .seatId(ticket.getSeat().getId()).build()).collect(Collectors.toList()))
                .build();
    }
}
