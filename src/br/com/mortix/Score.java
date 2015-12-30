package br.com.mortix;

import br.com.mortix.ui.Console;
import br.com.mortix.ui.GUI;

/**
 * Classe responsável por exibir os resultados da partida.
 */
public class Score {

    private static Log log;
    private static Console console;
    private static GUI gui;

    public static void main(String[] args) {

        String filename = "";
        console = new Console();
        gui = new GUI();

        if (args.length > 0 && args[0] != null && !args[0].isEmpty()) {
            if (Log.logFileExists(args[0])) {
                setLog(args[0]);
                showResults();
            } else {
                console.write("The log file doesn't exist!");
            }
        } else {
            while (!Log.logFileExists(filename) && !filename.trim().equals("0")) {
                filename = gui.read("Type a valid log file name or type 0 to exit:");
            }
            if (!filename.trim().equals("0")) {
                setLog(filename);
                showResults();
            }
        }
        System.exit(0);
    }

    private static void setLog(String filename) {
        log = new Log(filename);
    }

    private static boolean showResults() {

        if (log == null) {
            return false;
        }

        String winner = "";
        int numberOfDeaths = 0;
        int score = 0;

        Results results = new Results(log);

        console.separator();
        console.write("LOG:");
        console.separator();
        console.newLine();

        for (String line : log.getAllLines()) {
            console.write(line);
        }

        console.newLine();
        console.separator();
        console.write("RESULTS:");

        for (String player : log.getPlayers()) {

            int k = 0; // Número de assassinatos do jogador
            int d = 0; // Número de mortes do jogador
            int points = 0;

            if (!player.equals(Players.WORLD)) {

                console.separator();
                console.newLine();

                points = results.pointsOfThePlayer(player);
                k = results.murdersOfThePlayer(player);
                d = results.deathsOfThePlayer(player);

                if (points > score || (points == score && d < numberOfDeaths)) {
                    score = points;
                    winner = player;
                    numberOfDeaths = d;
                }

                console.write(player + " killed " + k);
                console.write(player + " died " + d + "\n");

                if (results.getPlayerUsages(player).size() > 0) {
                    console.write(player + " has used: " + results.getPlayerUsages(player) + "\n");
                }
            }
        }

        console.separator();
        console.write("FINAL SCORE:");
        console.separator();
        console.newLine();

        console.write("The winner was " + winner + ".");
        console.newLine();

        console.write("The winner prefers to use " + results.preference(winner) + ".");
        console.newLine();

        console.write("The max sequence of murders with no death (streak) was " + results.streak().get("max") + " by " + results.streak().get("player") + ".");
        console.newLine();

        if (numberOfDeaths == 0) {
            console.write(winner + " has won without any death = 1 AWARD.");
            console.newLine();
        }

        for (String player : results.fiveMurdersPerMinute()) {
            console.write(player + " has killed 5x per minute = 1 AWARD.");
            console.newLine();
        }

        return true;
    }
}
