package crosswordsage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TfacrossGui extends JFrame {

	private static final long serialVersionUID = 1L;

	JPanel toolbar = new JPanel();
	JPanel mainPanel = new JPanel();
	JPanel statusbar = new JPanel();
	
	JLabel toolbarLbl = new JLabel("");
    JLabel icon = new JLabel(new ImageIcon("noclue.jpg"));

	JLabel statusbarStatusLbl = new JLabel(" Welcome to Twenty Five Across! ");


	static TfacrossGui gui;
	LoginScreen login = new LoginScreen(this);
	RegisterScreen register = new RegisterScreen(this);
	ManagementScreen manager=null;
	GameListScreen lister=null;
	CrosswordSolver solverScreen=null;


	BorderLayout borderLayout = new BorderLayout();

	public TfacrossGui()
	{
		try
		{
			System.out.println("Loading the GUI!");

			jbInit();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		gui = new TfacrossGui();
	}

	private void jbInit()
	throws Exception
	{
		showReset();

		this.setVisible(true);

		toolbar.add(toolbarLbl);
		toolbar.setVisible(true);
		
		mainPanel.add(login);
		mainPanel.add(register);
		
		login.setVisible(true);
		mainPanel.setVisible(true);
		
		statusbar.add(statusbarStatusLbl);
		statusbar.setVisible(true);

		validate();
		
	}

	public void showReset()
	{
		this.getContentPane().setBackground(SystemColor.control);
		this.setTitle("Twenty Five Across");
		this.getContentPane().setLayout(borderLayout);
		this.setBounds(0, 0, 800, 700);
		this.setLocation(250, 20);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.getContentPane().add(toolbar, java.awt.BorderLayout.NORTH);
		this.getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);
		this.getContentPane().add(statusbar, java.awt.BorderLayout.SOUTH);
		
		statusbarStatusLbl.setForeground(Color.RED);

		toolbar.setBorder(BorderFactory.createLineBorder(Color.black));
		toolbar.setBounds(0,0,700,100);

		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		mainPanel.setBounds(0, 100, 800, 500);
		mainPanel.setBackground(Color.WHITE);
		 
		toolbar.setBorder(BorderFactory.createLineBorder(Color.black));
		toolbar.setBounds(0,600,700,100);
		
		login.setVisible(false);
		register.setVisible(false);
	}

	public void serverLost() {
		register.setVisible(false);
		if(null!=manager) { manager.setVisible(false); }
		if(null!=lister)  { lister.setVisible(false); }
		if(null!=solverScreen) { solverScreen.setVisible(false); }
		login.setVisible(true);
		statusbarStatusLbl.setText("Connection to server lost.  Please check connection and try again.");
		/* Also, set the state to the login state */ 
	}
}
