package br.com.mortix.ui;

import javax.swing.*;

public class GUI implements UI {

    @Override
    public void write(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    @Override
    public String read(String msg) {
        String value = JOptionPane.showInputDialog(msg);
        return value;
    }
}