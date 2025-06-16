package view;

import controller.GameController;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import model.Instrument;

/**
 * Sound-of-World main window.
 */
public class MainWindow extends JFrame {

    private final GameController      controller;
    private final GameMenuBar         menuBar;
    private final InstrumentListPanel instrumentListPanel;
    private final JPanel              centerPanel;

    private int panelCount = 0;             
    private static final int PANELS_PER_ROW = 4;

    public MainWindow() {
        super("Sound of World 🌍");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(1200, 800));

        controller = new GameController(this);

        menuBar = new GameMenuBar();
        menuBar.addResetListener(e -> controller.resetGame());
        setJMenuBar(menuBar);
        controller.setMenuBar(menuBar);

        instrumentListPanel =
                new InstrumentListPanel(controller.getAllInstrumentNames());
        add(instrumentListPanel, BorderLayout.WEST);

        centerPanel = new JPanel(new GridLayout(0, PANELS_PER_ROW, 10, 10));
        JScrollPane scroll = new JScrollPane(centerPanel);
        scroll.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        controller.resetGame();
    }


    public void displayInstrumentGrid() {
        List<Instrument> instruments = controller.getCurrentGameInstruments();

        centerPanel.removeAll();
        panelCount = 0;

        for (Instrument inst : instruments) {
            centerPanel.add(new InstrumentGuessPanel(inst, controller));
            panelCount++;

            if (panelCount % PANELS_PER_ROW == 0) {
                centerPanel.add(Box.createVerticalStrut(10));
            }
        }
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public void updateLives(int lives) { menuBar.setLives(lives); }

    public void showVictoryDialog() {
        try {
            ImageIcon icon = loadClippyIcon();
            JLabel imgLabel = new JLabel(icon);

            JPanel body = new JPanel(new BorderLayout(10, 10));
            body.add(new JLabel(
                    "<html>Bravo&nbsp;! Tu as deviné tous les instruments&nbsp;! 🎉<br>"
                    + "Rejouer&nbsp;?</html>"),
                    BorderLayout.NORTH);
            body.add(imgLabel, BorderLayout.CENTER);

            int choice = JOptionPane.showOptionDialog(
                    this, body, "Victoire !",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, new String[]{"Réinitialiser", "Quitter"}, "Réinitialiser");

            if (choice == JOptionPane.YES_OPTION) controller.resetGame();
            else System.exit(0);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Impossible de charger clippy.jpg : " + ex.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showGameOverDialog() {
        int opt = JOptionPane.showOptionDialog(
                this, "Plus de vies… 😢\nRejouer ?", "Partie perdue",
                JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE,
                null, new String[]{"Réinitialiser", "Quitter"}, "Réinitialiser");

        if (opt == JOptionPane.YES_OPTION) controller.resetGame();
        else System.exit(0);
    }

    private ImageIcon loadClippyIcon() throws IOException {

        /* 1) D’abord via le class-path */
        String cpPath = "/images/imagesClippy/clippy.jpg";
        URL url = getClass().getResource(cpPath);
        if (url != null) {
            return scale(new ImageIcon(url));
        }

        Path fsPath = Paths.get(System.getProperty("user.dir"),
                                "resources/images/imagesClippy/clippy.jpg")
                           .normalize();
        if (Files.exists(fsPath)) {
            BufferedImage img = ImageIO.read(fsPath.toFile());
            return scale(new ImageIcon(img));
        }

        throw new IOException("clippy.jpg non trouvé ni dans le class-path ni dans "
                              + fsPath);
    }

    private ImageIcon scale(ImageIcon raw) {
        Image scaled = raw.getImage()
                          .getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
