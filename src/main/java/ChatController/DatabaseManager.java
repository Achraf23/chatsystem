package ChatController;

import ContactDiscovery.Contact;
import GUI.ChatSessionView;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static DatabaseManager db = null;
    private static final String DB_URL = "jdbc:sqlite:/tmp/history.db";
    private static final String TABLE_NAME = "Message";



    // private constructor
    private DatabaseManager() throws Exception
    {
        createTableIfNotExists();
    }

    // Static method to create instance of Singleton class
    public static synchronized DatabaseManager getInstance() throws Exception
    {
        if (db == null)
            db = new DatabaseManager();

        return db;
    }


    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL);
    }

    public void addMessageToDatabase(String msg, Contact contact, boolean is_me) throws SQLException{
        String sql = "INSERT INTO Message (ip_contact, envoye, stringMessage) VALUES (?, ?, ?)";
        Connection connection = getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql);

        // Set parameters for the PreparedStatement
        pstmt.setString(1, contact.ip());
        pstmt.setBoolean(2, is_me);
        pstmt.setString(3, msg);

        // Execute the PreparedStatement
        pstmt.executeUpdate();

        System.out.println("Message added successfully!");

    }

    public ArrayList<ChatSessionView.Message> retrieveAllMessages(Contact contact) throws SQLException{
        ArrayList<ChatSessionView.Message> messages = new ArrayList<>();
        String sql = "SELECT ip_contact, envoye, stringMessage FROM Message WHERE ip_contact= ? ORDER BY id";

        Connection connection = getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, contact.ip()); // Set the IP contact parameter from the Contact record
        ResultSet rs = pstmt.executeQuery();

        while (rs.next())
            messages.add(new ChatSessionView.Message(rs.getString("stringMessage"),rs.getBoolean("envoye")));


        return messages;
    }

    public void deleteMessagesByIp(Contact contact) {
        try (Connection connection = getConnection()) {
            String sql = "DELETE FROM Message WHERE ip_contact = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, contact.ip());

                int affectedRows = preparedStatement.executeUpdate();
                System.out.println("Deleted rows: " + affectedRows);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void createTableIfNotExists() throws Exception{
        Connection conn = null;
        Statement stmt = null;

        // Open a connection to the database (creates a new database if it doesn't exist)
        conn = DriverManager.getConnection(DB_URL);

        // Create a statement object
        stmt = conn.createStatement();

        // Check if the table exists
        ResultSet rs = conn.getMetaData().getTables(null, null, TABLE_NAME, null);
        if (!rs.next()) { // Table does not exist
            // Create the table
            String sql = "CREATE TABLE Message (" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "    ip_contact VARCHAR(255)," +
                    "    envoye BOOLEAN," +
                    "    stringMessage TEXT," +
                    "    time TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ");";
            stmt.executeUpdate(sql);
            System.out.println("Table '" + TABLE_NAME + "' created successfully.");
        } else {
            System.out.println("Table '" + TABLE_NAME + "' already exists.");
        }

        // Close resources
        rs.close();
        stmt.close();
        conn.close();


    }
}



