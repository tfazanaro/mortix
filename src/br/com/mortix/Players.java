package br.com.mortix;

import java.util.ArrayList;

public final class Players {

    public static final String WORLD = "<WORLD>"; // Default = Computer
    private static ArrayList<String> players = new ArrayList<String>();

    public void add(String name) {
        players.add(name);
    }

    public ArrayList<String> getAll() {
        return players;
    }

    public static boolean playerExists(String player) {
        return (players.contains(player.toUpperCase()));
    }
}
