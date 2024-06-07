package Hostel_Package;
import java.util.ArrayList;
import java.util.List;

import Users_Package.dataBase;

public class Room {
    private int Room_No;
    private double Room_Price;
    private String name;
    private int Guest_no;
    private int capacity;
private List<Bed> beds;
    private boolean availability;
   
    public Room(String name, boolean availability, int noGuests, int capacity, double price, int roomNo) {
        this.name = name;
        this.availability = availability;
        this.Guest_no = noGuests;
        this.capacity = capacity;
        this.Room_Price = price;
        this.Room_No = roomNo;
        this.beds = new ArrayList<>();
    }
    public int getCapacity()
    {
        return this.capacity;
    }
    public int getGuestNo()
    {
        return this.Guest_no;
    }
    public double getRoomPrice()
    {
        return this.Room_Price;
    }
    //
    public int getRoomNo()
    {
        return this.Room_No;
    }
    // Method to insert room into the database
    public List<Bed> getbeds()
    {
        return beds;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public void addBed(Bed bed) {
        beds.add(bed);
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
    
}
