package risikopackage;

import java.awt.*;
import javax.swing.*;

/*
 * BackgroundImagePanel ist in der Lage ein Hintergrundbild zu empfangen und dieses zu zeichnen.
 *
 * @author Gernot Segieth
 */
class BackgroundImagePanel extends JPanel {
    private Image image;
    private boolean fitImage;

    /**
     * Erzeugt ein BackgroundImagePanel, das sich zun�chst wie ein "normales" JPanel verh�lt.
     */
    BackgroundImagePanel() {
        super();
    }

    /**
     * Erzeugt ein BackgroundImagePanel, das sich zun�chst wie ein "normales" JPanel verh�lt.
     *
     * @param layout ein LayoutManager zur Anordnung von GUI-Komponenten f�r das Panel.
     */
    BackgroundImagePanel(LayoutManager layout) {
        super(layout);
    }

    /**
     * Nimmt das zu zeichnende Bild entgegen und nimmt Einstellungen vor, die das Hintergrundbild im Originalformat
     * und in den Original-Dimensionen ausgeben werden.
     *
     * @param image das zu zeichnende Bild
     */
    void setImage(Image image) {
        this.setImage(image, true);
    }

    /**
     * Nimmt das zu zeichnende Bild entgegen und nimmt Einstellungen vor, die das Hintergrundbild im Originalformat
     * <b>oder in Format und Dimension des Panels ausgeben werden.</b>
     *
     * @param image    das zu zeichnende Bild
     * @param fitImage bei �bergabe von true wird das Bild immer an die Gr��e des Panel angepasst (kann zu Verzerrung f�hren),
     *                 ansonsten wird immer das Original gezeichnet.
     */
    private void setImage(Image image, boolean fitImage) {
        this.image = image;
        this.fitImage = fitImage;
        validate();
        repaint();
    }

    /**
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize() {
        if (image != null) {
            return new Dimension(image.getWidth(this), image.getHeight(this));
        }
        return super.getPreferredSize();
    }

    /**
     * @see javax.swing.JComponent#paintComponent(Graphics g)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            if (fitImage) {
                Dimension size = this.getSize();
                g.drawImage(image, 0, 0, size.width, size.height, this);
            } else {
                g.drawImage(image, 0, 0, this);
            }
        }
    }
}
