package br.com.mortix.tests;

import br.com.mortix.Log;
import br.com.mortix.Players;
import br.com.mortix.Results;
import br.com.mortix.ui.Console;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResultsTest {

    private Log log;
    private Results results;
    private Console console;

    // Informe o nome do arquivo (= número da partida) que deseja testar.
    private String filename = "11348965";
    // Informe um nome válido de jogador para testar (não é "case sensitive").
    private String player = "thomas";

    public ResultsTest() {

        if (Log.logFileExists(filename)) {
            log = new Log(filename);
            results = new Results(log);
            console = new Console();
        }
    }

    @Test
    public void testStreak() {

        assertNotNull("The log file doesn't exist!", log);

        int max = Integer.valueOf(results.streak().get("max"));

        assertFalse(max < 0);

        console.write("The max sequence of murders with no death (streak) was " + max + " by " + results.streak().get("player") + ".");
    }

    @Test
    public void testGetPlayerUsages() {

        assertNotNull("The log file doesn't exist!", log);

        assertTrue("The player " + player.toUpperCase() + " doesn't exist!", Players.playerExists(player));

        assertNotNull(results.getPlayerUsages(player));

        console.write(player.toUpperCase() + " has used: " + results.getPlayerUsages(player));
    }

    @Test
    public void testPreference() {

        assertNotNull("The log file doesn't exist!", log);

        assertTrue("The player " + player.toUpperCase() + " doesn't exist!", Players.playerExists(player));

        String preference = results.preference(player);

        assertNotNull(preference);

        if (preference.isEmpty()) {
            console.write(player.toUpperCase() + " hasn't any preference!");
        } else {
            console.write(player.toUpperCase() + " prefers to use " + preference + ".");
        }
    }

    @Test
    public void testFiveMurdersPerMinute() {

        assertNotNull("The log file doesn't exist!", log);

        assertNotNull(results.fiveMurdersPerMinute());

        if (results.fiveMurdersPerMinute().size() > 0) {
            for (String player : results.fiveMurdersPerMinute()) {
                console.write(player.toUpperCase() + " has killed 5x per minute in this match = 1 AWARD.");
            }
        } else {
            console.write("Anybody has killed 5x per minute in this match!");
        }
    }

    @Test
    public void testDeathsOfThePlayer() {

        assertNotNull("The log file doesn't exist!", log);

        assertTrue("The player " + player.toUpperCase() + " doesn't exist!", Players.playerExists(player));

        int d = results.deathsOfThePlayer(player);

        assertTrue(d >= 0);

        console.write(player.toUpperCase() + " has died " + d + (d > 0 ? "x." : "."));
    }

    @Test
    public void testMurdersOfThePlayer() {

        assertNotNull("The log file doesn't exist!", log);

        assertTrue("The player " + player.toUpperCase() + " doesn't exist!", Players.playerExists(player));

        int k = results.murdersOfThePlayer(player);

        assertTrue(k >= 0);

        console.write(player.toUpperCase() + " has killed " + k + (k > 0 ? "x." : "."));
    }

    @Test
    public void testPointsOfThePlayer() {

        assertNotNull("The log file doesn't exist!", log);

        assertTrue("The player " + player.toUpperCase() + " doesn't exist!", Players.playerExists(player));

        int p = results.pointsOfThePlayer(player);

        assertTrue(p >= 0);

        console.write(player.toUpperCase() + " has gotten " + p + (p > 1 ? " points." : " point."));
    }
}