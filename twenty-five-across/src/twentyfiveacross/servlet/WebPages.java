package twentyfiveacross.servlet;

import java.io.PrintWriter;

public class WebPages {
    /** Top level frameset, divides the page in two rows: header and main. */
    public static void printFrameset(PrintWriter out) throws Exception {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>25 Across</title>");
        out.println("</head>");
        out.println("<frameset rows='25%,*'>");
        out.println("   <frame name='header' src='main?cmd=printHeader' marginwidth=0 scrolling=no>");
        out.println("   <frame name='body' src='main?cmd=printMain' marginwidth=0>");
        out.println("</frameset>");
        out.println("</html>");
    }

    /** Draw the header frame. */
    public static void printHeader(PrintWriter out) throws Exception {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>25 Across</title>");
//        out.println("<link rel='stylesheet' href='style.css' type='text/css'>");
        out.println("</head>");
        out.println("<body><br><h3>header</h3>");
//        out.println("   <table width='100%' height='100%' border=0 cellpadding=2 cellspacing=0>");
//        out.println("       <tr><td class='header' width=100% height=80% valign=bottom colspan=2>25 Across</td></tr>");
//        out.println("       <tr><td class='project' width=20% height=20% valign=middle></td>");
//        out.println("           <td class='cmdLinks' width=80% height=20% valign=middle>");
//
//        /* Print the right links depending on the role of the user that is logged in. */
//        out.println("       <a href='main?cmd=home' target='body'>Home</a>&nbsp;&nbsp;&nbsp;");
////        if (user.isAdmin)
//            out.println("       <a href='main?cmd=printUsrMgmt' target='body'>User Management</a>&nbsp;&nbsp;&nbsp;");
////        else
//            out.println("       <a href='main?cmd=printChngPwd' target='body'>Change Password</a>&nbsp;&nbsp;&nbsp;");
//        out.println("       <a href='main?cmd=logout' target='_top'>Log Out</a>&nbsp;&nbsp;&nbsp;");
//
//        out.println("   </td></tr>");
//        out.println("   </table>");
//        out.println("</body>");
        out.println("</head>");
        out.println("</html>");
    }

    /** Draw the header frame. */
    public static void printMain(PrintWriter out) throws Exception {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>25 Across</title>");
        out.println("</head>");
        out.println("<body><br><h3>main</h3>");
        out.println("<applet code='twentyfiveacross.applet.CrossLet' archive='CrossLet.jar' width=750 height=750 />");
        out.println("</head>");
        out.println("</html>");
    }

    /** Print all the style specifications. */
    public static void printStyles(PrintWriter out) {
        out.println("<style type='text/css'>");
        out.println("BODY {");
        out.println("   font-family:Arial, Helvetica, sans-serif;");
        out.println("   font-size:8pt;");
        out.println("   margin-top:2px;");
        out.println("   margin-left:3px;");
        out.println("}");
        out.println("TD {");
        out.println("   font-family: \"Comic Sans MS\", cursive;");
        out.println("   font-size:10pt;");
        out.println("   font-weight:bold;");
        out.println("   color:#0077ff;");
        out.println("}");
        out.println(".HEADER {");
        out.println("   font-family: \"Comic Sans MS\", cursive;");
        out.println("   font-size:14pt;");
        out.println("}");
        out.println("</style>");
    }


    /** Print the login page. */
    public static void printLogin(PrintWriter out,
                                  boolean failedBefore) throws Exception {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>25 Across Login</title>");
        out.println("<link rel='stylesheet' href='style.css' type='text/css'>");
        out.println("</head>");
        printStyles(out);
        out.println("<body class='body' onLoad='document.frm._U.focus();'>");
        out.println("   <form name='frm' action='main' method='post'>");
        out.println("       <input type='hidden' name='cmd' value='login'>");
//        out.println("   <br><br><br>");
        out.println("   <table cellspacing=0 cellpadding=0 align='center'>");
        out.println("       <tr style='height:60px;'><td></td></tr>");
        out.println("       <tr>");
        out.println("           <td><img src='images/noclue.jpg' border='0'></td>");
        out.println("           <td width='300px'><table align='center'>");
        out.println("               <tr><td colspan='2' class='header'>Welcome to 25 Across!</td></tr>");
        out.println("               <tr><td colspan='2' align='right'>Do <i>you</i> have a clue?<br><br></td></tr>");
        if (failedBefore) {
            out.println("               <tr>");
            out.println("                   <td colspan='2' align='center' class='textBig'>Unable to log-in.<br>");
            out.println("                   Please re-enter your information.</td>");
            out.println("               </tr>");
        }
        out.println("               <tr><td colspan='2' height='20'></td></tr>");
        out.println("               <tr>");
        out.println("                   <td align='left' class='text'>Username:</td>");
        out.println("                   <td><input type='text' name='_U' size='20'></td>");
        out.println("               </tr>");
        out.println("               <tr>");
        out.println("                   <td align='left' class='text'>Password:</td>");
        out.println("                   <td><input type='password' name='_P' size='20'></td>");
        out.println("               </tr>");
        out.println("               <tr><td colspan='2' height='20'></td></tr>");
        out.println("               <tr><td colspan='2' align='center'>");
        out.println("                   <input class='button' type='submit' value='Log In'></td>");
        out.println("               </tr>");
        out.println("               <tr><td colspan='2' height='20'></td></tr>");
        out.println("               <tr><td colspan='2'>Not registered yet?&nbsp;<a href='#'>Register</a></td></tr>");
        out.println("           </table></td>");
        out.println("       </tr>");
        out.println("   </table>");
        out.println("   </form>");
        out.println("</body>");
        out.println("</head>");
        out.println("</html>");
    }
}
