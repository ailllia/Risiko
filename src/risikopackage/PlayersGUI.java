package risikopackage;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PlayersGUI extends JFrame implements ActionListener {

    private final String[] colorsone = {"blau", "gelb"};
    private final String[] colorstwo = {"rot", "lila"};
    private JComboBox<String> colorone, colortwo;
    private JLabel title, order, chooseone, choosetwo;
    private JButton startplaying;

    public PlayersGUI() {

        this.setLayout(null);
        this.setTitle("Risiko - Spielvorbereitung");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

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

        colorone = new JComboBox<String>(colorsone);
        colorone.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        this.add(colorone);
        colorone.setBounds(75, 120, 100, 20);

        colortwo = new JComboBox<String>(colorstwo);
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
        Main.playerOne = new Player((String) colorone.getSelectedItem());
        Main.playerTwo = new Player((String) colortwo.getSelectedItem());
        this.setVisible(false);
        new FieldGUI();
    }
}
