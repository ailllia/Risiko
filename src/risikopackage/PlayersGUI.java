package risikopackage;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class PlayersGUI extends JFrame implements ActionListener {
    private JComboBox<String> colorone, colortwo;
    private JLabel wrongcolor, choosename, nameTooLong;
    private JTextField nameOne, nameTwo;

    /**
     * Creates a new PlayerGUI. It creates a new window which welcomes the players and asks for their names and playing colors.
     */
    public PlayersGUI() {
        this.setLayout(null);
        this.setTitle("Risiko - Spielvorbereitung");
        this.setSize(500, 300);
        this.getContentPane().setBackground(new java.awt.Color(242, 228, 218));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Willkommen zu einer neuen Runde Risiko", SwingConstants.CENTER);
        title.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.add(title);
        title.setBounds(0, 20, 500, 20);

        JLabel order = new JLabel("Waehlt euren Namen und eure Farbe", SwingConstants.CENTER);
        order.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        this.add(order);
        order.setBounds(0, 60, 500, 20);

        JLabel chooseone = new JLabel("Spieler Eins", SwingConstants.CENTER);
        chooseone.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        this.add(chooseone);
        chooseone.setBounds(0, 90, 250, 20);

        JLabel choosetwo = new JLabel("Spieler Zwei", SwingConstants.CENTER);
        choosetwo.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        this.add(choosetwo);
        choosetwo.setBounds(250, 90, 250, 20);

        wrongcolor = new JLabel("Jeder Spieler muss eine unterschiedliche Farbe waehlen!", SwingConstants.CENTER);
        wrongcolor.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        wrongcolor.setVisible(false);
        this.add(wrongcolor);
        wrongcolor.setBounds(75, 210, 360, 20);

        choosename = new JLabel("Jeder Spieler muss einen (unterschiedelichen) Namen w�hlen!", SwingConstants.CENTER);
        choosename.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        choosename.setVisible(false);
        this.add(choosename);
        choosename.setBounds(75, 210, 360, 20);

        nameTooLong = new JLabel("Ein Name darf maximal 15 Zeichen lang sein!", SwingConstants.CENTER);
        nameTooLong.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        nameTooLong.setVisible(false);
        this.add(nameTooLong);
        nameTooLong.setBounds(75, 210, 360, 20);

        JLabel inputNameOne = new JLabel("Name:");
        this.add(inputNameOne);
        inputNameOne.setBounds(30, 110, 45, 20);

        JLabel inputNameTwo = new JLabel("Name:");
        this.add(inputNameTwo);
        inputNameTwo.setBounds(280, 110, 45, 20);

        JLabel inputColorOne = new JLabel("Farbe:");
        this.add(inputColorOne);
        inputColorOne.setBounds(30, 130, 45, 20);

        JLabel inputColorTwo = new JLabel("Farbe:");
        this.add(inputColorTwo);
        inputColorTwo.setBounds(280, 130, 45, 20);

        nameOne = new JTextField();
        this.add(nameOne);
        nameOne.setBounds(75, 110, 101, 20);

        nameTwo = new JTextField();
        this.add(nameTwo);
        nameTwo.setBounds(325, 110, 101, 20);

        String[] colorsone = {"blau", "rot", "lila", "pink", "grau"};
        colorone = new JComboBox<>(colorsone);
        colorone.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        this.add(colorone);
        colorone.setBounds(75, 130, 100, 20);

        String[] colorstwo = {"blau", "rot", "lila", "pink", "grau"};
        colortwo = new JComboBox<>(colorstwo);
        colortwo.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        this.add(colortwo);
        colortwo.setBounds(325, 130, 100, 20);

        JButton startplaying = new JButton("Spiel starten");
        this.add(startplaying);
        startplaying.setBounds(170, 170, 160, 20);
        startplaying.addActionListener(e -> startfct());

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    }

    /**
     * Opens a new window with the playing field, sets the name and the color of the players from InputFields and SelectedItems
     * and closes this window.
     */
    private void startfct() {
        if (colorone.getSelectedItem() == colortwo.getSelectedItem()) {
            wrongcolor.setVisible(true);
            choosename.setVisible(false);
            nameTooLong.setVisible(false);
        } else if (nameOne.getText().equals(nameTwo.getText())
                || nameOne.getText().equals("") || nameTwo.getText().equals("")
                || nameOne.getText().trim().isEmpty() || nameTwo.getText().trim().isEmpty()) {
            wrongcolor.setVisible(false);
            choosename.setVisible(true);
            nameTooLong.setVisible(false);
        } else if (nameOne.getText().length() > 15 || nameTwo.getText().length() > 15) {
            wrongcolor.setVisible(false);
            choosename.setVisible(false);
            nameTooLong.setVisible(true);
        } else {
            Gameplay.getInstance().getPlayerOne().setColor((String) colorone.getSelectedItem());
            Gameplay.getInstance().getPlayerTwo().setColor((String) colortwo.getSelectedItem());
            Gameplay.getInstance().getPlayerOne().setName((nameOne.getText()));
            Gameplay.getInstance().getPlayerTwo().setName((nameTwo.getText()));
            this.setVisible(false);
            Gameplay.getInstance().initialising();
            new FieldGUI();
            Gameplay.getInstance().welcomeText();
        }
    }
}

