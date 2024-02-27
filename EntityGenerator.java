package org.DDD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EntityGenerator extends JFrame {
    private final JTextField classNameField;
    private final JTextArea variablesArea;
    private final JTextArea generatedCodeArea;
    private final JCheckBox toStringCheckBox;
    private final JCheckBox hashCodeCheckBox;


    public EntityGenerator() {
        setTitle("Entity Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background

        // Checkbox panel
        JPanel checkboxPanel = new JPanel(new GridLayout(3, 1));
        checkboxPanel.setBackground(new Color(240, 240, 240));
        toStringCheckBox = new JCheckBox("Generate toString() method");
        toStringCheckBox.setForeground(Color.BLUE);
        hashCodeCheckBox = new JCheckBox("Generate hashCode() method");
        hashCodeCheckBox.setForeground(Color.BLUE);
        checkboxPanel.add(toStringCheckBox);
        checkboxPanel.add(hashCodeCheckBox);



        // Class name input panel
        JPanel classNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        classNamePanel.setBackground(new Color(240, 240, 240));
        JLabel classNameLabel = new JLabel("Enter the name of the class:");
        classNameLabel.setForeground(Color.BLUE);
        classNameField = new JTextField(20);
        classNamePanel.add(classNameLabel);
        classNamePanel.add(classNameField);

        // Variables input panel
        JPanel variablesPanel = new JPanel(new BorderLayout());
        variablesPanel.setBackground(new Color(240, 240, 240));
        JLabel variablesLabel = new JLabel("Enter variable name and type (one per line):");
        variablesLabel.setForeground(Color.BLUE);
        variablesArea = new JTextArea(5, 20);
        variablesArea.setLineWrap(true);
        JScrollPane variablesScrollPane = new JScrollPane(variablesArea);
        variablesPanel.add(variablesLabel, BorderLayout.NORTH);
        variablesPanel.add(variablesScrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();

        // Generated code output panel
        JPanel generatedCodePanel = new JPanel(new BorderLayout());
        generatedCodePanel.setBackground(new Color(240, 240, 240));
        JLabel generatedCodeLabel = new JLabel("Generated class code:");
        generatedCodeLabel.setForeground(Color.BLUE);
        generatedCodeArea = new JTextArea(5, 20);
        generatedCodeArea.setLineWrap(true);
        generatedCodeArea.setEditable(false);
        JScrollPane generatedCodeScrollPane = new JScrollPane(generatedCodeArea);
        generatedCodePanel.add(generatedCodeLabel, BorderLayout.NORTH);
        generatedCodePanel.add(generatedCodeScrollPane, BorderLayout.CENTER);

        // Add components to the frame
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(classNamePanel, BorderLayout.NORTH);
        leftPanel.add(variablesPanel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(checkboxPanel, BorderLayout.NORTH);
        topPanel.add(leftPanel, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(generatedCodePanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(240, 240, 240));
        JButton generateButton = new JButton("Generate");
        generateButton.setBackground(Color.BLUE);
        generateButton.setForeground(Color.WHITE);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateCode();
            }
        });
        buttonPanel.add(generateButton);
        return buttonPanel;
    }

    private void generateCode() {
        String className = classNameField.getText().trim();

        // Check if className field is empty
        if (className.isEmpty()) {
            generatedCodeArea.setText("Enter a class name please.");
            return;

        }

        EntityBuilder builder = new EntityBuilder(className);

        String[] lines = variablesArea.getText().split("\\n");
        for (String line : lines) {
            int spaceIndex = line.indexOf(' ');
            if (spaceIndex != -1 && spaceIndex < line.length() - 1) {
                String variableType = line.substring(0, spaceIndex).trim();
                String variableName = line.substring(spaceIndex + 1).trim();
                builder.addVariable(variableType, variableName);
            }
        }

        Entity entity = builder.build();

        StringBuilder classCode = new StringBuilder();
        classCode.append("public class ").append(capitalize(className)).append(" {\n\n");
        List<String> variables = builder.getVariables();
        List<String> variableTypes = builder.getVariableTypes();


        // appended the ID of the class here
        classCode.append("\tprivate long ").append(className).append("Id").append(";\n");
        for (int i = 0; i < variables.size(); i++) {
            String variable = variables.get(i);
            String variableType = variableTypes.get(i);


            classCode.append("\tprivate ").append(variableType).append(" ").append(variable).append(";\n");
        }
        classCode.append("\n");
        
        classCode.append(entity.generateConstructor());
        classCode.append(entity.addID());
        classCode.append(entity.generateGet());


        if (toStringCheckBox.isSelected()) {
            classCode.append(entity.generatetoString());
        }
        classCode.append("\n");

        if (hashCodeCheckBox.isSelected()) {
            classCode.append(entity.generateHashCode());
        }

        classCode.append("}\n");


        generatedCodeArea.setText(classCode.toString());
    }

    private String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

}