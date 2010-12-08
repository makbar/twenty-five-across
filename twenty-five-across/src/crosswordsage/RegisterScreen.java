package crosswordsage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import twentyfiveacross.ejbs.UserManagerRemote;
import java.security.MessageDigest;

public class RegisterScreen extends JPanel {

    private static final long serialVersionUID = 1L;

	public UserManagerRemote userManager;

	private JButton logInBtn, registerBtn;
    private JPanel mainPanel, imgPanel, formPanel;
    private JLabel nameLbl, usernameLbl, pwLbl, cartoon, bigHeader, smallHeader;
    private JTextField  nameField, usernameField, pwField;
    TfacrossGui mainScreen;

    RegisterScreen (TfacrossGui myMainScreen) {
        nameLbl = new JLabel();
        nameLbl.setText("Display name:");
        nameField = new JTextField(15);

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

        registerBtn = new JButton("Register");
        logInBtn = new JButton("Already a user? Go to Login!");

        imgPanel = new JPanel();
        imgPanel.setBackground(Color.white);
        imgPanel.add(cartoon);

        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.white);
        formPanel.add(bigHeader);
        formPanel.add(smallHeader);
        formPanel.add(nameLbl);
        formPanel.add(nameField);
        formPanel.add(usernameLbl);
        formPanel.add(usernameField);
        formPanel.add(pwLbl);
        formPanel.add(pwField);
        formPanel.add(registerBtn);
        formPanel.add(logInBtn);

        mainPanel = new JPanel(new FlowLayout());
        mainPanel.setBackground(Color.white);
        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.setAlignmentY(CENTER_ALIGNMENT);
        mainPanel.add(imgPanel);
        mainPanel.add(formPanel);

        add(mainPanel,BorderLayout.CENTER);
        logInBtn.addActionListener(new LoginListener());
        registerBtn.addActionListener(new RegisterListener());

//        setTitle("LOGIN FORM");

        mainScreen = myMainScreen;
//        mainScreen.add(mainPanel);
    }

    class LoginListener implements ActionListener
    {
        public void actionPerformed(ActionEvent a)
        {
        	mainScreen.register.setVisible(false);
			mainScreen.register.pwField.setText("");
        	mainScreen.login.setVisible(true);
        }
    }
    class RegisterListener implements ActionListener
    {
        public void actionPerformed(ActionEvent a)
        {
        	//java.util.Properties prop = System.getProperties();
        	//prop.put(Context.PROVIDER_URL, "http://localhost:8080");

            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        	try {
        		InitialContext ic = new InitialContext();
        		userManager = (UserManagerRemote) ic.lookup("twentyfiveacross.ejbs.UserManagerRemote");
        		String uName = usernameField.getText();
        		String nField = nameField.getText();
        		String pw = pwField.getText();
        		if(pw.length()<8)
        		{
        	   		mainScreen.statusbarStatusLbl.setText("Error: The password should be at least 8 characters long!");
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        	   		return;
        		}

        		MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
        		StringBuffer pwhash = new StringBuffer();
        		digest.reset();
        	    digest.update(pw.getBytes());
        	    byte[] hash = digest.digest();
        		for (int i=0;i<hash.length;i++) {
        			pwhash.append(Integer.toHexString(0xFF & hash[i]));
        		}

        	    //System.out.println("hash: " + pwhash.toString());

        		boolean test = userManager.createUser(uName, nField, pwhash.toString());

        		//if(userManager.createUser(usernameField.getText(), nameField.getText(), pwField.getText()))
        		if(test)
        		{
        	   		System.out.println("Registration Done!");
        	   		mainScreen.statusbarStatusLbl.setText("Registration Succeeded! Now Log in!");
                	mainScreen.register.setVisible(false);
	   				mainScreen.register.pwField.setText("");
                	mainScreen.login.setVisible(true);
        		}
        	   	else
        	   	{
        	   		System.out.println("Registration Failed!");
        	   		mainScreen.statusbarStatusLbl.setText("Registration Failed!");
        	   	}
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        	} catch (Exception e) {
        		System.err.println("Error!: " + e.getMessage());
        		e.printStackTrace();
        		return;
        	}
        }
    }
}
