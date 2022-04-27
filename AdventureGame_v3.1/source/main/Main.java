package main;

import javax.swing.JFrame;

public class Main {
    
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Adventure Game");

        Display display = new Display();
        window.add(display);

        window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        window.setUndecorated(true);

        //window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        display.setupGame();

        // CALLS display.run();
        display.startGameThread();
    }
    
}
