package dev.study.퍼샤드.과제1;

public class RoomFacade {
    private final RoomService roomService = new RoomService();
    private final PaymentService paymentService = new PaymentService();
    private final NotificationService notificationService = new NotificationService();

    public void reservationRoom(String room) {
        roomService.reservation(room);
        paymentService.processPayment(room);
        notificationService.alarm(room);
    }
}
