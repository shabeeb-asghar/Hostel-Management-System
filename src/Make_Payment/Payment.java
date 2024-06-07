package Make_Payment;

public abstract class Payment {
    protected String accNo;
    protected String challan;
protected float price=1900;
    public abstract String makePayment(String challan, String num,float price);

    public String updateSystem() {
        return "System updated for " + challan;
    }
}
