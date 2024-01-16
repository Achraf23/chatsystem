package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginView extends JPanel {
    private JTextField usernameField;
    private JButton connectButton;
    ArrayList<Observer> observers;

    public interface Observer{
        void connectClicked(String username);
    }

    public LoginView() {
        observers = new ArrayList<Observer>();
        setLayout(new GridBagLayout());

        // Create and configure the label for "Welcome to the Chat System!"
        JLabel welcomeLabel = new JLabel("Welcome to the Chat System!");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER); // Center align the label
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Create and configure the label for "Username"
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setHorizontalAlignment(JLabel.RIGHT); // Align the label to the right

        // Create and configure the username text field
        usernameField = new JTextField(20);
        usernameField.setMaximumSize(new Dimension(200, 30));

        // Create and configure the connect button
        connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle connect button click event
                String username = usernameField.getText();
                for(Observer observer : observers)
                    observer.connectClicked(username);
            }
        });

        // Add components to the panel using GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        gbc.insets = new Insets(0, 0, 10, 0); // Add space above the welcomeLabel
        add(welcomeLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0); // Add space above the usernameField
        add(usernameLabel, gbc);

        gbc.gridy = 2;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across two columns
        gbc.insets = new Insets(0, 0, 0, 0); // Reset insets for the connectButton
        add(connectButton, gbc);
    }

    public synchronized void addObserver(Observer obs){observers.add(obs);}

}
