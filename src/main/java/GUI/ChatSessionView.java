package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChatSessionView extends JPanel implements ActionListener {

    JPanel conversationPanel;
    ArrayList<String> messages;
    JTextField textField;

    ChatSessionView(String contact){
        super();
        setLayout(new BorderLayout());

        messages = new ArrayList<String>();

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


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        messages.add(textField.getText());
        conversationPanel.add(new JLabel("[Me]: "+textField.getText()));
        this.revalidate();
        this.repaint();

    }
}
