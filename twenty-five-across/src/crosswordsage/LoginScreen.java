package crosswordsage;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginScreen extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JButton logInBtn;
    private JPanel panel;
    private JLabel usernameLbl, pwLbl;
    private JTextField  usernameField, pwField;

    LoginScreen () {
        usernameLbl = new JLabel();
        usernameLbl.setText("Username:");
        usernameField = new JTextField(15);

        pwLbl = new JLabel();
        pwLbl.setText("Password:");
        pwField = new JPasswordField(15);

        logInBtn = new JButton("Log In");

        panel = new JPanel(new GridLayout(3,10));
        panel.add(usernameLbl);
        panel.add(usernameField);
        panel.add(pwLbl);
        panel.add(pwField);
        panel.add(logInBtn);
        add(panel,BorderLayout.CENTER);
        logInBtn.addActionListener(this);
        setTitle("LOGIN FORM");
    }

    public void actionPerformed(ActionEvent ae) {
        //TODO
    }
}

//class Login extends JFrame implements ActionListener
//{
// JButton SUBMIT;
// JPanel panel;
// JLabel label1,label2;
// final JTextField  text1,text2;
//  Login()
//  {
//    label1 = new JLabel();
//    label1.setText("Username:");
//    text1 = new JTextField(15);
//
//    label2 = new JLabel();
//    label2.setText("Password:");
//      text2 = new JPasswordField(15);
//
//    SUBMIT=new JButton("SUBMIT");
//
//    panel=new JPanel(new GridLayout(3,1));
//    panel.add(label1);
//    panel.add(text1);
//    panel.add(label2);
//    panel.add(text2);
//    panel.add(SUBMIT);
//    add(panel,BorderLayout.CENTER);
//    SUBMIT.addActionListener(this);
//    setTitle("LOGIN FORM");
//  }
//   public void actionPerformed(ActionEvent ae)
//  {
//    String value1=text1.getText();
//    String value2=text2.getText();
//        if (value1.equals("roseindia") && value2.equals("roseindia")) {
//    NextPage page=new NextPage();
//    page.setVisible(true);
//    JLabel label = new JLabel("Welcome:"+value1);
//        page.getContentPane().add(label);
//  }
//    else{
//      System.out.println("enter the valid username and password");
//      JOptionPane.showMessageDialog(this,"Incorrect login or password",
//            "Error",JOptionPane.ERROR_MESSAGE);
//  }
//}