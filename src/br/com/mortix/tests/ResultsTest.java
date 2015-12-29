package br.com.mortix.tests;

import br.com.mortix.Log;
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
    // Informe o nome de um jogador para testar.
    private String player = "thomas";

    public ResultsTest()
    {
        if (log.logFileExists(filename)) {
            log = new Log(filename);
            results = new Results(log);
            console = new Console();
        }
    }

    @Test
    public void testStreak()
    {
        assertNotNull("It wasn't possible to create a object of Results' class!", results);

        if (results != null) {

            int max = Integer.valueOf(results.streak().get("max"));

            assertFalse("There isn't any \"streak\" in this match!", max == 0);

            console.write("The max sequence of kills was " + max + " by " + results.streak().get("player") + ".");
        }
    }

    @Test
    public void testGetPlayerUsages() throws Exception
    {
        assertNotNull("It wasn't possible to create a object of Results' class!", results);

        if (results != null) {

            assertTrue(player.toUpperCase() + " hasn't killed in this match!", results.getPlayerUsages(player).size() > 0);

            console.write(player.toUpperCase() + " has used: " + results.getPlayerUsages(player));
        }
    }

    @Test
    public void testPreference() throws Exception
    {
        assertNotNull("It wasn't possible to create a object of Results' class!", results);

        if (results != null) {

            String preference = results.preference(player);

            assertFalse("There is no preference by " + player.toUpperCase() + "!", preference.isEmpty());

            console.write(player.toUpperCase() + " prefers to use " + preference + ".");
        }
    }

    @Test
    public void testFiveKillsPerMinute() throws Exception
    {
        assertNotNull("It wasn't possible to create a object of Results' class!", results);

        if (results != null) {

            assertTrue("There isn't anyone who killed 5x/minute!", results.fiveKillsPerMinute().size() > 0);

            for (String player : results.fiveKillsPerMinute()) {
                console.write(player.toUpperCase() + " has killed 5x/minute.");
            }
        }
    }

    @Test
    public void testDeathsOfThePlayer() throws Exception
    {
        assertNotNull("It wasn't possible to create a object of Results' class!", results);

        if (results != null) {

            int d = results.deathsOfThePlayer(player);

            assertTrue(d >= 0);

            console.write(player.toUpperCase() + " has died " + d + (d > 1 ? " times." : " time."));
        }
    }

    @Test
    public void testKillsOfThePlayer() throws Exception
    {
        assertNotNull("It wasn't possible to create a object of Results' class!", results);

        if (results != null) {

            int k = results.killsOfThePlayer(player);

            assertTrue(k >= 0);

            console.write(player.toUpperCase() + " has killed " + k + (k > 1 ? " times." : " time."));
        }
    }

    @Test
    public void testPointsOfThePlayer() throws Exception
    {
        assertNotNull("It wasn't possible to create a object of Results' class!", results);

        if (results != null) {

            int points = results.pointsOfThePlayer(player);

            assertNotNull(points);

            console.write(player.toUpperCase() + " has gotten " + points + (points > 1 ? " points." : " point."));
        }
    }
}