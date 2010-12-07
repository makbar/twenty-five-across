package twentyfiveacross.ejbs;

import java.security.MessageDigest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SecurityTest extends TestCase {
	
	private UserManager user = null;

	public SecurityTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		user = new UserManager();
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		user = null;
	}

	public void testBruteForce() {
		try {
			// Attempting to detect password using bruteforce
			String testUsername = "testSecurityUser";
			MessageDigest digest = java.security.MessageDigest
			.getInstance("MD5");
			String generatedPassword;
			boolean broken = false;
			for (char i = 'a'; i <= 'z'; i++) {
				generatedPassword = Character.toString(i);
				digest.reset();
				digest.update(generatedPassword.getBytes());
				byte[] hash = digest.digest();
				StringBuffer pwhash = new StringBuffer();
				for (int j = 0; j < hash.length; j++) {
					pwhash.append(Integer.toHexString(0xFF & hash[j]));
				}
				broken = user.checkLogin(testUsername, pwhash.toString());
				if (broken) {
					System.out.println("Password found: " + generatedPassword);
					break;
				}
			}

			assertFalse(broken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testSQLInjection() {
		try {
				String testUsername = "testSecurityUser";
				String testPassword = "testSecurityPass";
				
				//trying SQL injection
				String[] sqlInjection = {"normaltext\';DROP TABLE userinfo; --",
						"normaltext\' OR 'x'='x;",
						"normaltext\';INSERT INTO userinfo SET username=\'abcd\', password=\'abcdefgh\', rating=\'abcd\', status=\'abcd\', displayname=\'abcd\' ; --",
						"normaltext\';UPDATE userinfo SET password=\'abcdefgh\' WHERE username=\'testSecurityUser\'; --",
						"normaltext\';DELETE FROM userinfo WHERE username=\'testSecurityUser\'; --"};
				for(int i=0;i<sqlInjection.length;i++)
				{
					user.createUser(sqlInjection[i], sqlInjection[i], sqlInjection[i]);
					user.banUser(sqlInjection[i], sqlInjection[i]);
					user.unBanUser(sqlInjection[i], sqlInjection[i]);
					user.checkBan(sqlInjection[i]);
					user.checkLogin(sqlInjection[i], sqlInjection[i]);
					user.deleteUser(sqlInjection[i], sqlInjection[i]);
					user.decUserRating(sqlInjection[i], sqlInjection[i]);
					user.incUserRating(sqlInjection[i], sqlInjection[i]);
					user.getRating(sqlInjection[i]);
				}

				//checking if (table dropped or user deleted)
				MessageDigest digest = java.security.MessageDigest
				.getInstance("MD5");
				StringBuffer pwhash = new StringBuffer();
				digest.reset();
				digest.update(testPassword.getBytes());
				byte[] hash = digest.digest();
				for (int i = 0; i < hash.length; i++) {
					pwhash.append(Integer.toHexString(0xFF & hash[i]));
				}
				assertTrue(user.checkLogin(testUsername, pwhash.toString()));

				//checking if (password changed)
				digest.reset();
				digest.update("abcdefgh".getBytes());
				pwhash.delete(0, pwhash.length());
				hash = digest.digest();
				for (int i = 0; i < hash.length; i++) {
					pwhash.append(Integer.toHexString(0xFF & hash[i]));
				}
				assertFalse(user.checkLogin(testUsername, pwhash.toString()));

				//checking if (new user created)
				digest.reset();
				digest.update("abcdefgh".getBytes());
				pwhash.delete(0, pwhash.length());
				hash = digest.digest();
				for (int i = 0; i < hash.length; i++) {
					pwhash.append(Integer.toHexString(0xFF & hash[i]));
				}
				assertFalse(user.checkLogin("abcd", pwhash.toString()));

		} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	public static Test suite(){
	      TestSuite suite = new TestSuite();
	      suite.addTest(new SecurityTest("testBruteForce"));
	      suite.addTest(new SecurityTest("testSQLInjection"));
		return suite;
	}

}
