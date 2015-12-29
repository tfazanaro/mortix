package br.com.mortix;

import java.util.ArrayList;

public final class Players {

    private ArrayList<String> players = new ArrayList<String>();

    public static final String WORLD = "<WORLD>"; // Default = Computer

    public void add(String name)
    {
        this.players.add(name);
    }

    public ArrayList<String> getAll()
    {
        return this.players;
    }
}