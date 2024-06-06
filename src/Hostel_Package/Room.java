package Hostel_Package;
import java.util.ArrayList;
import java.util.List;

import Users_Package.dataBase;

public class Room {
    private String name;
private List<Bed> beds;
    private boolean availability;
    public Room() {
        this.beds = new ArrayList<>();
        this.availability = true; // Assuming rooms are available by default
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
