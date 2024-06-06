package Hostel_Package;
import java.util.ArrayList;
import java.util.List;

import Setting_Package.Discounts;
import Setting_Package.Feedback;

public class Hostel { //controller
    
    private int roomCounter = 1; // Counter for generating room names
    private int bedCounter = 1; // Counter for generating bed IDs
    private int Hostelid;
        private String name;
        private String password;
        private String location;
        private String contactNumber;
        private int numberOfOneBedRooms;
        private int numberOfTwoBedRooms;
        private boolean laundryService;
        private boolean messService;
    private List<Room> rooms;
    private List<Discounts> discount;
    private List<Feedback> feedback;
    //private List<User> users;
    private List<Service> services;
    private MessSystem messSystem;

    
        // Constructor
        public Hostel(String name, String password, String location, String contactNumber, int numberOfOneBedRooms, int numberOfTwoBedRooms, boolean laundryService, boolean messService) {
            
            this.name = name;
            this.password = password;
            this.location = location;
            this.contactNumber = contactNumber;
            this.numberOfOneBedRooms = numberOfOneBedRooms;
            this.numberOfTwoBedRooms = numberOfTwoBedRooms;
            this.laundryService = laundryService;
            this.messService = messService;
            this.rooms = new ArrayList<>();
        this.services = new ArrayList<>();
        this.messSystem = new MessSystem();
        this.feedback= new ArrayList<>();
        this.discount=new ArrayList<>();
       
        
        // Initialize rooms
        initializeRooms();
        // Initialize services
        initializeServices();
        }
       
        public void setId(int id)
        {
            Hostelid=id;
        }
private String generateRoomName() {
    return name + "_Room_" + roomCounter++;
}

private int generateBedID() {
    return bedCounter++;
}

        public List<Room> getRooms() {
            return rooms;
        }
        private void initializeRooms() {
            
            for (int i = 0; i < numberOfOneBedRooms; i++) {
                Room room = new Room();
                room.setName(generateRoomName());
                room.addBed(new SingleSeater(generateBedID()));
                rooms.add(room);
               
            }
            for (int i = 0; i < numberOfTwoBedRooms; i++) {
                Room room = new Room();
        room.setName(generateRoomName());
        room.addBed(new DoubleSeater(generateBedID()));
        room.addBed(new DoubleSeater(generateBedID())); // Assuming two beds in each two-bed room
        rooms.add(room);
                
            }
            
        }


        private void initializeServices() {
            if (laundryService) {
                services.add(new Laundary());
            }
            if (messService) {
                services.add(new MessSystem());
            }
        }
        // Getters
        public String getName() {
            return name;
        }
    
        public String getPassword() {
            return password;
        }
    
        public String getLocation() {
            return location;
        }
    
        public String getContactNumber() {
            return contactNumber;
        }
    
        public int getNumberOfOneBedRooms() {
            return numberOfOneBedRooms;
        }
    
        public int getNumberOfTwoBedRooms() {
            return numberOfTwoBedRooms;
        }
    
        public boolean hasLaundryService() {
            return laundryService;
        }
    
        public boolean hasMessService() {
            return messService;
        }
        public void addMenu(String item) {
            messSystem.addMenu(item);
        }
    
        public void updateMenu(String item) {
            messSystem.updateMenu(item);
        }
    
        public void removeMenu(String item) {
            messSystem.removeMenu(item);
        }
    
        public boolean roomAvailability() {
            // Code to check room availability
            return false;
        }
    
        public void addService(Service service) {
            services.add(service);
        }
    
        public void removeService(Service service) {
            services.remove(service);
        }

        public String getLaundryService() {
            for (Service service : services) {
                if (service instanceof Laundary) {
                    // Assuming you have a method to get the laundry service name from your Laundary class
                    return ((Laundary) service).getname(); // Return the laundry service name
                }
            }
            return null; // Return null if hostel does not have a laundry service
        }
        
        public String getMessService() {
            for (Service service : services) {
                if (service instanceof MessSystem) {
                    // Assuming you have a method to get the laundry service name from your Laundary class
                    return ((MessSystem) service).getmenu(); // Return the laundry service name
                }
            }
            return null; // Return null if hostel does not have a laundry service
        }
    
       
}

