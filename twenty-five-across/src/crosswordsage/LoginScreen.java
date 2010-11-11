package crosswordsage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginScreen extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JButton logInBtn;
    private JPanel mainPanel, imgPanel, formPanel;
    private JLabel usernameLbl, pwLbl, cartoon, bigHeader, smallHeader;
    private JTextField  usernameField, pwField;

    LoginScreen () {
        usernameLbl = new JLabel();
        usernameLbl.setText("Username:");
        usernameField = new JTextField(15);

        pwLbl = new JLabel();
        pwLbl.setText("Password:");
        pwField = new JPasswordField(15);

        cartoon = new JLabel(new ImageIcon("noclue.jpg"));

        bigHeader = new JLabel("Welcome to 25 Across!");
        bigHeader.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        bigHeader.setForeground(new Color(15,124,244));

        smallHeader = new JLabel("Do you have a clue?");
        smallHeader.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        smallHeader.setForeground(new Color(15,124,244));
        smallHeader.setVerticalTextPosition(JLabel.TOP);
        smallHeader.setPreferredSize(new Dimension(60, 60));

        logInBtn = new JButton("Log In");

        imgPanel = new JPanel();
        imgPanel.setBackground(Color.white);
        imgPanel.add(cartoon);

        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.white);
        formPanel.add(bigHeader);
        formPanel.add(smallHeader);
        formPanel.add(usernameLbl);
        formPanel.add(usernameField);
        formPanel.add(pwLbl);
        formPanel.add(pwField);
        formPanel.add(logInBtn);

        mainPanel = new JPanel(new FlowLayout());
        mainPanel.setBackground(Color.white);
        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.setAlignmentY(CENTER_ALIGNMENT);
        mainPanel.add(imgPanel);
        mainPanel.add(formPanel);

        add(mainPanel,BorderLayout.CENTER);
        logInBtn.addActionListener(this);

        setTitle("LOGIN FORM");
    }

    public void actionPerformed(ActionEvent ae) {
        //TODO
    }
}
