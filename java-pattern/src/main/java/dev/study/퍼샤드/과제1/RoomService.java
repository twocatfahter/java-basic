package dev.study.퍼샤드.과제1;

/**
 * 호텔 예약 시스템 설계
 * 파샤드 패턴 이용 호텔 예약 과정을 단순화 해보기
 * - 방예약 -> RoomService
 * - 결제처리 -> PaymentService
 * - 예약 확인 알림 -> NotificationService
 */
public class RoomService {
    public void reservation(String room) {
        System.out.println(room + "예약");
    }
}
