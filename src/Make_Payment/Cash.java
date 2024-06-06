package Make_Payment;

public class Cash extends Payment {
    @Override
    public String makePayment(String challan, String num, float price) {
        this.challan = challan;
        this.price=100;
        //this.price=price;
        return "Cash payment successful for challan: " + challan;
    }

    public String printChallan(String challan, String num) {
        return "Challan printed for challan: " + challan + ", number: " + num;
    }

    @Override
    public String updateSystem() {
        return "Cash system updated for " + challan;
    }
}
