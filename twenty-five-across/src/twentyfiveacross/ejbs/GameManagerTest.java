package twentyfiveacross.ejbs;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class GameManagerTest extends TestCase {
	
	private GameManager gameMgr1;

	public GameManagerTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		gameMgr1 = new GameManager();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
		gameMgr1 = null;
	}
	
	public void testGetCrossword() {
		try {
			assertTrue(gameMgr1.getCrossword(-2345) == null);//Negative input			
			assertTrue(gameMgr1.getCrossword(2345) == null);//Game that is not in the system
			assertTrue(gameMgr1.getCrossword(813801187) == null);//In the system, but not an active game
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void testGetCw() {
		try {
			assertTrue(gameMgr1.getCw(null) == null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testGetSolveState() {
		try {
			assertTrue(gameMgr1.getSolveState(-2345) == null);//Negative input			
			assertTrue(gameMgr1.getSolveState(2345) == null);//Game that is not in the system
			assertTrue(gameMgr1.getSolveState(813801187) == null);//In the system, but not an active game
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testSetLetter() {
		try {
			assertTrue(gameMgr1.setLetter(-2345, 1, 2, "A") == null);//Negative input			
			assertTrue(gameMgr1.setLetter(2345, 1, 2, "A") == null);//Game that is not in the system
			assertTrue(gameMgr1.setLetter(813801187, 1, 2, "A") == null);//In the system, but not an active game
			assertTrue(gameMgr1.setLetter(1439197775, 1, 2, "3") == null);//Letter field must be alphabetic
			assertTrue(gameMgr1.setLetter(1439197775, -1, 0, "A") == null);//Only positive entries for x and y
			assertTrue(gameMgr1.setLetter(1439197775, 123938, 222222344, "3") == null);//Cannot go beyond range of x and y
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Test suite(){
	      TestSuite suite = new TestSuite();
	      suite.addTest(new GameManagerTest("testGetCrossword"));
	      suite.addTest(new GameManagerTest("testGetCw"));
	      suite.addTest(new GameManagerTest("testGetSolveState"));
	      suite.addTest(new GameManagerTest("testSetLetter"));
		return suite;
	}

}
