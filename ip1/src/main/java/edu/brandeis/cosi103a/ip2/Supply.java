package edu.brandeis.cosi103a.ip2;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Collections;
import java.util.HashSet;

public class Supply {

    private Map<String, Integer> cards;
    private Map<String, Card> prototypes;

    public Supply() {
        cards = new HashMap<>();
        prototypes = new HashMap<>();
        initDefaults();
    }

    private void initDefaults() {
        // Automation cards
        prototypes.put("Method", Card.automation("Method", 2, 1));
        cards.put("Method", 14);

        prototypes.put("Module", Card.automation("Module", 5, 3));
        cards.put("Module", 8);

        prototypes.put("Framework", Card.automation("Framework", 8, 6));
        cards.put("Framework", 8);

        // Cryptocurrency cards
        prototypes.put("Bitcoin", Card.crypto("Bitcoin", 0, 1));
        cards.put("Bitcoin", 60);

        prototypes.put("Ethereum", Card.crypto("Ethereum", 3, 2));
        cards.put("Ethereum", 40);

        prototypes.put("Dogecoin", Card.crypto("Dogecoin", 6, 3));
        cards.put("Dogecoin", 30);
    }

    public Set<String> availableCardNames() {
        return Collections.unmodifiableSet(new HashSet<>(cards.keySet()));
    }

    public int getCount(String name) {
        return cards.getOrDefault(name, 0);
    }

    public Card peek(String name) {
        return prototypes.get(name);
    }

    public Card take(String name) {
        int cnt = cards.getOrDefault(name, 0);
        if (cnt <= 0) return null;
        cards.put(name, cnt - 1);
        Card proto = prototypes.get(name);
        if (proto == null) return null;
        return proto.copy();
    }
}