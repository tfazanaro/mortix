package br.com.mortix;

import java.util.ArrayList;

public final class Weapons {

    private static ArrayList<String> weapons = new ArrayList<String>();

    public void add(String name) {
        weapons.add(name);
    }

    public ArrayList<String> getAll() {
        return weapons;
    }

    public static boolean weaponExists(String weapon) {
        return (weapons.contains(weapon.toUpperCase()));
    }
}
