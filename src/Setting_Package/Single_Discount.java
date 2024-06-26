package Setting_Package;

import java.sql.Date;
import java.sql.Time;

public class Single_Discount {

    private String dlsCode;
    private float amount;
    private Date deadline;

    public Single_Discount(String dlsCode, float amount, Date deadline) {
        this.dlsCode = dlsCode;
        this.amount = amount;
        this.deadline = deadline;
    }

    public String getDlsCode() {
        return dlsCode;
    }

    public void setDlsCode(String dlsCode) {
        this.dlsCode = dlsCode;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Discount code: " + dlsCode + ", Amount: " + amount + ", Deadline: " + deadline.toString();
    }
}
