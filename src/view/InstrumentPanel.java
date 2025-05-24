package view;

import controller.GameController;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import model.Instrument;

public class InstrumentPanel extends JPanel {
    private Instrument instrument;
    private GameController controller;

    public InstrumentPanel(Instrument instrument, GameController controller) {
        this.instrument = instrument;
        this.controller = controller;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel content;

        try {
            Image img = ImageIO.read(new File(instrument.getImagePath()));
            if (img == null) throw new IOException("Image invalide ou vide");
            JLabel label = new JLabel(new ImageIcon(img.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
            label.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    controller.playSound(instrument);
                }
            });
            content = label;
        } catch (IOException e) {
            content = new JLabel("Image introuvable", SwingConstants.CENTER);
            content.setForeground(Color.RED);
        }

        add(content, BorderLayout.CENTER);
        add(new JLabel(instrument.getName(), SwingConstants.CENTER), BorderLayout.SOUTH);
    }
}
