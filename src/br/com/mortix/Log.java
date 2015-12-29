package br.com.mortix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Log {

    private Scanner scanner;

    private ArrayList<String> log   = new ArrayList<String>();
    private ArrayList<String> dates = new ArrayList<String>();
    private ArrayList<String> hours = new ArrayList<String>();
    private ArrayList<String> killers = new ArrayList<String>();
    private ArrayList<String> victims = new ArrayList<String>();
    private ArrayList<String> usages  = new ArrayList<String>();

    Players players = new Players();
    Weapons weapons = new Weapons();

    public Log(String filename)
    {
        if (logFileExists(filename)) {
            openFile(filename);
            getLinesFromFile();
            getValuesPerColumnFromFile();
            setPlayersOfTheMatch();
            setWeaponsOfTheMatch();
        }
    }

    public static final boolean logFileExists(String filename)
    {
        return (new File("log/" + filename + ".txt")).exists();
    }

    private void openFile(String filename)
    {
        String file = "log/" + filename + ".txt";
        try {
            scanner = new Scanner(new FileReader(file))
                    .useDelimiter("\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getLinesFromFile()
    {
        while (scanner.hasNext()) {
            log.add(scanner.next().toUpperCase());
        }
    }

    private void getValuesPerColumnFromFile()
    {
        ArrayList<String> lines = getTheMoves();
        String[][] columns = new String[lines.size()][7];

        for (int i = 0; i < lines.size(); i++)
        {
            columns[i] = lines.get(i).split(" ");

            dates.add(columns[i][0]);
            hours.add(columns[i][1]);
            killers.add(columns[i][2]);
            victims.add(columns[i][4]);
            usages.add(columns[i][6]);
        }
    }

    private ArrayList<String> getTheMoves()
    {
        ArrayList<String> lines = new ArrayList<String>();
        lines.addAll(log);
        lines.remove(0);
        lines.remove(lines.size()-1);
        return lines;
    }

    private void setPlayersOfTheMatch()
    {
        for (String killer : getKillers()) {
            if (players.getAll().indexOf(killer) < 0)
            {
                players.add(killer);
            }
        }
        for (String victim : getVictims()) {
            if (players.getAll().indexOf(victim) < 0)
            {
                players.add(victim);
            }
        }
    }

    private void setWeaponsOfTheMatch()
    {
        for (String used : getUsages()) {
            if (weapons.getAll().indexOf(used) < 0) {
                weapons.add(used);
            }
        }
    }

    public final ArrayList<String> getAllLines()
    {
        return log;
    }

    public final ArrayList<String> getPlayers()
    {
        return players.getAll();
    }

    public final ArrayList<String> getWeapons()
    {
        return weapons.getAll();
    }

    public final ArrayList<String> getDates()
    {
        return dates;
    }

    public final ArrayList<String> getHours()
    {
        return hours;
    }

    public final ArrayList<String> getKillers()
    {
        return killers;
    }

    public final ArrayList<String> getVictims()
    {
        return victims;
    }

    public final ArrayList<String> getUsages()
    {
        return usages;
    }
}
