package br.com.mortix.ui;

import java.util.Scanner;

public class Console implements UI {

    private Scanner scanner;

    public Console() {
        this(new Scanner(System.in));
    }

    public Console(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void write(String msg) {
        System.out.println(msg);
    }

    @Override
    public String read(String msg) {
        this.write(msg);
        return this.read();
    }

    public String read() {
        return scanner.nextLine();
    }

    public void separator() {
        write("--------------------------------------------------------------");
    }

    public void newLine() {
        write("");
    }
}
