package edu.brandeis.cosi103a.ip2;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

	@Test
	public void testInitializeStarterDeckAndAPs() {
		Supply s = new Supply();
		Player p = new Player("Test");
		p.initializeStarterDeck(s);

		// starter deck is 10 cards total
		assertEquals(10, p.getAllCards().size());

		// starter APs: 3 Method cards worth 1 each
		assertEquals(3, p.getAutomationPoints());

		// supply counts reduced by 7 bitcoins and 3 methods
		assertEquals(60 - 7, s.getCount("Bitcoin"));
		assertEquals(14 - 3, s.getCount("Method"));
	}

	@Test
	public void testBuyWithStrategyBuysHighAPWhenAffordable() {
		Supply s = new Supply();
		Player p = new Player("Buyer");
		// afford framework
		boolean bought = p.buyWithStrategy(s, 8);
		assertTrue(bought);
		// check that player's cards include a Framework
		boolean hasFramework = p.getAllCards().stream().anyMatch(c -> "Framework".equals(c.getName()));
		assertTrue(hasFramework);
		assertEquals(7, s.getCount("Framework"));
	}

	@Test
	public void testPlayAllCryptoMovesCryptoFromHandAndReturnsCoins() throws Exception {
		Supply s = new Supply();
		Player p = new Player("CryptPlayer");
		p.initializeStarterDeck(s);

		// reflectively access private hand to compute expected coin total
		java.lang.reflect.Field handField = Player.class.getDeclaredField("hand");
		handField.setAccessible(true);
		@SuppressWarnings("unchecked")
		java.util.List<Card> hand = (java.util.List<Card>) handField.get(p);

		int expected = 0;
		int cryptoCount = 0;
		for (Card c : new java.util.ArrayList<>(hand)) {
			if (c.getType() == Card.Type.CRYPTO) {
				expected += c.getCoinValue();
				cryptoCount++;
			}
		}

		int coins = p.playAllCrypto();
		assertEquals(expected, coins);

		// hand size should have decreased by number of crypto cards played
		assertEquals(5 - cryptoCount, hand.size());

		// cleanup should discard played and restore hand to 5
		p.cleanupAndDraw();
		assertEquals(5, hand.size());
	}
}
