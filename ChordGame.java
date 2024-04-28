import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class ChordGame {

    private static JFrame frame = new JFrame();
    private static JLabel chordDisplay = new JLabel();
    private static JLabel controlDisplay1 = new JLabel();
    private static JLabel controlDisplay2 = new JLabel();
    private static JLabel controlDisplay3 = new JLabel();
    public static JButton[] pButton = new JButton[2];
    private static JButton[] optButton = new JButton[4];
    private static JButton winDisplay = new JButton();
    private static final Random random = new Random();
    private static Chord chord;
    private static Chord[] opt = new Chord[4];
    private static int[] p = {0, 0};
    public static boolean pressed = false;
    
    public static void nextChord() {
        pButton[0].setText("Player 1: " + p[0] + "/10");
        pButton[1].setText("Player 2: " + p[1] + "/10");
        java.util.Timer timer = new java.util.Timer();
        TimerTask task = new Flash();
        timer.schedule(task, 500);
        chord = Chord.randomChord();
        chordDisplay.setText(chord.getName());
        chordDisplay.setIcon(chord.getIcon());
        for (int i = 0; i < 4; i++) {
            switch (random.nextInt(0, 5)) {
                case 0: case 3:
                    opt[i] = new Chord(chord.r, Chord.TYPE.randomType());
                    break;
                case 1: 
                    opt[i] = new Chord(Chord.ROOT.randomRoot(), chord.t);
                    break;
                case 2: case 4:
                    opt[i] = Chord.randomChord();
            }
            for (int j = 0; j < i; j++)
                if (opt[j].equals(opt[i])) {
                    i--;
                    break;
                }
            if (opt[i].equals(chord))
                i--;
        }
        opt[random.nextInt(0, 4)] = chord;
        for (int i = 0; i < 4; i ++)
            optButton[i].setText(((i + 1) + ") " + opt[i].getName()));
    }

    public static void main (String[] args) {
        
        frame.setVisible(true);
        frame.setTitle("Chord Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setSize(752, 540);
        Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dm.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dm.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        
        frame.add(controlDisplay1);
        controlDisplay1.setBounds(300, 275, 240, 240);
        controlDisplay1.setText("Player 1 uses Q, W, A, S");
        frame.add(controlDisplay2);
        controlDisplay2.setBounds(300, 300, 240, 240);
        controlDisplay2.setText("Player 2 uses I, O, K, L");
        frame.add(controlDisplay3);
        controlDisplay3.setBounds(300, 325, 240, 240);
        controlDisplay3.setText("<Space bar> to reset");

        frame.add(chordDisplay);
        chordDisplay.setBounds(256, 0, 240, 240);

        frame.add(winDisplay);
        winDisplay.setBounds(256, 100, 240, 100);
        winDisplay.setVisible(false);

        pButton[0] = new JButton();
        frame.add(pButton[0]);
        pButton[0].setBounds(100, 200, 100, 50);
        pButton[0].setOpaque(true);
        pButton[0].setBorder(new LineBorder(Color.black));
        pButton[0].setBackground(Color.white);

        pButton[1] = new JButton();
        frame.add(pButton[1]);
        pButton[1].setBounds(550, 200, 100, 50);
        pButton[1].setOpaque(true);
        pButton[1].setBorder(new LineBorder(Color.black));
        pButton[1].setBackground(Color.white);

        for (int i = 0; i < 4; i++) {
            optButton[i] = new JButton();
            frame.add(optButton[i]);
        }
        optButton[0].setBounds(275, 225, 100, 50);
        optButton[1].setBounds(375, 225, 100, 50);
        optButton[2].setBounds(275, 275, 100, 50);
        optButton[3].setBounds(375, 275, 100, 50);

        //gameplay
        nextChord();

        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int player = 1;
                int keyCode = e.getKeyCode();
                int i = -1;
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    p = new int[] {0, 0};
                    nextChord();
                    pressed = false;
                    winDisplay.setVisible(false);
                    chordDisplay.setVisible(true);
                } else if (pressed)
                    return;
                else
                    pressed = true;

                switch (keyCode) {
                    //Option 1
                    case KeyEvent.VK_Q: 
                        player = 0; 
                    case KeyEvent.VK_I: 
                        i = 0;
                        break;

                    //Option 2
                    case KeyEvent.VK_W: 
                        player = 0; 
                    case KeyEvent.VK_O: 
                        i = 1;
                        break;

                    //Option 3
                    case KeyEvent.VK_A: 
                        player = 0; 
                    case KeyEvent.VK_K: 
                        i = 2;
                        break;

                    //Option 4
                    case KeyEvent.VK_S: 
                        player = 0; 
                    case KeyEvent.VK_L: 
                        i = 3;
                        break;
                }
                if (i != -1) {
                    if (opt[i].equals(chord)) {
                        p[player]++;
                        pButton[player].setBackground(Color.green);
                    } else {
                        p[(player+1)%2]++;
                        pButton[player].setBackground(Color.red);
                    }
                    if (p[0] == 10) {
                        pButton[0].setText("Player 1: 10/10");
                        winDisplay.setText("Player 1 Wins");
                        winDisplay.setVisible(true);
                        chordDisplay.setVisible(false);
                        pressed = true;
                    } else if (p[1] == 10) {
                        pButton[1].setText("Player 2: 10/10");
                        winDisplay.setText("Player 2 Wins");
                        winDisplay.setVisible(true);
                        chordDisplay.setVisible(false);
                        pressed = true;
                    } else {
                    nextChord();
                    }
                }  else {pressed = false;}
            }
        });

    }


}

class Flash extends TimerTask {
    public void run(){
        ChordGame.pButton[0].setBackground(Color.white);
        ChordGame.pButton[1].setBackground(Color.white);
        ChordGame.pressed = false;
    }
}