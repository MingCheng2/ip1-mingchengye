package edu.brandeis.cosi103a.ip2;

import java.util.Random;

public class Game {
	private final Supply supply;
	private final Player p1;
	private final Player p2;
	private final java.util.Random rnd;

	public Game() {
		this(new java.util.Random());
	}

	public Game(long seed) {
		this(new java.util.Random(seed));
	}

	private Game(java.util.Random rnd) {
		this.rnd = rnd;
		supply = new Supply();
		p1 = new Player("Player 1");
		p2 = new Player("Player 2");
	}

	public void setup() {
		p1.initializeStarterDeck(supply);
		p2.initializeStarterDeck(supply);
	}

	public void run() {
		setup();
		Player current = rnd.nextBoolean() ? p1 : p2;
		Player other = current == p1 ? p2 : p1;

		int turn = 0;
		int maxTurns = 10000;
		while (supply.getCount("Framework") > 0 && turn < maxTurns) {
			turn++;
			int coins = current.playAllCrypto();
			current.buyWithStrategy(supply, coins);
			current.cleanupAndDraw();

			// swap players
			Player tmp = current; current = other; other = tmp;
		}

		int ap1 = p1.getAutomationPoints();
		int ap2 = p2.getAutomationPoints();

		System.out.println("Game over after " + turn + " turns");
		System.out.println(p1.getName() + " AP = " + ap1);
		System.out.println(p2.getName() + " AP = " + ap2);
		if (ap1 > ap2) System.out.println(p1.getName() + " wins");
		else if (ap2 > ap1) System.out.println(p2.getName() + " wins");
		else System.out.println("Tie");
	}

	// Accessors for testing
	public int getSupplyCount(String name) {
		return supply.getCount(name);
	}

	public Player getPlayer1() { return p1; }
	public Player getPlayer2() { return p2; }

	public static void main(String[] args) {
		new Game().run();
	}
}


