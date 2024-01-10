package GUI;

import ContactDiscovery.Contact;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;

public class CustomizedButton extends JButton {
    Contact contact;
    CustomizedButton(Contact contact){
        super(contact.pseudo());
        this.contact = contact;
        setBackground(new Color(238,238,238));
        setFocusPainted(false);
        setFont(new Font("Tahoma", Font.BOLD, 12));
        setBorderPainted(false);

    }
}
