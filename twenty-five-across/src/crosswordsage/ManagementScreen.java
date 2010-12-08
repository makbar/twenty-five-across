package crosswordsage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.naming.InitialContext;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import twentyfiveacross.ejbs.UserManagerRemote;

public class ManagementScreen extends JPanel {

    private static final long serialVersionUID = 1L;

    String userlist[];
    List<String> usersL;

    public UserManagerRemote userManager;

	private JButton banBtn, unBanBtn;
	private JList usersLst;
	private JList banLst;
	private JPanel mainPanel, imgPanel, formPanel, titlePanel, listPanel, btnPanel;
    private JLabel cartoon, bigHeader, smallHeader, title;
    TfacrossGui mainScreen;

	DefaultListModel model = new DefaultListModel();

	String banStr = new String("(Banned)");
	String actStr = new String("(Active)");

	String rootToken;

    ManagementScreen (TfacrossGui myMainScreen, String token) {

    	rootToken = token;

    	updateUserList();

        cartoon = new JLabel(new ImageIcon("noclue.jpg"));

        title = new JLabel();

        bigHeader = new JLabel("Welcome to 25 Across!");
        bigHeader.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        bigHeader.setForeground(new Color(15,124,244));

        smallHeader = new JLabel("Management Screen");
        smallHeader.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        smallHeader.setForeground(new Color(15,124,244));
        //smallHeader.setVerticalTextPosition(JLabel.TOP);
        //smallHeader.setPreferredSize(new Dimension(60, 60));

        banBtn = new JButton("Ban");
        unBanBtn = new JButton("UnBan");

        imgPanel = new JPanel();
        imgPanel.setBackground(Color.white);
        imgPanel.add(cartoon);

        titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Color.white);
        titlePanel.setAlignmentX(CENTER_ALIGNMENT);
        titlePanel.setAlignmentY(CENTER_ALIGNMENT);
        titlePanel.add(bigHeader);
        titlePanel.add(smallHeader);
        titlePanel.add(title);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.X_AXIS));
        listPanel.setBackground(Color.white);
        listPanel.add(usersLst);
        listPanel.add(banLst);

        btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.setBackground(Color.white);
        btnPanel.add(banBtn);
        btnPanel.add(unBanBtn);

        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.white);
        formPanel.setAlignmentX(CENTER_ALIGNMENT);
        formPanel.setAlignmentY(CENTER_ALIGNMENT);
        formPanel.add(titlePanel);
        formPanel.add(listPanel);
        formPanel.add(btnPanel);

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

    void updateUserList()
    {
    	try {
    		InitialContext ic = new InitialContext();
    		//userManager = (UserManagerRemote) ic.lookup("UserManager");
    		userManager = (UserManagerRemote) ic.lookup("twentyfiveacross.ejbs.UserManagerRemote");

    		usersL = userManager.listUsers(rootToken);
    		if(null!=usersL)
    		{
    			userlist = (String[]) (Object[])usersL.toArray(new String[0]);
    	    	usersLst = new JList(userlist);
    	        updateLists();
    	        usersLst.setVisibleRowCount(5);
    	        banLst.setVisibleRowCount(5);
    		}
    		else
    		{
    	    	usersLst = new JList();
    	    	banLst = new JList();
    		}


    	} catch (Exception e) {
    		System.err.println("Error!: " + e.getMessage());
    		e.printStackTrace();
    	}
    }

    void updateLists()
    {
    	try {
    		InitialContext ic = new InitialContext();
    		//userManager = (UserManagerRemote) ic.lookup("UserManager");
    		userManager = (UserManagerRemote) ic.lookup("twentyfiveacross.ejbs.UserManagerRemote");

        	model.removeAllElements();

        	for(int i=0; i<userlist.length; i++)
            {
            	if(userManager.checkBan(userlist[i]))
            		model.add(i,banStr);
            	else
            		model.add(i,actStr);
            }
        	banLst = new JList(model);
        	banLst.setEnabled(false);

    	} catch (Exception e) {
    		System.err.println("Error!: " + e.getMessage());
    		e.printStackTrace();
    	}
    }

    class BanListener implements ActionListener
    {
        public void actionPerformed(ActionEvent a)
        {
        	try {
        		InitialContext ic = new InitialContext();
        		//userManager = (UserManagerRemote) ic.lookup("UserManager");
        		userManager = (UserManagerRemote) ic.lookup("twentyfiveacross.ejbs.UserManagerRemote");

        		if(null==usersLst.getSelectedValue())
        			return;

        		if(userManager.banUser(usersLst.getSelectedValue().toString(),rootToken))
        		{
                	System.out.println("Ban "+usersLst.getSelectedValue().toString());
                	mainScreen.statusbarStatusLbl.setText("User "+usersLst.getSelectedValue().toString()+" Banned");
        		}

        		updateLists();

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
        		//userManager = (UserManagerRemote) ic.lookup("UserManager");
        		userManager = (UserManagerRemote) ic.lookup("twentyfiveacross.ejbs.UserManagerRemote");

        		if(null==usersLst.getSelectedValue())
        			return;

        		if(userManager.unBanUser(usersLst.getSelectedValue().toString(),rootToken))
        		{
                	System.out.println("Ban "+usersLst.getSelectedValue().toString());
                	mainScreen.statusbarStatusLbl.setText("User "+usersLst.getSelectedValue().toString()+" Banned");
        		}

        		updateLists();

        	} catch (Exception e) {
        		System.err.println("Error!: " + e.getMessage());
        		e.printStackTrace();
        	}
        }
    }
}
