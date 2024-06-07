package Users_Package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Hostel_Package.Bed;
import Hostel_Package.DoubleSeater;
import Hostel_Package.Hostel;
import Hostel_Package.Room;
import Hostel_Package.SingleSeater;

public class dataBase {
 private static final String URL = "jdbc:mysql://localhost:3306/sda_project_final_db";
   
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234Qwert@";
public String geturl()
{
return URL;
}
public String getusername()
{
    return USERNAME;
}
public String getpassword()
{
    return PASSWORD;
}

public boolean validateLogintudents(String email, String password) {
    String query = "SELECT * FROM Students WHERE email = ? AND password = ?";
    
    try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(query)) {
         
        pstmt.setString(1, email);
        pstmt.setString(2, password);
        
        ResultSet resultSet = pstmt.executeQuery();
        
        return resultSet.next(); // Return true if a record is found, false otherwise
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

public void updateRoomAvailability(int roomId, boolean availability) {
    String updateQuery = "UPDATE rooms SET availability = ? WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

        pstmt.setBoolean(1, availability);
        pstmt.setInt(2, roomId);

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Room availability updated successfully.");
        } else {
            System.out.println("Room not found or no change in availability.");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
public List<Hostel> fetchHostelData() {
        List<Hostel> hostels = new ArrayList<>();
        String query = "SELECT id, name, password, location, contact_number, number_of_one_bed_rooms, number_of_two_bed_rooms, laundry_service, mess_service FROM hostels";
        String roomQuery = "SELECT id, name, availability, no_guests, capacity, price, room_no FROM rooms WHERE hostel_id = ?";
        String bedQuery = "SELECT id, available FROM beds WHERE room_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             PreparedStatement roomStmt = conn.prepareStatement(roomQuery);
             PreparedStatement bedStmt = conn.prepareStatement(bedQuery);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String location = rs.getString("location");
                String contactNumber = rs.getString("contact_number");
                int numberOfOneBedRooms = rs.getInt("number_of_one_bed_rooms");
                int numberOfTwoBedRooms = rs.getInt("number_of_two_bed_rooms");
                boolean laundryService = rs.getBoolean("laundry_service");
                boolean messService = rs.getBoolean("mess_service");

                Hostel hostel = new Hostel(name, password, location, contactNumber, numberOfOneBedRooms, numberOfTwoBedRooms, laundryService, messService);
                hostel.setId(id);
                roomStmt.setInt(1, id);
                try (ResultSet roomRs = roomStmt.executeQuery()) {
                    while (roomRs.next()) {
                        int roomId = roomRs.getInt("id");
                        String roomName = roomRs.getString("name");
                        boolean availability = roomRs.getBoolean("availability");
                        int noGuests = roomRs.getInt("no_guests");
                        int capacity = roomRs.getInt("capacity");
                        double price = roomRs.getDouble("price");
                        int roomNo = roomRs.getInt("room_no");

                        Room room = new Room(roomName, availability, noGuests, capacity, price, roomNo);

                        // Fetch beds for the room
                        bedStmt.setInt(1, roomId);
                        try (ResultSet bedRs = bedStmt.executeQuery()) {
                            while (bedRs.next()) {
                                int bedId = bedRs.getInt("id");
                                boolean bedAvailable = bedRs.getBoolean("available");

                                Bed bed = new Bed(bedId,bedAvailable);
                                room.addBed(bed);
                            }
                        }

                        hostel.addRoom(room);
                    }
                }

                hostels.add(hostel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hostels;
    }
    // Method to insert hostel data into the database
    public static void insertHostel(Hostel hostel) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Prepare SQL statement for inserting hostel data
            String sql = "INSERT INTO hostels (name, password, location, contact_number, " +
                         "number_of_one_bed_rooms, number_of_two_bed_rooms, laundry_service, mess_service) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            // Set values for parameters in the prepared statement
            statement.setString(1, hostel.getName());
            statement.setString(2, hostel.getPassword());
            statement.setString(3, hostel.getLocation());
            statement.setString(4, hostel.getContactNumber());
            statement.setInt(5, hostel.getNumberOfOneBedRooms());
            statement.setInt(6, hostel.getNumberOfTwoBedRooms());
            statement.setBoolean(7, hostel.hasLaundryService());
            statement.setBoolean(8, hostel.hasMessService());
            int hostelId=-1;
            // Execute the SQL statement to insert data
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Hostel inserted successfully.");
                  ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                hostelId = generatedKeys.getInt(1);
                // Set the generated hostel ID to the hostel object
                hostel.setId(hostelId);
                System.out.println(hostel.getRooms());
                insertRoom(connection, hostelId, hostel.getRooms());
                insertServicesForHostel( connection,  hostelId,  hostel);
            }
              
            }
        } catch (SQLException e) {
            System.err.println("Error inserting hostel: " + e.getMessage());
        }
    }
    private static void insertRoom(Connection connection, int hostelId, List<Room> rooms) {
        try {
            String roomSql = "INSERT INTO rooms (hostel_id, name, availability, no_guests, capacity, price, room_no) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement roomStatement = connection.prepareStatement(roomSql, Statement.RETURN_GENERATED_KEYS);
            
            String bedSql = "INSERT INTO beds (room_id, available) VALUES (?, ?)";
            PreparedStatement bedStatement = connection.prepareStatement(bedSql);
            
            for (Room room : rooms) {
                roomStatement.setInt(1, hostelId);
                roomStatement.setString(2, room.getName());
                roomStatement.setBoolean(3, room.isAvailable());
                roomStatement.setInt(4, room.getGuestNo());
                roomStatement.setInt(5, room.getCapacity());
                roomStatement.setDouble(6, room.getRoomPrice());
                roomStatement.setInt(7, room.getRoomNo());
    
                int roomRowsInserted = roomStatement.executeUpdate();
                if (roomRowsInserted > 0) {
                    System.out.println("Room inserted successfully for hostel: " + hostelId);
                    ResultSet roomGeneratedKeys = roomStatement.getGeneratedKeys();
                    if (roomGeneratedKeys.next()) {
                        int roomId = roomGeneratedKeys.getInt(1);
                        insertBeds(connection, roomId, room.getbeds());
                    }
                } else {
                    System.out.println("Failed to insert room for hostel: " + hostelId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error inserting rooms: " + e.getMessage());
        }
    }
    
    private static void insertBeds(Connection connection, int roomId, List<Bed> beds) {
        try {
            String bedSql = "INSERT INTO beds (room_id, available) VALUES (?, ?)";
            PreparedStatement bedStatement = connection.prepareStatement(bedSql, Statement.RETURN_GENERATED_KEYS);
            
            for (Bed bed : beds) {
                bedStatement.setInt(1, roomId);
                bedStatement.setBoolean(2, bed.isAvailable());
                
                int rowsInserted = bedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Bed inserted successfully for room: " + roomId);
                    ResultSet generatedKeys = bedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int bedId = generatedKeys.getInt(1);
                        if (bed instanceof SingleSeater) {
                            insertSingleSeaterBedId(connection, bedId);
                        } else if (bed instanceof DoubleSeater) {
                            insertDoubleSeaterBedId(connection, bedId);
                        } else {
                            System.out.println("Unsupported bed type: " + bed.getClass().getSimpleName());
                        }
                    }
                } else {
                    System.out.println("Failed to insert bed for room: " + roomId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error inserting beds: " + e.getMessage());
        }
    }
    
    private static void insertSingleSeaterBedId(Connection connection, int bedId) {
        try {
            String sql = "INSERT INTO single_seater_beds (bed_id) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bedId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting single seater bed ID: " + e.getMessage());
        }
    }
    
    private static void insertDoubleSeaterBedId(Connection connection, int bedId) {
        try {
            String sql = "INSERT INTO double_seater_beds (bed_id) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bedId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting double seater bed ID: " + e.getMessage());
        }
    }
        
   
private static void insertServicesForHostel(Connection connection, int hostelId, Hostel hostel) {
    try {
        // Insert data into the Service table
       
        
        // Check if the hostel has laundry service
        if (hostel.hasLaundryService()) {
            // Insert laundry service
            int serviceId = insertService(connection, hostelId);
            insertLaundryService(connection, serviceId, hostel.getLaundryService());
        }

        // Check if the hostel has mess service
        if (hostel.hasMessService()) {
            // Insert mess service
            int serviceId = insertService(connection, hostelId);
            insertMessService(connection, serviceId, hostel.getMessService());
        }
    } catch (SQLException e) {
        System.err.println("Error inserting services: " + e.getMessage());
    }
}

private static int insertService(Connection connection, int hostelId) throws SQLException {
    // Prepare SQL statement for inserting service data
    String sql = "INSERT INTO Service (hostel_id) VALUES (?)";
    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    statement.setInt(1, hostelId);
    statement.executeUpdate();
    
    // Retrieve the generated service ID
    ResultSet generatedKeys = statement.getGeneratedKeys();
    if (generatedKeys.next()) {
        return generatedKeys.getInt(1);
    } else {
        throw new SQLException("Failed to insert service, no ID obtained.");
    }
}

private static void insertLaundryService(Connection connection, int serviceId, String laundryName) throws SQLException {
    // Prepare SQL statement for inserting laundry service
    String sql = "INSERT INTO laundary_services (Service_id, laundry_name) VALUES (?, ?)";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, serviceId);
    statement.setString(2, laundryName);
    statement.executeUpdate();
    System.out.println("Laundry service inserted successfully for service: " + serviceId);
}

private static void insertMessService(Connection connection, int serviceId, String menuItem) throws SQLException {
    // Prepare SQL statement for inserting mess service
    String sql = "INSERT INTO mess_systems (Service_id, menu_item) VALUES (?, ?)";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, serviceId);
    statement.setString(2, menuItem);
    statement.executeUpdate();
    System.out.println("Mess service inserted successfully for service: " + serviceId);
}


}


