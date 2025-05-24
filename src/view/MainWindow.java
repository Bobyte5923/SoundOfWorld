package view;

import controller.GameController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Instrument;

public class MainWindow extends JFrame {
    private GameController controller;

    public MainWindow() {
        super("Sound of World");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        controller = new GameController(this);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel instrumentGrid = new JPanel(new GridLayout(2, 3, 10, 10));
        List<Instrument> instruments = controller.loadInstruments();

        for (Instrument instrument : instruments) {
            instrumentGrid.add(new InstrumentPanel(instrument, controller));
        }

        JScrollPane scrollPane = new JScrollPane(instrumentGrid);
        add(scrollPane, BorderLayout.CENTER);
    }
}
