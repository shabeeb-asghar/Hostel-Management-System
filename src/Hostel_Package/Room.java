package Hostel_Package;
import java.util.ArrayList;
import java.util.List;

public class Room {
private List<Bed> beds;
    private boolean availability;
    public Room() {
        this.beds = new ArrayList<>();
        this.availability = true; // Assuming rooms are available by default
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
