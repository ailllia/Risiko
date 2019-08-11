package risikopackage;

import java.awt.*;
import javax.swing.JPanel;

/*
 * BackgroundImagePanel ist in der Lage ein Hintergrundbild zu empfangen und dieses zu zeichnen.
 *
 * @author Gernot Segieth
 *
 * Quelle: Hintergrundbild in eine GUI einfuegen (Java). In: Byte-Welt Wiki. 
 * URL: https://wiki.byte-welt.net/wiki/Hintergrundbild_in_eine_GUI_einfügen_(Java) [11.08.2019]
 */
class BackgroundImagePanel extends JPanel {
   private Image image;
   private boolean fitImage;

   /**
    * Erzeugt ein BackgroundImagePanel, das sich zunächst wie ein "normales" JPanel verhält.
    */
   BackgroundImagePanel() {
      super();
   }

   /**
    * Erzeugt ein BackgroundImagePanel, das sich zunächst wie ein "normales" JPanel verhält.
    *
    * @param layout ein LayoutManager zur Anordnung von GUI-Komponenten für das Panel.
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
    * @param image das zu zeichnende Bild
    * @param fitImage bei Übergabe von true wird das Bild immer an die Größe des Panel angepasst (kann zu Verzerrung führen),
    * ansonsten wird immer das Original gezeichnet.
    */
   void setImage(Image image, boolean fitImage) {
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
      if(image != null) {
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
      if(image != null) {
         if(fitImage) {
            Dimension size = this.getSize();
            g.drawImage(image, 0, 0, size.width, size.height, this);
         } else {
            g.drawImage(image, 0, 0, this);
         }
      }
   }
}
