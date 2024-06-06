package Setting_Package;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Discounts {
   private List<Single_Discount> discounts;

    public Discounts() {
        this.discounts = new ArrayList<>();
    }

    public void setDiscount(String dlsCode, float amount, Time deadline) {
        this.discounts.add(new Single_Discount(dlsCode, amount, deadline));
    }

    public void removeDiscount(String dlsCode) {
        discounts.removeIf(discount -> discount.getDlsCode().equals(dlsCode));
    }

    public void updateDiscount(String oldDlsCode, float oldAmount, Time oldDeadline, String newDlsCode, float newAmount, Time newDeadline) {
        for (Single_Discount discount : discounts) {
            if (discount.getDlsCode().equals(oldDlsCode) && discount.getAmount() == oldAmount && discount.getDeadline().equals(oldDeadline)) {
                discount.setDlsCode(newDlsCode);
                discount.setAmount(newAmount);
                discount.setDeadline(newDeadline);
                break;
            }
        }
    }

    public List<String> getAllDiscounts() {
        List<String> discountList = new ArrayList<>();
        for (Single_Discount discount : discounts) {
            discountList.add(discount.toString());
        }
        return discountList;
    }
}
