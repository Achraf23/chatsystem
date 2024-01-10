package ChatController;

import ContactDiscovery.Contact;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static DatabaseManager db = null;
    private static final String DB_URL = "jdbc:sqlite:history.db";

    public static void main(String[] args) throws SQLException{
        DatabaseManager db = DatabaseManager.getInstance();

//        db.retrieveAllMessages(new Contact("pseudo","ip"));
        db.printAllMessages(new Contact("pseudo","0.0.0.0"));

    }


    // private constructor
    private DatabaseManager()
    {
    }

    // Static method to create instance of Singleton class
    public static synchronized DatabaseManager getInstance()
    {
        if (db == null)
            db = new DatabaseManager();

        return db;
    }


    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL);
    }

    public void addMessageToDatabase(String msg, Contact contact, boolean sender) throws SQLException{
        String sql = "INSERT INTO Message (ip_contact, envoye, stringMessage) VALUES (?, ?, ?)";
        Connection connection = getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql);

        // Set parameters for the PreparedStatement
        pstmt.setString(1, contact.ip());
        pstmt.setBoolean(2, sender);
        pstmt.setString(3, msg);

        // Execute the PreparedStatement
        pstmt.executeUpdate();

        System.out.println("Message added successfully!");

    }

    public ArrayList<String> retrieveAllMessages(Contact contact) throws SQLException{
        ArrayList<String> messages = new ArrayList<>();
        String sql = "SELECT ip_contact, envoye, stringMessage FROM Message WHERE ip_contact= ? ORDER BY id";

        Connection connection = getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, contact.ip()); // Set the IP contact parameter from the Contact record
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            // Constructing a message string format
            String messageString =
                    ", IP Contact: " + rs.getString("ip_contact") +
                    ", Envoye: " + rs.getBoolean("envoye") +
                    ", Message: " + rs.getString("stringMessage");

            messages.add(messageString);
        }

        return messages;
    }

    //FOR DEBUGGING
    public void printAllMessages(Contact contact) throws SQLException{
        ArrayList<String> messages = retrieveAllMessages(contact);

        if (messages.isEmpty()) {
            System.out.println("No messages found in the database.");
        } else {
            System.out.println("List of Messages:");
            for (String message : messages) {
                System.out.println(message);
            }
        }
    }
}

