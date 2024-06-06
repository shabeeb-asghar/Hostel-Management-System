package Hostel_Package;
import java.util.ArrayList;
import java.util.List;

public class Service {
   private int id;
    private List<String> services;

    public Service() {
        this.services = new ArrayList<>();
    }

    public void addService(String service) {
        services.add(service);
    }

    public void removeService(String service) {
        services.remove(service);
    }
}
