package crosswordsage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.InitialContext;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import twentyfiveacross.ejbs.GameManagerRemote;

public class GameListScreen extends JPanel {

    private static final long serialVersionUID = 1L;

    String gamelist[];

	public GameManagerRemote gameManager;
    
	private JButton playBtn=null;
	private JButton newBtn=null;
	private JList gameLst=null;
    private JButton logoutBtn = new JButton();
	private JPanel mainPanel, imgPanel, formPanel;
    private JLabel cartoon, bigHeader, smallHeader, title;
    TfacrossGui mainScreen;
    
	DefaultListModel model = new DefaultListModel();
	
    GameListScreen (TfacrossGui myMainScreen) {
    	
        playBtn = new JButton("Play Game");
        newBtn = new JButton("Create New Game");
        logoutBtn = new JButton("Logout");

    	gamelist = null;
    	    	
        cartoon = new JLabel(new ImageIcon("noclue.jpg"));
        
        title = new JLabel();

        bigHeader = new JLabel("Welcome to 25 Across!");
        bigHeader.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        bigHeader.setForeground(new Color(15,124,244));

        smallHeader = new JLabel("List of Games!");
        smallHeader.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        smallHeader.setForeground(new Color(15,124,244));
        smallHeader.setVerticalTextPosition(JLabel.TOP);
        smallHeader.setPreferredSize(new Dimension(60, 60));
        
        imgPanel = new JPanel();
        imgPanel.setBackground(Color.white);
        imgPanel.add(cartoon);

        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.white);
        formPanel.add(bigHeader);
        formPanel.add(smallHeader);
        formPanel.add(title);
        //formPanel.add(gameLst);
        //formPanel.add(playBtn);
        //formPanel.add(newBtn);
        updateGameList();

        mainPanel = new JPanel(new FlowLayout());
        mainPanel.setBackground(Color.white);
        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.setAlignmentY(CENTER_ALIGNMENT);
        mainPanel.add(imgPanel);
        mainPanel.add(formPanel);

        add(mainPanel,BorderLayout.CENTER);

        playBtn.addActionListener(new PlayListener());
        newBtn.addActionListener(new NewListener());
        logoutBtn.addActionListener(new LogoutListener());

        
        mainScreen = myMainScreen;
    }
    
    void updateGameList()
    {
    	try {
    		InitialContext ic = new InitialContext();
    		//gameManager = (GameManagerRemote) ic.lookup("java:global/twenty-five-across/GameManager");
    		gameManager = (GameManagerRemote) ic.lookup("twentyfiveacross.ejbs.GameManagerRemote");
    		
    		String[] listGamesStr = gameManager.listGames();
    		if(null==listGamesStr)
    			gamelist = null;
    		else
    			gamelist = listGamesStr.clone();
    		
    		if(null!=gameLst)
    		{
        		playBtn.setEnabled(true);
    			formPanel.remove(gameLst);
    			formPanel.remove(playBtn);
    			formPanel.remove(newBtn);
    			formPanel.remove(logoutBtn);
    			gameLst=null;
    		}
    		if (null!=gamelist) {
        		gameLst = new JList(gamelist);
        	}
        	else {
        		gameLst = new JList();
        		playBtn.setEnabled(false);
        	}
        	gameLst.setVisibleRowCount(5);
            formPanel.add(gameLst);
            formPanel.add(playBtn);
            formPanel.add(newBtn);
            formPanel.add(logoutBtn);
        	    		
    	} catch (Exception e) {
    		System.err.println("Error!: " + e.getMessage());
    		e.printStackTrace();
    	}
    }

    class PlayListener implements ActionListener
    {
        public void actionPerformed(ActionEvent a)
        {
        	try {
        		InitialContext ic = new InitialContext();
        		//gameManager = (GameManagerRemote) ic.lookup("java:global/twenty-five-across/GameManager");
        		gameManager = (GameManagerRemote) ic.lookup("twentyfiveacross.ejbs.GameManagerRemote");
        		
        		if(null==gameLst.getSelectedValue())
        			return;
        		
        		mainScreen.lister.setVisible(false);
        		int loadThisGame = Integer.parseInt(gameLst.getSelectedValue().toString());
        		if(null!=mainScreen.solverScreen)
        		{
        			mainScreen.mainPanel.remove(mainScreen.solverScreen);
        			mainScreen.solverScreen = null;
        		}
        		Crossword c = gameManager.getCrossword(loadThisGame);
//        		mainScreen.mainPanel.removeAll();
        		CrosswordSolver cs = new CrosswordSolver(c, gameManager, loadThisGame, mainScreen);
        		mainScreen.mainPanel.add(cs);
        		mainScreen.solverScreen = cs;
        		mainScreen.validate();
        		
        	} catch (Exception e) {
        		System.err.println("Error!: " + e.getMessage());
        		e.printStackTrace();
        	}
        }
    }
    class NewListener implements ActionListener
    {
        public void actionPerformed(ActionEvent a)
        {
        	try {
        		InitialContext ic = new InitialContext();
        		//gameManager = (GameManagerRemote) ic.lookup("java:global/twenty-five-across/GameManager");
        		gameManager = (GameManagerRemote) ic.lookup("twentyfiveacross.ejbs.GameManagerRemote");
        		mainScreen.lister.setVisible(false);
        		int loadThisGame = gameManager.newGame();
        		Crossword c = gameManager.getCrossword(loadThisGame);
        		if(null!=mainScreen.solverScreen)
        		{
        			mainScreen.mainPanel.remove(mainScreen.solverScreen);
        			mainScreen.solverScreen = null;
        		}
//        		mainScreen.mainPanel.removeAll();
        		CrosswordSolver cs = new CrosswordSolver(c, gameManager, loadThisGame, mainScreen);
        		mainScreen.mainPanel.add(cs);
        		mainScreen.solverScreen = cs;
        		mainScreen.validate();
        		
        	} catch (Exception e) {
        		System.err.println("Error!: " + e.getMessage());
        		e.printStackTrace();
        	}
        }
    }
    class LogoutListener implements ActionListener
    {
        public void actionPerformed(ActionEvent a)
        {
        	try {
        		mainScreen.lister.setVisible(false);
        		mainScreen.login.setVisible(true);
            	mainScreen.validate();
            	mainScreen.mainPanel.validate();
        	} catch (Exception e) {
        		System.err.println("Error!: " + e.getMessage());
        		e.printStackTrace();
        	}
        }
    }
}
