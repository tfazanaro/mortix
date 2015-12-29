package br.com.mortix;

import java.util.ArrayList;

public class Weapons {

    private ArrayList<String> weapons = new ArrayList<String>();

    public void add(String name) {
        this.weapons.add(name);
    }

    public ArrayList<String> getAll() {
        return this.weapons;
    }
}
