package Make_Payment;

public class PaymentFactory {
    public Payment makePayment(String type) {
        switch (type.toLowerCase()) {
            case "creditcard":
                return new CreditCard();
            case "cash":
                return new Cash();
            case "bank":
                return new Bank();
            default:
                return null;
        }
    }
}
