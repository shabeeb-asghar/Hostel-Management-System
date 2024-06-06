package Hostel_Package;
import java.util.ArrayList;
import java.util.List;

import Setting_Package.Discounts;
import Setting_Package.Feedback;

public class Hostel { //controller

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
        private void initializeRooms() {
            for (int i = 0; i < numberOfOneBedRooms; i++) {
                Room room = new Room();
                room.addBed(new SingleSeater());
                rooms.add(room);
            }
            for (int i = 0; i < numberOfTwoBedRooms; i++) {
                Room room = new Room();
                room.addBed(new DoubleSeater());
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
}

