package twentyfiveacross.servlet;

import java.io.PrintWriter;

public class WebPages {
    /** Top level frameset, divides the page in two rows: header and main. */
    static void printFrameset(PrintWriter out) throws Exception {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>25 Across</title>");
        out.println("</head>");
        out.println("<frameset border='0' rows='10%,*'>");
        out.println("   <frame name='header' src='main?cmd=printHeader' marginwidth=0 scrolling=no>");
        out.println("   <frame name='body' src='main?cmd=printMain' marginwidth=0>");
        out.println("</frameset>");
        out.println("</html>");
    }

    /** Draw the header frame. */
    static void printHeader(PrintWriter out) throws Exception {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>25 Across</title>");
        out.println("</head>");
        printStyles(out);
        out.println("<body class='headerframe'>");
        out.println("   <table width='100%' height='100%' border=0 cellpadding=2 cellspacing=0>");
        out.println("       <tr>");
        out.println("           <td class='logo' width=60% height=80% valign=bottom colspan=2>25 Across</td>");
        out.println("           <td class='cmdLinks' width=40% height=20% valign=middle align=right>");

        //      /* Print the right links depending on the role of the user that is logged in. */
        out.println("       <a href='main?cmd=home' target='main' class='headerlink'>Home</a>&nbsp;&nbsp;&nbsp;&nbsp;");
        //      if (user.isAdmin)
        out.println("       <a href='main?cmd=printMgmt' target='main' class='headerlink'>Management</a>&nbsp;&nbsp;&nbsp;&nbsp;");
        //      else
        out.println("       <a href='main?cmd=printChngPwd' target='main' class='headerlink'>Change Password</a>&nbsp;&nbsp;&nbsp;&nbsp;");
        out.println("       <a href='main?cmd=logout' target='_top' class='headerlink'>Log Out</a>&nbsp;&nbsp;&nbsp;&nbsp;");
//
      out.println("   </td>");
      out.println("       </tr>");
      out.println("   </table>");
      out.println("</body>");
      out.println("</head>");
      out.println("</html>");
    }

    /** Draw the main frame. */
    static void printMain(PrintWriter out) throws Exception {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>25 Across</title>");
        out.println("</head>");
        out.println("<body><br><h3>main</h3>");
        out.println("<applet code='twentyfiveacross.applet.CrossLet' archive='CrossLet.jar' width=750 height=750 />");
        out.println("</head>");
        out.println("</html>");
    }

    /** Print the login page. */
    static void printLogin(PrintWriter out, String msg) throws Exception {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>25 Across Login</title>");
        out.println("<link rel='stylesheet' href='style.css' type='text/css'>");
        out.println("</head>");
        printStyles(out);
        out.println("<body class='body' onLoad='document.frm._U.focus();'>");
        out.println("   <form name='frm' action='main' method='post'>");
        out.println("       <input type='hidden' name='cmd' value='login'>");
        out.println("   <table cellspacing=0 cellpadding=0 align='center'>");
        out.println("       <tr style='height:60px;'><td></td></tr>");
        out.println("       <tr>");
        out.println("           <td><img src='images/noclue.jpg' border='0'></td>");
        out.println("           <td width='300px'><table align='center'>");
        out.println("               <tr><td colspan='2' class='header'>Welcome to 25 Across!</td></tr>");
        out.println("               <tr><td colspan='2' class='label' align='right'>Do <i>you</i> have a clue?</td></tr>");
        out.println("               <tr><td colspan='2' align='center' class='label error'>");
        if (null != msg)
            out.println(msg);
        else
            out.println("<br>");
        out.println("                   </td>");
        out.println("               </tr>");
        out.println("               <tr><td colspan='2' height='20px'></td></tr>");
        out.println("               <tr>");
        out.println("                   <td align='left' class='label'>Username:</td>");
        out.println("                   <td><input type='text' name='_U' size='20'></td>");
        out.println("               </tr>");
        out.println("               <tr>");
        out.println("                   <td align='left' class='label'>Password:</td>");
        out.println("                   <td><input type='password' name='_P' size='20'></td>");
        out.println("               </tr>");
        out.println("               <tr><td colspan='2' height='20px'></td></tr>");
        out.println("               <tr><td colspan='2' align='center'>");
        out.println("                   <input class='button' type='submit' value='Log In'></td>");
        out.println("               </tr>");
        out.println("               <tr><td colspan='2' height='20px'></td></tr>");
        out.println("               <tr><td colspan='2'>Not registered yet?&nbsp;<a href='main?cmd=printRegister'>Register</a></td></tr>");
        out.println("           </table></td>");
        out.println("       </tr>");
        out.println("   </table>");
        out.println("   </form>");
        out.println("</body>");
        out.println("</head>");
        out.println("</html>");
    }

