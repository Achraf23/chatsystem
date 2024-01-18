package GUI;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;


public class UsernamePanel extends JPanel {
    public JButton createIconButton;
    private JLabel nickname;

    public UsernamePanel(String username) {

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        //Panel containing username label
        JPanel usernamePanel = new JPanel();
        nickname = new JLabel(username);
        nickname.setForeground(new Color( 	0, 0, 139));
        usernamePanel.add(nickname);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        createIconButton = new JButton("Change Username");
        createIconButton.setLayout(new FlowLayout());


        buttonPanel.add(createIconButton);

        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        //Adding username and button to class panel
        add(usernamePanel);
        add(buttonPanel);
    }

    public void setUsername(String username){
        nickname.setText(username);
    }

}
