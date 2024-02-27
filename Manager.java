package org.DDD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Manager extends JFrame implements ActionListener {
    public Manager() {
        setTitle("Main Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Creating buttons
        JButton valueObjectsButton = new JButton("Value Object");
        JButton entitiesButton = new JButton("Entities");
        JButton domainPrimitivesButton = new JButton("Domain Primitives");

        // Adding action listeners
        valueObjectsButton.addActionListener(this);
        entitiesButton.addActionListener(this);
        domainPrimitivesButton.addActionListener(this);

        // Adding buttons to main panel
        JPanel mainPanel = new JPanel(new FlowLayout());
        mainPanel.add(valueObjectsButton);
        mainPanel.add(entitiesButton);
        mainPanel.add(domainPrimitivesButton);

        // Adding components to main frame
        add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Value Object")) {
            dispose(); // Close the current frame
            new ValueObjectGenerator(); // Open the Value Objects GUI
        } else if (e.getActionCommand().equals("Entities")) {
            dispose(); // Close the current frame
            new EntityGenerator(); // Open the Entities GUI
        } else if (e.getActionCommand().equals("Domain Primitives")) {
            dispose();
            new DomainPrimitiveGenerator();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Manager frame = new Manager();
            frame.setVisible(true);
        });
    }
}