package edu.brandeis.cosi103a.ip2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;

public class Player {
	private final String name;
	private final List<Card> drawPile = new ArrayList<>();
	private final List<Card> discardPile = new ArrayList<>();
	private final List<Card> hand = new ArrayList<>();
	private final List<Card> played = new ArrayList<>();

	public Player(String name) {
		this.name = name;
	}

	public String getName() { return name; }

	public void initializeStarterDeck(Supply supply) {
		// take 7 Bitcoins and 3 Methods from the supply
		for (int i = 0; i < 7; i++) {
			Card c = supply.take("Bitcoin");
			if (c != null) drawPile.add(c);
		}
		for (int i = 0; i < 3; i++) {
			Card c = supply.take("Method");
			if (c != null) drawPile.add(c);
		}
		Collections.shuffle(drawPile);
		drawToHand(5);
	}

	private void drawToHand(int n) {
		while (hand.size() < n) {
			if (drawPile.isEmpty()) {
				if (discardPile.isEmpty()) break;
				// shuffle discard into draw
				drawPile.addAll(discardPile);
				discardPile.clear();
				Collections.shuffle(drawPile);
			}
			if (!drawPile.isEmpty()) {
				hand.add(drawPile.remove(drawPile.size() - 1));
			}
		}
	}

	public int playAllCrypto() {
		int coins = 0;
		Iterator<Card> it = hand.iterator();
		List<Card> toPlay = new ArrayList<>();
		while (it.hasNext()) {
			Card c = it.next();
			if (c.getType() == Card.Type.CRYPTO) {
				coins += c.getCoinValue();
				toPlay.add(c);
				it.remove();
			}
		}
		played.addAll(toPlay);
		return coins;
	}

	public boolean buyWithStrategy(Supply supply, int coins) {
		// prefer automation cards by AP value
		String[] apPriority = {"Framework", "Module", "Method"};
		for (String name : apPriority) {
			Card proto = supply.peek(name);
			if (proto != null && proto.getCost() <= coins && supply.getCount(name) > 0) {
				Card bought = supply.take(name);
				if (bought != null) {
					discardPile.add(bought);
					return true;
				}
			}
		}

		// else buy best crypto affordable (Dogecoin > Ethereum). Skip Bitcoin purchases by AI.
		String[] cryptoPriority = {"Dogecoin", "Ethereum"};
		for (String name : cryptoPriority) {
			Card proto = supply.peek(name);
			if (proto != null && proto.getCost() <= coins && supply.getCount(name) > 0) {
				Card bought = supply.take(name);
				if (bought != null) {
					discardPile.add(bought);
					return true;
				}
			}
		}

		return false;
	}

	public void cleanupAndDraw() {
		// discard hand and played
		discardPile.addAll(hand);
		hand.clear();
		discardPile.addAll(played);
		played.clear();
		// draw new hand
		drawToHand(5);
	}

	public List<Card> getAllCards() {
		List<Card> all = new ArrayList<>();
		all.addAll(drawPile);
		all.addAll(discardPile);
		all.addAll(hand);
		all.addAll(played);
		return all;
	}

	public int getAutomationPoints() {
		int sum = 0;
		for (Card c : getAllCards()) {
			sum += c.getApValue();
		}
		return sum;
	}
}
