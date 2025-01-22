package dev.study.퍼샤드.movie;

public class MovieFacade {
    private final TicketService ticketService = new TicketService();
    private final PaymentService paymentService = new PaymentService();
    private final SeatService seatService = new SeatService();

    public void watchMovie(String movie) {
        ticketService.ticketing(movie);
        paymentService.processPayment(movie);
        seatService.seat(movie);
    }

}
