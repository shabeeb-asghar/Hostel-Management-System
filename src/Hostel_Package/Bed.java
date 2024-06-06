package Hostel_Package;
public class Bed {
    private String name;
    private int id;
    private boolean available;
    public Bed() {
        this.available = true; // Assuming beds are available by default
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
   
}

