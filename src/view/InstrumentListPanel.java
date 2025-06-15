package view;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class InstrumentListPanel extends JPanel {
    private JList<String> instrumentJList;

    public InstrumentListPanel(List<String> instrumentNames) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(150, 0)); // Largeur fixe

        instrumentJList = new JList<>(instrumentNames.toArray(new String[0]));
        instrumentJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(instrumentJList);
        add(scrollPane, BorderLayout.CENTER);
        setBorder(BorderFactory.createTitledBorder("Instruments"));
    }

    public void updateList(List<String> newInstrumentNames) {
        instrumentJList.setListData(newInstrumentNames.toArray(new String[0]));
    }
}