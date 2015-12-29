package br.com.mortix;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Results {

    private Log log;

    public Results(Log log) {
        this.log = log;
    }

    public final HashMap<String, String> streak() {

        int max = 0;
        String name = "";

        for (int i = 0; i < this.log.getPlayers().size(); i++) {

            int num = 0;
            int aux = 0;

            String player = this.log.getPlayers().get(i);

            for (int j = 0; j < this.log.getKillers().size(); j++) {

                String killer = this.log.getKillers().get(j);
                String victim = this.log.getVictims().get(j);

                if (player.equals(killer)) {
                    num++;
                }

                if (player.equals(victim)) {
                    num = 0;
                }

                if (num > aux) {
                    aux = num;
                }
            }

            if (aux > max) {
                max = aux;
                name = player;
            }
        }
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("player", name);
        result.put("max", Integer.toString(max));
        return result;
    }

    public final ArrayList<String> getPlayerUsages(String player)
    {
        ArrayList<String> usedByKiller = new ArrayList<String>();

        int i = 0;
        for (String killer : this.log.getKillers()) {
            if (player.toUpperCase().equals(killer)) {
                usedByKiller.add(this.log.getUsages().get(i));
            }
            i++;
        }
        return usedByKiller;
    }

    public final String preference(String player) {

        int max = 0;
        int pos = 0;
        int aux = 0;

        String preference = "";

        ArrayList<String> playerUsages = this.getPlayerUsages(player.toUpperCase());
        ArrayList<Integer> numberOfUsages = new ArrayList<Integer>();

        if (playerUsages.size() > 0)
        {
            for (String weapon : this.log.getWeapons()) {

                int num = 0;

                for (int i = 0; i < playerUsages.size(); i++) {
                    if (playerUsages.get(i).equals(weapon)) {
                        num++;
                    }
                }

                numberOfUsages.add(num);

                if (numberOfUsages.get(pos) > max) {
                    max = numberOfUsages.get(pos);
                    aux = pos;
                    preference = this.log.getWeapons().get(aux);
                } else {
                    if (numberOfUsages.get(pos) == max) {
                        preference = preference + " and " + this.log.getWeapons().get(pos);
                    }
                }
                pos++;
            }
        }

        return preference;
    }

    public ArrayList<String> fiveKillsPerMinute() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        ArrayList<String> players = new ArrayList<String>();

        for (int i = 0; i < this.log.getPlayers().size(); i++) {

            int num = 0;

            Date start = null;
            Date end = null;

            long diffMS = 0;
            long diffSeconds = 0;
            long diffMinutes = 0;

            String player = this.log.getPlayers().get(i);

            if (!player.equals(Players.WORLD)) {

                for (int j = 0; j < this.log.getKillers().size(); j++) {

                    String killer = this.log.getKillers().get(j);
                    Date dateTime = null;

                    try {
                        dateTime = sdf.parse(this.log.getDates().get(j) + " " + this.log.getHours().get(j));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (player.equals(killer)) {
                        if (num == 0 || ((dateTime.getTime() - start.getTime()) / 1000) > 60) {
                            start = dateTime;
                            num = 0;
                        }
                        num++;

                        if (num == 5) {
                            end = dateTime;
                            num = 0;
                        }
                    }

                    if (end != null) {
                        diffMS = end.getTime() - start.getTime();
                        diffSeconds = diffMS / 1000;
                        diffMinutes = diffSeconds / 60;

                        if (diffMinutes <= 1) {
                            if (players.indexOf(player) < 0) {
                                players.add(player);
                            }
                        }
                    }
                }
            }
        }
        return players;
    }

    public int deathsOfThePlayer(String player)
    {
        int d = 0;
        for (String victim : log.getVictims()) {
            if (player.toUpperCase().equals(victim)) {
                d++;
            }
        }
        return d;
    }

    public int killsOfThePlayer(String player)
    {
        int k = 0;
        for (String killer : log.getKillers()) {
            if (player.toUpperCase().equals(killer)) {
                k++;
            }
        }
        return k;
    }

    public int pointsOfThePlayer(String player)
    {
        int points = 0;

        if (!player.toUpperCase().equals(Players.WORLD))
        {
            int k = killsOfThePlayer(player);
            int d = deathsOfThePlayer(player);
            points = k - d;
        }
        return points;
    }
}
