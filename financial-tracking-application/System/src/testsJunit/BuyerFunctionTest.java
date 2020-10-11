package testsJunit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Objects.*;
class BuyerFunctionTest {
	private Buyer luke;
	private Transaction gamble;

	@BeforeEach
	void setUp() throws Exception {
		luke = new Buyer("Luke");
		gamble = new Transaction("02/02/2020", "BETWAY", "Luke", 25.0);
		luke.addTrans();
		luke.addIdentifier("BETWAY");
		luke.updatePoints(10.0);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testBuyerTransCounter() {
		assertEquals(1, luke.getTrans());
	}
	
	@Test
	void testBuyerPoints() {
		luke.updatePoints(1 * gamble.getValue());
		assertEquals(35.0, luke.getPoints());
	}
	
	@Test
	void testTransBuyer() {
		assertEquals(gamble.getBuyer(), luke.getName());
	}

}
