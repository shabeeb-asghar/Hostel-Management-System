package Hostel_Package;

import Users_Package.dataBase;

public class Bed {
    
    private int id;
    private boolean available;
   
    public Bed(int id, boolean ava) {
        this.id = id;
        this.available = ava; // Assuming beds are available by default
    }
    
    public int getID() {
        return id;
    }
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
   
}

