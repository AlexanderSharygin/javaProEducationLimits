package pro.java.education.payment.model;

public enum PaymentStatus {

    PENDING("PENDING"),
    APPROVED("APPROVED"),
    CANCELED("CANCELED");

    private final String state;

    PaymentStatus(String state) {
        this.state = state;
    }

    public String toString() {
        return state;
    }
}
