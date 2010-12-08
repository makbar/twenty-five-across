package crosswordsage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import twentyfiveacross.ejbs.UserManagerRemote;

public class LoginScreen extends JPanel {

    private static final long serialVersionUID = 1L;

	private JButton logInBtn, registerBtn, connectBtn;
    private JPanel mainPanel, imgPanel, formPanel;
    private JLabel usernameLbl, pwLbl, cartoon, bigHeader, smallHeader, serverLbl;
    private JTextField  usernameField, pwField, serverField;
    TfacrossGui mainScreen;

	public UserManagerRemote userManager;

    LoginScreen (TfacrossGui myMainScreen) {
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
        registerBtn = new JButton("New User? Go to Registration!");
        connectBtn = new JButton("Connect");

        serverLbl = new JLabel();
        serverLbl.setText("Server:");
        serverField = new JTextField(15);
        serverField.setText("localhost");

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
        formPanel.add(registerBtn);
        formPanel.add(serverLbl);
        formPanel.add(serverField);
        formPanel.add(connectBtn);


        mainPanel = new JPanel(new FlowLayout());
        mainPanel.setBackground(Color.white);
        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.setAlignmentY(CENTER_ALIGNMENT);
        mainPanel.add(imgPanel);
        mainPanel.add(formPanel);

        add(mainPanel,BorderLayout.CENTER);
        logInBtn.addActionListener(new LoginListener());
        registerBtn.addActionListener(new RegisterListener());

        connectBtn.addActionListener(new ConnectListener());
        pwField.setEnabled(false);
        usernameField.setEnabled(false);
        logInBtn.setEnabled(false);
        registerBtn.setEnabled(false);

        mainScreen = myMainScreen;
    }

    class LoginListener implements ActionListener
    {
        public void actionPerformed(ActionEvent a)
        {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        	try {
        		InitialContext ic = new InitialContext();
        		userManager = (UserManagerRemote) ic.lookup("twentyfiveacross.ejbs.UserManagerRemote");

        		String uName = usernameField.getText();
        		String uPass = pwField.getText();

        		MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
        		StringBuffer pwhash = new StringBuffer();
        		digest.reset();
        	    digest.update(uPass.getBytes());
        	    byte[] hash = digest.digest();
        		for (int i=0;i<hash.length;i++) {
        			pwhash.append(Integer.toHexString(0xFF & hash[i]));
        		}

        	    System.out.println("hash: " + pwhash.toString());

        		if(userManager.checkLogin(uName, pwhash.toString()))
        		{
        	   		System.out.println("Password Correct!");
        	   		if("root".equals(uName))
        	   		{
        	   			mainScreen.statusbarStatusLbl.setText("Welcome root. User Management Screen loaded!");
        	   			mainScreen.login.setVisible(false);
    	   				mainScreen.login.pwField.setText("");
        	   			mainScreen.manager = new ManagementScreen(mainScreen,pwhash.toString());
    	   				mainScreen.mainPanel.add(mainScreen.manager);
        	   			mainScreen.manager.setVisible(true);
        	   		}
        	   		else
        	   		{
        	   			if(userManager.checkBan(uName))
        	   				mainScreen.statusbarStatusLbl.setText("You are banned!");
        	   			else
        	   			{
        	   				mainScreen.statusbarStatusLbl.setText("Welcome! Game List Screen loaded!");
        	   				mainScreen.login.setVisible(false);
        	   				mainScreen.login.pwField.setText("");
        	   				mainScreen.lister = new GameListScreen(mainScreen);
        	   				mainScreen.mainPanel.add(mainScreen.lister);
        	   				mainScreen.lister.setVisible(true);
        	   			}
        	   		}
        		}
        	   	else
        	   	{
        	   		System.out.println("Password Incorrect!");
        	   		mainScreen.statusbarStatusLbl.setText("Login Failed!");
        	   	}

                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        	} catch (Exception e) {
        		mainScreen.statusbarStatusLbl.setText("Connection to server failed. Check address.");
        		System.err.println("Error!: " + e.getMessage());
        		e.printStackTrace();
        		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        	}
        }
    }
    class RegisterListener implements ActionListener
    {
        public void actionPerformed(ActionEvent a)
        {
        	mainScreen.login.setVisible(false);
			mainScreen.login.pwField.setText("");
        	mainScreen.register.setVisible(true);
        }
    }
    class ConnectListener implements ActionListener
    {
        public void actionPerformed(ActionEvent a)
        {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        	java.util.Properties prop = System.getProperties();
        	prop.put(Context.PROVIDER_URL, "http://" + serverField.getText() + ":8080");
        	prop.setProperty("org.omg.CORBA.ORBInitialHost", serverField.getText());

        	try {
        		mainScreen.statusbarStatusLbl.setText("Trying to connect to " + prop.getProperty("org.omg.CORBA.ORBInitialHost"));
        		InitialContext ic = new InitialContext();
        		userManager = (UserManagerRemote) ic.lookup("twentyfiveacross.ejbs.UserManagerRemote");

        		mainScreen.statusbarStatusLbl.setText("Connection to server succeeded! Please Log in.");
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        		pwField.setEnabled(true);
                usernameField.setEnabled(true);
                logInBtn.setEnabled(true);
                registerBtn.setEnabled(true);
                serverField.setEnabled(false);
                connectBtn.setEnabled(false);

        	} catch (Exception e) {
        		mainScreen.statusbarStatusLbl.setText("Connection to server failed. Check address.");
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        		System.err.println("Error!: " + e.getMessage());
        		e.printStackTrace();
        		return;
        	}
        }
    }
}
