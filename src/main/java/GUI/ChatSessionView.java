package GUI;

import ContactDiscovery.Contact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.InetAddress;
import java.util.ArrayList;

public class ChatSessionView extends JPanel implements ActionListener {

    JPanel conversationPanel;
    ArrayList<Message> messages;
    JTextField textField;
    Contact contact;
    ArrayList<Observer> observers;


    public interface Observer{
        void sentMessage(String msg,Contact recipient);
    }

    public record Message(String content, boolean is_me){
    }

    ChatSessionView(Contact contact){
        super();
        setLayout(new BorderLayout());

        messages = new ArrayList<Message>();

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
        addMessageToConversation(new Message(textField.getText(),true));
        for(Observer obs : observers)
            obs.sentMessage(textField.getText(),contact);
    }

    public void addMessageToConversation(Message message){
        this.messages.add(message);
        if(message.is_me())
            conversationPanel.add(new JLabel("[Me]:" +message.content()));
        else
            conversationPanel.add(new JLabel("["+contact.pseudo()+"]:"+message.content()));

        this.revalidate();
        this.repaint();
    }

}
