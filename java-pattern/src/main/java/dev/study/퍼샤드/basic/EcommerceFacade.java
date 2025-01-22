package dev.study.퍼샤드.basic;

public class EcommerceFacade {
    private final OrderService orderService = new OrderService();
    private final PaymentService paymentService = new PaymentService();
    private final ShippingService shippingService = new ShippingService();

    public void completePurchase(String product) {
        orderService.placeOrder(product);
        paymentService.processPayment(product);
        shippingService.shipProduct(product);
    }
}
