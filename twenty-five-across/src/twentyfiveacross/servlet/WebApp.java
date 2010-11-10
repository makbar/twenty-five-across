package twentyfiveacross.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twentyfiveacross.ejbs.UserManagerRemote;
import twentyfiveacross.puzzle.Puzzle;
import twentyfiveacross.puzzle.Square;
import twentyfiveacross.puzzle.Word;

/**
 * Servlet implementation class WebApp
 */
@WebServlet(name = "WebApp", urlPatterns = { "/main" })
public class WebApp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
    UserManagerRemote userManager;

	Puzzle puzzle;

    public WebApp() {
        super();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param req servlet request
     * @param res servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest req,
            HttpServletResponse res) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html");

        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession();

        String cmd;

        try {
            cmd = req.getParameter("cmd");

            if (null == cmd)
                WebPages.printLogin(out,null);
            else if ("login".equals(cmd)) {
                /* Authenticate the user. */

//                WebPages.printLogin(out,userManager.sayHi("Team"));
                String puzzleFile =
                    "D:\\workspace\\twenty-five-across\\puzzles\\2.txt";
                puzzle = getPuzzle(puzzleFile);
                WebPages.printGamePage(out,puzzle);
            }
            else if ("register".equals(cmd)) {
                /* Create an account for the new user. */

            }

            if (null!=cmd) {
                if ("printHeader".equals(cmd)) {
                    WebPages.printHeader(out);
                }
                else if ("printMain".equals(cmd)) {
                    if(true/*user.loggedIn*/)
                    	WebPages.printMain(out);
                    else
                    	WebPages.printLogin(out, null);
                }
                else if ("printRegister".equals(cmd)) {
                    WebPages.printRegister(out,null);
                }
                else if ("processRegister".equals(cmd)) {
                    if(userManager.createUser(req.getParameter("_U"),req.getParameter("name"),req.getParameter("_P")))
                        WebPages.printLogin(out,"Registration Succeeded! Please Log in.");
                    else
                        WebPages.printRegister(out,"Registration Failed! Please try again.");
                }
                else if ("processLogin".equals(cmd)) {
                    sendMessage();
//                    if (userManager.checkLogin(req.getParameter("_U"),req.getParameter("_P")))
//                        WebPages.printMain(out);
//                    else
//                        WebPages.printLogin(out,"Login Failed! Please try again.");
                }
                else if ("enterWord".equals(cmd)) {
                    // TODO:
                    // get puzzle object for this user (from user session?)
                    // fill in the appropriate squares
                    // serialize and store in the db
                    // notify viewer clients of the update?
                    // redraw puzzle page with filled-in word
                    String word = req.getParameter("word");
                    int nr = Integer.parseInt(req.getParameter("nr"));
                    int direction = Integer.parseInt(req.getParameter("direction"));

                    updatePuzzle(puzzle, word, nr, direction);
                    WebPages.printGamePage(out,puzzle);
                }
                else if ("finish".equals(cmd)) {
                    // TODO:
                    // notify viewer clients the player has finished?
                    // later: store stats about the play
                    // redirect to home page / list of puzzles
                }
                else if ("quit".equals(cmd)) {
                    // TODO:
                    // notify viewer clients the player has finished?
                    // redirect to home page / list of puzzles
                }
                else
					WebPages.printFrameset(out);
            }
            else
            {
                WebPages.printLogin(out,null);
            	return;
            }

        }
        catch(Exception e) {
            System.out.println("Error(doPost): " + e);
            e.printStackTrace();
        }
        finally {
            out.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public Puzzle getPuzzle(String fileName) {
        String line;
        File puzzleFile;
        BufferedReader in;

        String[] tokens1, tokens2;

        Puzzle p = null;
        Vector<Word> words;
        Square[][] squares;

        int size, col, row = 0;

        String letter;
        int nr;
        String word, clue;

        try {
            puzzleFile = new File(fileName);
            in = new BufferedReader(new FileReader(puzzleFile));

            /* Read in the size of the puzzle. */
            if (null != (line = in.readLine()))
                size = Integer.parseInt(line);
            else
                throw new Exception("File at " + puzzleFile.getAbsolutePath() +
                                    " is empty.");

            p = new Puzzle();
            words = new Vector<Word>();
            squares = new Square[size][size];

            /* Populate the size of the puzzle. */
            p.setSize(size);

            /* Read and populate all the squares of the puzzle. */
            System.out.println("Squares");//ek
            while (null != (line = in.readLine()) && row < size) {
                if ("".equals(line))
                    continue;

                tokens1 = line.split("\\|",size);

                if (tokens1.length != size)
                    throw new Exception("Number of columns (" + tokens1.length +
                                        ") does not match puzzle size (" +
                                        size + ")");

                for (col = 0; col < size; col++) {
                    tokens2 = tokens1[col].split(":");

                    // TODO: fix
                    letter = tokens2[0];
                    if (!"".equals(letter))
                        letter = "&nbsp;";

                    if (tokens2.length > 1) {
                        nr = Integer.parseInt(tokens2[1]);
                        squares[row][col] = new Square(letter,nr,"".equals(letter));
                    }
                    else
                        squares[row][col] = new Square(letter,"".equals(letter));
                }
                row++;
            }
            p.setSquares(squares);

            /* Read and populate the across words. */
            while (null != (line = in.readLine()) && !"====".equals(line)) {
                if ("".equals(line))
                    continue;

                tokens1 = line.split("\\|",3);

                nr = Integer.parseInt(tokens1[0]);
                word = tokens1[1];
                clue = tokens1[2];

                words.add(new Word(word, clue, nr));
            }
            p.setAcrossWords(words);

            /* Read and populate the down words. */
            words = new Vector<Word>();
            while (null != (line = in.readLine())) {
                if ("".equals(line))
                    continue;

                tokens1 = line.split("\\|",3);

                nr = Integer.parseInt(tokens1[0]);
                word = tokens1[1];
                clue = tokens1[2];

                words.add(new Word(word, clue, nr));
            }
            p.setDownWords(words);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }

    static final int DIR_ACROSS = 1;
    static final int DIR_DOWN = 2;

    // TODO: move to the appropriate session bean
    static void updatePuzzle(Puzzle puzzle, String word, int nr, int direction) throws Exception {
        int i, j, letterIndx;
        Square square;
        Square[][] squares = puzzle.getSquares();
        char[] letters = word.toCharArray();
        boolean writing = false;

        for (i = 0; i < puzzle.getSize(); i++) {
            letterIndx = 0;
            for (j = 0; j < puzzle.getSize(); j++) {
                if (DIR_ACROSS == direction)
                    square = squares[i][j];
                else if (DIR_DOWN == direction)
                    square = squares[j][i];
                else
                    throw new Exception("Unknown direction: " + direction);

                /* Skip the squares that don't have a matching number if not writing yet. */
                if (nr != square.getNumber() && !writing)
                    continue;

                /* Break out if writing word and reached a black square. */
                if (writing && square.isBlack())
                    break;

                /* At this point, square with number was found, so start writing. */
                if (!writing)
                    writing = true;

                square.setLetter(String.valueOf(letters[letterIndx]));
                letterIndx++;
            }
            /* Break out if writing word and reached the end of a row. */
            if (writing)
                break;
        }
    }

    @Resource(mappedName = "testQueue2")
    private Queue queue;

    @Resource(mappedName = "jms/QueueConnectionFactory")
    private QueueConnectionFactory queueConnectionFactory;

    public void sendMessage() {
        Connection connection = null;
        Session session = null;
        MessageProducer messageProducer = null;
        TextMessage message = null;
        final int NUM_MSGS = 3;

        try {
            connection = queueConnectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            messageProducer = session.createProducer(queue);
            message = session.createTextMessage();

            for (int i = 0; i < NUM_MSGS; i++) {
                message.setText("This is message " + (i + 1));
                System.out.println("Sending message: " + message.getText());
                messageProducer.send(message);
            }

        } catch (JMSException e) {
            System.out.println("Exception occurred: " + e.toString());
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (JMSException e) {
//                }
//            }

            System.exit(0);
        }
    }
}
