package dev.sertan.payroll.order;

class OrderNotFoundException extends RuntimeException {
    OrderNotFoundException(Long id) {
        super("Order not found: " + id);
    }
}
