package br.com.mortix;

import br.com.mortix.ui.Console;
import br.com.mortix.ui.GUI;

public class Score {

    private static Log log;
    private static Console console;
    private static GUI gui;

    public static void main(String[] args)
    {
        console = new Console();
        gui = new GUI();

        if (args.length > 0 && !args[0].isEmpty()) {
            setLog(args[0]);
        } else {
            String filename = "";
            while(!log.logFileExists(filename)) {
                filename = gui.read("Informe um número válido (nome) do arquivo de log:");
            }
            setLog(filename);
        }
        showResults();
        System.exit(0);
    }

    private static void setLog(String filename)
    {
        log = new Log(filename);
    }

    private static boolean showResults()
    {
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

        for (String line : log.getAllLines())
        {
            console.write(line);
        }

        console.newLine();
        console.separator();
        console.write("RESULTS:");

        for (String player : log.getPlayers())
        {
            int k = 0;      // Número de assassinatos do jogador
            int d = 0;      // Número de mortes do jogador
            int points = 0; // Considera quem matou mais subtraindo o número de mortes

            if (!player.equals(Players.WORLD))
            {
                console.separator();
                console.newLine();

                points = results.pointsOfThePlayer(player);
                k = results.killsOfThePlayer(player);
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

        console.write("The max sequence of kills (streak) was " + results.streak().get("max") + " by " + results.streak().get("player") + ".");
        console.newLine();

        if (numberOfDeaths == 0) {
            console.write(winner + " has won with no death = AWARD.");
            console.newLine();
        }

        for (String player : results.fiveKillsPerMinute()) {
            console.write(player + " has killed 5x/minute = AWARD.");
            console.newLine();
        }

        return true;
    }
}
