package com.ticket.flow;

import com.ticket.flow.DTO.ReservationRequest;
import com.ticket.flow.Entity.Guest;
import com.ticket.flow.Entity.Seat;
import com.ticket.flow.Enum.SeatStatus;
import com.ticket.flow.Repository.GuestRepository;
import com.ticket.flow.Repository.ReservationRepository;
import com.ticket.flow.Repository.SeatRepository;
import com.ticket.flow.Repository.TicketRepository;
import com.ticket.flow.Service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TicketServiceTest {

    private Long testUserId;
    private List<Long> testSeatIds;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private SeatRepository seatRepository;

    // 테스트 실행 전 DB를 깨끗이 비우고 필요한 데이터를 생성합니다.
    @BeforeEach
    void setUp() {
        // 기존 데이터 삭제 (외래키 제약이 있다면 순서 주의)
        reservationRepository.deleteAll();
        seatRepository.deleteAll();
        guestRepository.deleteAll();

        // 1. 유저(Guest) 생성
        Guest guest = Guest.builder()
                .name("김종조")
                .build();
        Guest savedGuest = guestRepository.save(guest); // DB가 생성해준 ID가 담긴 객체

        // 2. 좌석(Seat) 생성
        Seat seat1 = Seat.builder().status(SeatStatus.AVAILABLE).build();
        Seat seat2 = Seat.builder().status(SeatStatus.AVAILABLE).build();
        Seat savedSeat1 = seatRepository.save(seat1);
        Seat savedSeat2 = seatRepository.save(seat2);

        // 3. 테스트에서 사용할 실제 ID들을 변수에 저장
        this.testUserId = savedGuest.getId();
        this.testSeatIds = List.of(savedSeat1.getId(), savedSeat2.getId());
    }


    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("100명이 동시에 예약을 했을 때 비관적 락으로 인해 1명만 성공해야 한다.")
    void concurrencyTest() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        // 테스트 데이터 (setUp에서 만든 ID와 일치해야 함)
//        Long userId = 1L;
        Long eventId = 2L;
//        List<Long> seatIds = List.of(10L, 11L);

        ReservationRequest request = ReservationRequest.builder()
                .userId(testUserId)
                .eventId(eventId)
                .seatIds(testSeatIds)
                .build();

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    reservationService.createReservation(request);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failCount.incrementAndGet();
                    // 0명이 성공할 경우, 원인을 파악하기 위해 에러 로그를 한 번만 찍어봅니다.
                    if (failCount.get() == 1) {
                        System.out.println("### 첫 번째 실패 원인: " + e.getMessage());
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();


        System.out.println("최종 성공 횟수 : " + successCount.get());
        System.out.println("최종 실패 횟수 : " + failCount.get());

        // 비관적 락이 정상 작동한다면 무조건 1이어야 합니다.
        assertThat(successCount.get()).isEqualTo(1);
    }
}