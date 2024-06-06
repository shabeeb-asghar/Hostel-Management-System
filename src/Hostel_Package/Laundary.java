package Hostel_Package;
public class Laundary extends Service{
    private String laundry;

    public Laundary() {
        super();
    }

    public void addLaundry(String laundry) {
        this.laundry = laundry;
        addService(laundry);
    }

    public void removeLaundry() {
        removeService(laundry);
        this.laundry = null;
    }

    
}
