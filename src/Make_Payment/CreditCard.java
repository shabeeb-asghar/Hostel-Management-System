package Make_Payment;
public class CreditCard extends Payment {
    protected float balance;
    @Override
    public String makePayment(String challan, String num, float price) {
        this.challan = challan;
        this.accNo = num;
        this.price=price;
        //this.price=100;
        return "Credit Card payment successful for challan: " + challan + ", account number: " + num+ ", Price: "+ price;
    }
    public void setbalance(float bal)
    {
        balance=bal;
    }
     public float getbalance()
     {
        return balance;
     }
    @Override
    public String updateSystem() {
        return "Credit Card system updated for " + challan;
    }

}
