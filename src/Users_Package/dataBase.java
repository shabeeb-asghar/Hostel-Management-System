package Users_Package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            // Prepare SQL statement for inserting room data
            String roomSql = "INSERT INTO rooms (hostel_id, name, availability) VALUES (?, ?, ?)";
            PreparedStatement roomStatement = connection.prepareStatement(roomSql, Statement.RETURN_GENERATED_KEYS);
            
            // Prepare SQL statement for inserting bed data
            String bedSql = "INSERT INTO beds (room_id, available) VALUES (?, ?)";
            PreparedStatement bedStatement = connection.prepareStatement(bedSql);
            
            // Insert each room associated with the hostel
            for (Room room : rooms) {
                // Set values for parameters in the room prepared statement
                roomStatement.setInt(1, hostelId);
                roomStatement.setString(2, room.getName());
                roomStatement.setBoolean(3, room.isAvailable());
                
                // Execute the SQL statement to insert room data
                int roomRowsInserted = roomStatement.executeUpdate();
                if (roomRowsInserted > 0) {
                    System.out.println("Room inserted successfully for hostel: " + hostelId);
                    
                    // Retrieve the generated room ID
                    ResultSet roomGeneratedKeys = roomStatement.getGeneratedKeys();
                    if (roomGeneratedKeys.next()) {
                        int roomId = roomGeneratedKeys.getInt(1);
                        
                        // Insert beds for the room
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
        // Prepare SQL statement for inserting bed data
        String bedSql = "INSERT INTO beds (room_id, available) VALUES (?, ?)";
        PreparedStatement bedStatement = connection.prepareStatement(bedSql, Statement.RETURN_GENERATED_KEYS);
        
        // Insert each bed associated with the room
        for (Bed bed : beds) {
            // Set values for parameters in the prepared statement
            bedStatement.setInt(1, roomId);
            bedStatement.setBoolean(2, bed.isAvailable());
            
            // Execute the SQL statement to insert bed data
            int rowsInserted = bedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Bed inserted successfully for room: " + roomId);
                
                // Retrieve the generated bed ID
                ResultSet generatedKeys = bedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int bedId = generatedKeys.getInt(1);
                    
                    // Insert the bed ID into the appropriate bed table
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

private static void insertSingleSeaterBedId(Connection connection, int bedId) throws SQLException {
    String sql = "INSERT INTO single_seater_beds (bed_id) VALUES (?)";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, bedId);
    statement.executeUpdate();
}

private static void insertDoubleSeaterBedId(Connection connection, int bedId) throws SQLException {
    String sql = "INSERT INTO double_seater_beds (bed_id) VALUES (?)";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, bedId);
    statement.executeUpdate();
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


