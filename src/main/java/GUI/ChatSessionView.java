package GUI;

import ContactDiscovery.Contact;
import TCP.TCPClient;
import TCP.TCPMessage;
import TCP.TCPServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class ChatSessionView extends JPanel implements ActionListener {

    JPanel conversationPanel;
    ArrayList<String> messages;
    JTextField textField;
    Contact contact;
    ArrayList<Observer> observers;

    public interface Observer{
        void sentMessage(String msg,Contact recipient);
    }

    ChatSessionView(Contact contact){
        super();
        setLayout(new BorderLayout());

        messages = new ArrayList<String>();

        this.contact = contact;

        conversationPanel = new JPanel();
        conversationPanel.setLayout(new BoxLayout(conversationPanel,BoxLayout.Y_AXIS));

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom,BoxLayout.X_AXIS));
        textField = new JTextField(10);
        bottom.add(textField);

        JButton sendButton = new JButton("send");
        sendButton.setBackground(new Color(59, 89, 182));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setFont(new Font("Tahoma", Font.BOLD, 12));

        sendButton.addActionListener(this);
        bottom.add(sendButton);

        //create some space
        bottom.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

        this.add(bottom,BorderLayout.SOUTH);
        this.add(conversationPanel);

        observers = new ArrayList<ChatSessionView.Observer>();

    }

    public synchronized void addObserver(ChatSessionView.Observer obs){this.observers.add(obs);}

    @Override
    public void actionPerformed(ActionEvent e) {
        addMessageToConversation(textField.getText(),"Me");
        for(Observer obs : observers)
            obs.sentMessage(textField.getText(),contact);
    }

    public void addMessageToConversation(String msg,String sender){
        this.messages.add(msg);
        conversationPanel.add(new JLabel("["+sender+"]: "+msg));
        this.revalidate();
        this.repaint();
    }

}
