package twentyfiveacross.servlet;

import java.io.PrintWriter;
import java.util.Vector;

import twentyfiveacross.puzzle.Puzzle;
import twentyfiveacross.puzzle.Square;
import twentyfiveacross.puzzle.Word;


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
        //out.println("<applet code='twentyfiveacross.applet.CrossLet' archive='CrossLet.jar' width=750 height=750 />");
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
        out.println("       <input type='hidden' name='cmd' value='processLogin'>");
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

    /** Display the page for a given puzzle. */
    static void printGamePage(PrintWriter out, Puzzle puzzle) throws Exception {
        Vector<Word> acrossWords = puzzle.getAcrossWords();
        Vector<Word> downWords = puzzle.getDownWords();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>25 Across</title>");
        out.println("</head>");
        printStyles(out);
        out.println("<body><br><h3>Puzzle Page</h3>");
        out.println("   <table align='center'>");
        out.println("       <tr>");

        out.println("           <td style='vertical-align:top; padding: 0 30px 0 20px;width:420px;'>");
        printPuzzle(out,puzzle);
        printUserInputs(out,puzzle);
        printPuzzleButtons(out,puzzle);
        out.println("           </td>");

        out.println("           <td style='vertical-align:top;width:200px;'>");
        printClues(out,acrossWords,"Across");
        out.println("           </td>");

        out.println("           <td style='vertical-align:top;width:200px;'>");
        printClues(out,downWords,"Down");
        out.println("           </td>");

        out.println("       </tr>");
        out.println("   </table>");
        out.println("</head>");
        out.println("</html>");
    }

    static void printPuzzle(PrintWriter out, Puzzle puzzle) throws Exception {
        Square[][] squares = puzzle.getSquares();
        int size = puzzle.getSize();
        Square square;

        out.println("               <table cellspacing='0' cellpadding='0' class='puzzle'>");
        for (int i = 0; i < size; i++) {
            out.println("               <tr>");
            for (int j = 0; j < size; j++) {
                square = squares[i][j];
                printSquare(out,square);
            }
            out.println("               </tr>");
        }
        out.println("               </table>");
    }

    static void printSquare(PrintWriter out, Square square) throws Exception {
        String nr = square.getNumber() == 0 ? "&nbsp;" : String.valueOf(square.getNumber());

        if (true == square.isBlack())
            out.println("                 <td colspan='2' class='blacksquare'>");
        else {
            out.print("                 <td class='squareNr'>");
            out.print(nr);
            out.print("</td>");
            out.print("<td class='squareLetter'>");
            out.print(square.getLetter());
//            out.print("&nbsp;");
            out.println("</td>");
        }
    }

    static void printUserInputs(PrintWriter out, Puzzle puzzle) throws Exception {
        out.println("               <br><table>");
        out.println("               <form name='frm' action='main' method='post'>");
        out.println("                   <input type='hidden' name='cmd' value='enterWord'>");
        out.println("                   <tr>");
        out.println("                       <td>Word: <input name='word' type='text' size='" + puzzle.getSize() + "'></td>");
        out.println("                       <td style='padding-left:5px;'>Nr: <input name='nr' type'text' size='1' maxlength='2'></td>");
        out.println("                       <td style='padding-left:5px;' align='right'>");
        out.println("                           <input name='direction' type='radio' value='1'>Across</input>");
        out.println("                           <input name='direction' type='radio' value='2'>Down</input>");
        out.println("                       </td>");
        out.println("                       <td style='padding-left:5px;'align='right'><input type='submit' value='Submit'></td>");
        out.println("                   </tr>");
        out.println("                   </form>");
        out.println("               </table>");
    }

    static void printPuzzleButtons(PrintWriter out, Puzzle puzzle) throws Exception {
        out.println("               <br><br><table align='center'>");
        out.println("                   <tr>");
//        out.println("                       <td style='padding-left:5px;'align='right'><input type='button' value='Save'></td>");
        out.println("                       <td style='padding-left:5px;'align='right'><input type='button' value='Finished!'></td>");
        out.println("                       <td style='padding-left:5px;'align='right'><input type='button' value='Reset'></td>");
        out.println("                       <td style='padding-left:5px;'align='right'><input type='button' value='Quit'></td>");
        out.println("                   </tr>");
        out.println("               </table>");
    }
    static void printClues(PrintWriter out, Vector<Word> words, String direction)
            throws Exception {
        Word word;

        out.println("               <table>");
        out.println("               <tr><td colspan='2'><b>" + direction + ":</td></tr>");
        for (int i = 0; i < words.size(); i++) {
            word = words.get(i);
            out.print("               <tr><td>");
            out.print(word.getNumber());
            out.print(". ");
            out.print(word.getClue());
            out.println("</td></tr>");
        }
        out.println("               </table>");
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
        out.println(".PUZZLE {");
        out.println("   border: 1px solid black;");
        out.println("   cellpadding: 0px;");
        out.println("}");
        out.println(".SQUARENR {");
        out.println("   width: 10px;");
        out.println("   height: 32px;");
        out.println("   background-color: white;");
        out.println("   border-style: solid;");
        out.println("   border-color: black;");
        out.println("   border-width: 1px 0px 1px 1px;");
        out.println("   text-align: center;");
        out.println("   vertical-align: top;");
        out.println("   font-size: 8pt;");
        out.println("   font-weight: bold;");
        out.println("}");
        out.println(".SQUARELETTER {");
        out.println("   width: 20px;");
        out.println("   height: 32px;");
        out.println("   background-color: white;");
        out.println("   border-style: solid;");
        out.println("   border-color: black;");
        out.println("   border-width: 1px 1px 1px 0px;");
        out.println("   font-variant: small-caps;");
        out.println("   font-size: 11pt;");
        out.println("}");
        out.println(".BLACKSQUARE {");
        out.println("   background-color: black;");
        out.println("   border: 1px solid black;");
        out.println("}");
        out.println(".ERROR {");
        out.println("   color: red;");
        out.println("}");
        out.println("</style>");
    }
}
