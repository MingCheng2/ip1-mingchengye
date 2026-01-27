package edu.brandeis.cosi103a.ip2;

public class Card {
	public enum Type { AUTOMATION, CRYPTO }

	private final String name;
	private final int cost;
	private final int apValue;      // automation points at game end
	private final int coinValue;    // coins when played
	private final Type type;

	private Card(String name, Type type, int cost, int apValue, int coinValue) {
		this.name = name;
		this.type = type;
		this.cost = cost;
		this.apValue = apValue;
		this.coinValue = coinValue;
	}

	public static Card automation(String name, int cost, int apValue) {
		return new Card(name, Type.AUTOMATION, cost, apValue, 0);
	}

	public static Card crypto(String name, int cost, int coinValue) {
		return new Card(name, Type.CRYPTO, cost, 0, coinValue);
	}

	public Card copy() {
		return new Card(name, type, cost, apValue, coinValue);
	}

	public String getName() { return name; }
	public int getCost() { return cost; }
	public int getApValue() { return apValue; }
	public int getCoinValue() { return coinValue; }
	public Type getType() { return type; }

	@Override
	public String toString() {
		return name + "(" + type + ", cost=" + cost + ")";
	}
}