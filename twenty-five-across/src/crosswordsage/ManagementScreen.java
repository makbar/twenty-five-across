package crosswordsage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import twentyfiveacross.ejbs.UserManagerRemote;

public class ManagementScreen extends JPanel {

    private static final long serialVersionUID = 1L;

    String userlist[];// = { "userA", "userB", "userC", "userD", "userE", "userF", "userG", "userH", "userI", "userJ" };
    List<String> usersL;

    public UserManagerRemote userManager;
    
	private JButton banBtn, unBanBtn;
	private JList usersLst;
	private JPanel mainPanel, imgPanel, formPanel;
    private JLabel cartoon, bigHeader, smallHeader, title;
    TfacrossGui mainScreen;

    ManagementScreen (TfacrossGui myMainScreen) {
    	
    	java.util.Properties prop = System.getProperties();
    	prop.put(Context.PROVIDER_URL, "http://localhost:8080");

    	try {
    		InitialContext ic = new InitialContext();
    		userManager = (UserManagerRemote) ic.lookup("UserManager");
    		
    		usersL = userManager.listUsers();
    		
    	} catch (Exception e) {
    		System.err.println("Error!: " + e.getMessage());
    		e.printStackTrace();
    	}

    	userlist = (String[]) (Object[])usersL.toArray(new String[0]);
    	usersLst = new JList(userlist);
        usersLst.setVisibleRowCount(5);
        
        cartoon = new JLabel(new ImageIcon("noclue.jpg"));
        
        title = new JLabel();

        bigHeader = new JLabel("Welcome to 25 Across!");
        bigHeader.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        bigHeader.setForeground(new Color(15,124,244));

        smallHeader = new JLabel("Management Screen");
        smallHeader.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        smallHeader.setForeground(new Color(15,124,244));
        smallHeader.setVerticalTextPosition(JLabel.TOP);
        smallHeader.setPreferredSize(new Dimension(60, 60));
        
        banBtn = new JButton("Ban");
        unBanBtn = new JButton("UnBan");

        imgPanel = new JPanel();
        imgPanel.setBackground(Color.white);
        imgPanel.add(cartoon);

        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.white);
        formPanel.add(bigHeader);
        formPanel.add(smallHeader);
        formPanel.add(title);
        formPanel.add(usersLst);
        formPanel.add(usersLst);
        formPanel.add(banBtn);
        formPanel.add(unBanBtn);
        
        mainPanel = new JPanel(new FlowLayout());
        mainPanel.setBackground(Color.white);
        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.setAlignmentY(CENTER_ALIGNMENT);
        mainPanel.add(imgPanel);
        mainPanel.add(formPanel);

        add(mainPanel,BorderLayout.CENTER);

        banBtn.addActionListener(new BanListener());
        unBanBtn.addActionListener(new UnBanListener());

        
        mainScreen = myMainScreen;
    }

    class BanListener implements ActionListener
    {
        public void actionPerformed(ActionEvent a)
        {
        	try {
        		InitialContext ic = new InitialContext();
        		userManager = (UserManagerRemote) ic.lookup("UserManager");
        		
        		if(null==usersLst.getSelectedValue())
        			return;
        		
        		if(userManager.banUser(usersLst.getSelectedValue().toString()))
        		{
                	System.out.println("Ban "+usersLst.getSelectedValue().toString());
                	mainScreen.statusbarStatusLbl.setText("User "+usersLst.getSelectedValue().toString()+" Banned");
        		}
        		
        	} catch (Exception e) {
        		System.err.println("Error!: " + e.getMessage());
        		e.printStackTrace();
        	}
        }
    }
    class UnBanListener implements ActionListener
    {
        public void actionPerformed(ActionEvent a)
        {
        	try {
        		InitialContext ic = new InitialContext();
        		userManager = (UserManagerRemote) ic.lookup("UserManager");
        		
        		if(null==usersLst.getSelectedValue())
        			return;
        		
        		if(userManager.unBanUser(usersLst.getSelectedValue().toString()))
        		{
                	System.out.println("Ban "+usersLst.getSelectedValue().toString());
                	mainScreen.statusbarStatusLbl.setText("User "+usersLst.getSelectedValue().toString()+" Banned");
        		}
        		
        	} catch (Exception e) {
        		System.err.println("Error!: " + e.getMessage());
        		e.printStackTrace();
        	}
        }
    }
}
