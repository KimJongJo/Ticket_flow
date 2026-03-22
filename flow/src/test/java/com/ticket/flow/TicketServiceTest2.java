package com.ticket.flow;

import com.ticket.flow.DTO.ReservationRequest;
import com.ticket.flow.Entity.Event;
import com.ticket.flow.Entity.Guest;
import com.ticket.flow.Entity.Seat;
import com.ticket.flow.Enum.SeatStatus;
import com.ticket.flow.Repository.EventRepository;
import com.ticket.flow.Repository.GuestRepository;
import com.ticket.flow.Repository.ReservationRepository;
import com.ticket.flow.Repository.SeatRepository;
import com.ticket.flow.Service.ReservationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
//@Transactional
public class TicketServiceTest2 {

    private Long guestId;
    private Long eventId;
    private List<Long> seatIds;

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp(){
        // 1. 게스트 생성

        Guest guest = Guest.builder()
                .name("테스터")
                .email("test@test.com")
                .phone("01012121212")
                .build();

        // 2. 이벤트 생성
        Event event = Event.builder()
                .title("테스트_이벤트")
                .eventTime(LocalDateTime.now())
                .venue("테스트장소")
                .build();

        // 3. 좌석 생성
        Seat seat1 = Seat.builder()
                .seatNumber("A1")
                .status(SeatStatus.AVAILABLE)
                .event(event)
                .build();

        Seat seat2 = Seat.builder()
                .seatNumber("A2")
                .status(SeatStatus.AVAILABLE)
                .event(event)
                .build();

        guestRepository.save(guest);
        eventRepository.save(event);
        seatRepository.save(seat1);
        seatRepository.save(seat2);


        this.guestId = guest.getId();
        this.eventId = event.getId();
        this.seatIds = List.of(seat1.getId(), seat2.getId());

    }

    @AfterEach
    void cleanDB() {
        reservationRepository.deleteAll();
        seatRepository.deleteAll();
        eventRepository.deleteAll();
        guestRepository.deleteAll();
    }

    @Test
    @DisplayName("100명이 동시에 예약을 했을 때 한명만 성공해야 한다.")
    void concurrentReservationTest() throws InterruptedException {

        // 100번의 예약
        int reservationCount = 100;

        // 10개의 고정된 스레드를 이용해서 100개의 일을 처리하겠다.
        ExecutorService executorService = Executors.newFixedThreadPool(10); // 10개의 고정 스레드
        CountDownLatch latch = new CountDownLatch(reservationCount); // 100개가 들어와야 종료 -> 이걸 쓰지 않으면 1개가 실패될 때 바로 테스트 실패로 끝남

        // 일반적인 Integer 와의 차이점 -> 일반 Integer는 멀티스레드 환경에서 숫자가 새나간다.
        // AtomicInteger를 사용하면 값을 계속 확인하기 때문에 새나갈수 없게한다.
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        // 예약할때 필요한 객체 DTO 생성
        ReservationRequest request = ReservationRequest.builder()
                .userId(guestId)
                .eventId(eventId)
                .seatIds(seatIds)
                .build();


        for(int i = 0; i < reservationCount; i++){

            executorService.submit(() -> {
                try{
                    reservationService.createReservation(request);
                    successCount.incrementAndGet();
                }catch(Exception e){
                    failCount.incrementAndGet();

                    // 첫 실패에 대한 이유를 보기
                    if(failCount.get() == 1){
                        System.out.println("첫 번째 실패 원인 : " + e.getMessage());
                    }
                }finally{
                    // 100 개를 카운트 다운
                    latch.countDown();
                }
            });

        }

        // 100개가 다 들어올 때까지 기다리기
        latch.await();
        // 테스트가 여러개면, 테스트가 하나 끝날때마다 10개씩 쌓이는데, 이걸 해제해주는 메서드
        executorService.shutdown();

        System.out.println("성공한 횟수 : " + successCount.get());
        System.out.println("실패한 횟수 : " + failCount.get());

        Assertions.assertThat(successCount.get()).isEqualTo(1);



    }



}
