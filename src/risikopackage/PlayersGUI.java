package risikopackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayersGUI extends JFrame implements ActionListener {

    private final String[] colorsone = {"blau", "rot", "lila", "pink", "grau"};
    private final String[] colorstwo = {"blau", "rot", "lila", "pink", "grau"};
    private JComboBox<String> colorone, colortwo;
    private JLabel wrongcolor;

    public PlayersGUI() {
        this.setLayout(null);
        this.setTitle("Risiko - Spielvorbereitung");
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Willkommen zu einer neuen Runde Risiko", SwingConstants.CENTER);
        title.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.add(title);
        title.setBounds(0, 20, 500, 20);

        JLabel order = new JLabel("Waehlt eure Farbe", SwingConstants.CENTER);
        order.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        this.add(order);
        order.setBounds(0, 60, 500, 20);

        JLabel chooseone = new JLabel("Spieler Eins", SwingConstants.CENTER);
        chooseone.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        this.add(chooseone);
        chooseone.setBounds(0, 100, 250, 20);

        JLabel choosetwo = new JLabel("Spieler Zwei", SwingConstants.CENTER);
        choosetwo.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        this.add(choosetwo);
        choosetwo.setBounds(250, 100, 250, 20);

        wrongcolor = new JLabel("Die Spieler muessen unterschiedliche Farben waehlen!", SwingConstants.CENTER);
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

        JButton startplaying = new JButton("Spiel starten");
        this.add(startplaying);
        startplaying.setBounds(170, 170, 160, 20);
        startplaying.addActionListener(e -> startfct());

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    }

    //create players, "close" PrepWindow, open playingfield
    private void startfct() {
        if (colorone.getSelectedItem() == colortwo.getSelectedItem()) {
            wrongcolor.setVisible(true);
        } else {
            Gameplay.getInstance().getPlayerOne().setColor((String) colorone.getSelectedItem());
            Gameplay.getInstance().getPlayerTwo().setColor((String) colortwo.getSelectedItem());
            this.setVisible(false);
            Gameplay.getInstance().initialising();
            new FieldGUI();
            Gameplay.getInstance().welcomeText();
        }
    }
}
