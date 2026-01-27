package edu.brandeis.cosi103a.ip2;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

	@Test
	public void testSetupCreatesStarterDecks() {
		Game g = new Game();
		g.setup();
		Player p1 = g.getPlayer1();
		Player p2 = g.getPlayer2();

		assertEquals(10, p1.getAllCards().size());
		assertEquals(10, p2.getAllCards().size());

		// supply reduced by starter decks
		assertEquals(60 - 7 - 7, g.getSupplyCount("Bitcoin"));
		assertEquals(14 - 3 - 3, g.getSupplyCount("Method"));
	}

	@Test
	public void testRunEndsGameAndScores() {
		Game g = new Game();
		g.run();

		// Game should end when all Frameworks are purchased
		assertEquals(0, g.getSupplyCount("Framework"));

		int ap1 = g.getPlayer1().getAutomationPoints();
		int ap2 = g.getPlayer2().getAutomationPoints();
		assertTrue(ap1 >= 0);
		assertTrue(ap2 >= 0);
	}

	@Test
	public void testDeterministicRunWithSeedProducesSameOutcome() {
		Game g1 = new Game(12345L);
		g1.run();
		int ap1a = g1.getPlayer1().getAutomationPoints();
		int ap2a = g1.getPlayer2().getAutomationPoints();

		Game g2 = new Game(12345L);
		g2.run();
		int ap1b = g2.getPlayer1().getAutomationPoints();
		int ap2b = g2.getPlayer2().getAutomationPoints();

		assertEquals(ap1a, ap1b);
		assertEquals(ap2a, ap2b);
		assertEquals(0, g1.getSupplyCount("Framework"));
		assertEquals(0, g2.getSupplyCount("Framework"));
	}
}
