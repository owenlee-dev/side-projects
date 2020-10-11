/**
 * 
 */
package testsJunit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Objects.*;

/**
 * @author luke3
 *
 */
public class BuyerTest {

	/**
	 * @throws java.lang.Exception
	 */
	
	private Buyer luke;
	private Transaction gamble;
	@Before
	public void setUp() throws Exception {
		luke = new Buyer("Luke");
		gamble = new Transaction("01/02/2020", "BETWAY", "Luke", 20.00);
		luke.addTrans();
		luke.addIdentifier("BETWAY");
		luke.updatePoints(10.0);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBuyerTransCounter() {
		assertEquals(1, luke.getTrans());
	}
	/**
	public void testBuyerIdentifiers() {
		assertEquals("BETWAY", luke.getIdentifiers());
	}
	
	public void getBuyerTest() {
		assertEquals(luke.getName(), gamble.getBuyer());
	}
	*/
	

}
