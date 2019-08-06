package risikopackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayersGUI extends JFrame implements ActionListener {

    private final String[] colorsone = {"blau", "rot", "lila", "pink", "grau"};
    private final String[] colorstwo = {"blau", "rot", "lila", "pink", "grau"};
    private JComboBox<String> colorone, colortwo;
    private JLabel title, order, chooseone, choosetwo, wrongcolor;
    private JButton startplaying;

    public PlayersGUI() {
        this.setLayout(null);
        this.setTitle("Risiko - Spielvorbereitung");
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        title = new JLabel("Willkommen zu einer neuen Runde Risiko", SwingConstants.CENTER);
        title.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.add(title);
        title.setBounds(0, 20, 500, 20);

        order = new JLabel("Wählt eure Farbe", SwingConstants.CENTER);
        order.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        this.add(order);
        order.setBounds(0, 60, 500, 20);

        chooseone = new JLabel("Spieler Eins", SwingConstants.CENTER);
        chooseone.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        this.add(chooseone);
        chooseone.setBounds(0, 100, 250, 20);

        choosetwo = new JLabel("Spieler Zwei", SwingConstants.CENTER);
        choosetwo.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        this.add(choosetwo);
        choosetwo.setBounds(250, 100, 250, 20);

        wrongcolor = new JLabel("Die Spieler müssen unterschiedliche Farben wählen!", SwingConstants.CENTER);
        wrongcolor.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        wrongcolor.setVisible(false);
        this.add(wrongcolor);
        wrongcolor.setBounds(90, 210, 300, 20);

        colorone = new JComboBox<>(colorsone);
        colorone.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        this.add(colorone);
        colorone.setBounds(75, 120, 100, 20);

        colortwo = new JComboBox<>(colorstwo);
        colortwo.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        this.add(colortwo);
        colortwo.setBounds(325, 120, 100, 20);

        startplaying = new JButton("Spiel starten");
        this.add(startplaying);
        startplaying.setBounds(170, 170, 160, 20);
        startplaying.addActionListener(e -> startfct());

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
    }

    //create players, "close" PrepWindow, open playingfield
    private void startfct() {
        if (colorone.getSelectedItem() == colortwo.getSelectedItem()) {
            wrongcolor.setVisible(true);
        } else {
            Main.playerOne.setColor((String) colorone.getSelectedItem());
            Main.playerTwo.setColor((String) colortwo.getSelectedItem());
            this.setVisible(false);
            //sollten in die main:
            Gameplay.initialising();
            new FieldGUI();
            Gameplay.welcome();
        }
    }
}
