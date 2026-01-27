package edu.brandeis.cosi103a.ip2;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Set;

public class SupplyTest {

	@Test
	public void testInitialCountsAndNames() {
		Supply s = new Supply();
		assertEquals(60, s.getCount("Bitcoin"));
		assertEquals(14, s.getCount("Method"));
		assertEquals(8, s.getCount("Framework"));

		Set<String> names = s.availableCardNames();
		assertTrue(names.contains("Bitcoin"));
		assertTrue(names.contains("Method"));
		assertTrue(names.contains("Framework"));
	}

	@Test
	public void testTakeReducesCountAndEventuallyNull() {
		Supply s = new Supply();
		int initial = s.getCount("Framework");
		for (int i = 0; i < initial; i++) {
			Card c = s.take("Framework");
			assertNotNull(c);
			assertEquals("Framework", c.getName());
		}
		assertEquals(0, s.getCount("Framework"));
		Card none = s.take("Framework");
		assertNull(none);
	}

	@Test
	public void testPeekDoesNotReduceCountAndUnknowns() {
		Supply s = new Supply();
		int before = s.getCount("Module");
		Card p = s.peek("Module");
		assertNotNull(p);
		assertEquals("Module", p.getName());
		// peek should not reduce count
		assertEquals(before, s.getCount("Module"));

		// unknown card
		assertEquals(0, s.getCount("Nonexistent"));
		assertNull(s.peek("Nonexistent"));
	}
}
