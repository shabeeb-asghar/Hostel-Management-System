package Setting_Package;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import Users_Package.Feedback;

public class Settings {
//private GuestPolicy policy;
    //private Notification notification;
    private Discounts discount;
    private Feedback feedback;

    public Settings() {
       // this.policy = new GuestPolicy();
        //this.notification = new Notification();
        this.discount = new Discounts();
        this.feedback = new Feedback();
    }
   

    public void setFeedback(String feedback) {
        this.feedback.addFeedback(feedback);
    }

    public List<String> getFeedback() {
        return feedback.getFeedback();
    }

    public void removeFeedback(String feedback) {
        this.feedback.removeFeedback(feedback);
    }

    public void setDiscount(String dlsCode, float amount, Date deadline) {
        this.discount.setDiscount(dlsCode, amount, deadline);
    }

    public void removeDiscount(String dlsCode) {
        this.discount.removeDiscount(dlsCode);
    }

    public void updateDiscount(String oldDlsCode, float oldAmount, Date oldDeadline, String newDlsCode, float newAmount, Date newDeadline) {
        this.discount.updateDiscount(oldDlsCode, oldAmount, oldDeadline, newDlsCode, newAmount, newDeadline);
    }

    public List<Single_Discount> getDiscounts() {
        return this.discount.getAllDiscounts();
    }
   /*  public void setNotification(String input) {
        this.notification.setNotification(input);
    }

    public void removeNotification() {
        this.notification.removeNotification();
    }

    public void addPolicy(String policy) {
        this.policy.enterPolicy(policy);
    }

    public void updatePolicy(String policy) {
        this.policy.updatePolicy(policy);
    }*/
}