    /** Print the user registration page. */
    static void printRegister(PrintWriter out, String msg) throws Exception {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>25 Across Register</title>");
        out.println("<link rel='stylesheet' href='style.css' type='text/css'>");
        out.println("</head>");
        printStyles(out);
        out.println("<body class='body' onLoad='document.frm._U.focus();'>");
        out.println("   <form name='frm' action='main' method='post'>");
        out.println("       <input type='hidden' name='cmd' value='processRegister'>");
        out.println("   <table cellspacing=0 cellpadding=0 align='center'>");
        out.println("       <tr style='height:60px;'><td></td></tr>");
        out.println("       <tr>");
        out.println("           <td valign='top'><img src='images/noclue.jpg'></td>");
        out.println("           <td width='300px'><table align='center'>");
        out.println("               <tr><td colspan='2' class='header'>Welcome to 25 Across!</td></tr>");
        out.println("               <tr><td colspan='2' align='center' class='label error'>");
        if (null != msg)
            out.println(msg);
        else
            out.println("<br>");
        out.println("                   </td>");
        out.println("               </tr>");
        out.println("               <tr><td colspan='2' height='20'></td></tr>");
        out.println("               <tr><td colspan='2'>Please fill out the form to register:</td></tr>");
        out.println("               <tr>");
        out.println("                   <td align='left' class='label'>Name:</td>");
        out.println("                   <td><input type='text' name='name' size='20'></td>");
        out.println("               </tr>");
        out.println("               <tr>");
        out.println("                   <td align='left' class='label'>Username:</td>");
        out.println("                   <td><input type='text' name='_U' size='20'></td>");
        out.println("               </tr>");
        out.println("               <tr><td colspan='2' height='20'></td></tr>");
        out.println("               <tr>");
        out.println("                   <td align='left' class='label'>Password:</td>");
        out.println("                   <td><input type='password' name='_P' size='20'></td>");
        out.println("               </tr>");
        out.println("               <tr>");
        out.println("                   <td align='left' class='label'>Confirm password:</td>");
        out.println("                   <td><input type='password' name='_P2' size='20'></td>");
        out.println("               </tr>");
        out.println("               <tr><td colspan='2' height='20'></td></tr>");
        out.println("               <tr><td colspan='2' align='center'>");
        out.println("                   <input class='button' type='submit' value='Register'>");
        out.println("                   <input class='button' type='button' value='Cancel' onclick='document.location=\"main\"'>");
        out.println("                   </td>");
        out.println("               </tr>");
        out.println("               <tr><td colspan='2' height='20'></td></tr>");
        out.println("           </table></td>");
        out.println("       </tr>");
        out.println("   </table>");
        out.println("   </form>");
        out.println("</body>");
        out.println("</head>");
        out.println("</html>");
    }    
    
    /** Print all the style specifications. */
    static void printStyles(PrintWriter out) {
        out.println("<style type='text/css'>");
        out.println("TD {");
        out.println("   font-family: Arial, Helvetica, sans-serif;");
        out.println("   font-size: 9pt;");
        out.println("   margin-top: 2px;");
        out.println("   margin-left: 3px;");
        out.println("}");
        out.println("IMG {");
        out.println("   vertical-align: top;");
        out.println("}");
        out.println(".LABEL {");
        out.println("   font-family: \"Comic Sans MS\", cursive;");
        out.println("   font-size: 10pt;");
        out.println("   font-weight: bold;");
        out.println("   color: #0077ff;");
        out.println("}");
        out.println(".HEADER {");
        out.println("   font-family: \"Comic Sans MS\", cursive;");
        out.println("   font-size: 14pt;");
        out.println("   color: #0077ff;");
        out.println("}");
        out.println(".LOGO {");
        out.println("   font-family: \"Comic Sans MS\", cursive;");
        out.println("   font-size: 22pt;");
        out.println("   font-weight: bold;");
        out.println("   color: white;");
        out.println("   padding: 0px 0px 10px 15px;");
        out.println("}");
        out.println(".HEADERFRAME {");
        out.println("   font-size: 10pt;");
        out.println("   background-color: #0077ff;");
        out.println("}");
        out.println(".HEADERLINK {");
        out.println("   font-family: \"Comic Sans MS\", cursive;");
        out.println("   font-size: 10pt;");
        out.println("   font-weight: bold;");
        out.println("   text-decoration:none;");
        out.println("   color: white;");
        out.println("}");
        out.println(".ERROR {");
        out.println("   color: red;");
        out.println("}");
        out.println("</style>");
    }
}
