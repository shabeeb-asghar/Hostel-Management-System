package Hostel_Package;
public class Laundary extends Service{
    private String laundry="Laundary";

    public Laundary() {
        super();
    }
public String getname()
{
    return laundry;
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
