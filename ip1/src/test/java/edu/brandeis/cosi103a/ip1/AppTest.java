package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

/**
 * Unit tests for App helpers.
 */
public class AppTest {
    @Test
    public void rollDieAlwaysBetweenOneAndSix() {
        Random random = new Random(12345);
        for (int i = 0; i < 1000; i++) {
            int v = App.rollDie(random);
            assertTrue("roll out of range: " + v, v >= 1 && v <= 6);
        }
    }

    @Test
    public void addScoreAccumulatesCorrectly() {
        int score = 0;
        score = App.addScore(score, 4);
        assertEquals(4, score);
        score = App.addScore(score, 6);
        assertEquals(10, score);
    }
}
