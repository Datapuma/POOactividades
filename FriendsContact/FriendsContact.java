package FriendsContact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FriendsContact extends JFrame {

    private JTextField nameField, numberField;
    private JTextArea displayArea;
    private JButton addButton, updateButton, deleteButton, displayButton;

    public FriendsContact() {
        // Configuración de la ventana principal
        setTitle("Friends Contacts");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Crear panel para los campos de entrada
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Number:"));
        numberField = new JTextField();
        inputPanel.add(numberField);

        add(inputPanel, BorderLayout.NORTH);

        // Crear área de texto para mostrar los contactos
        displayArea = new JTextArea();
        displayArea.setEditable(false);  // El área de texto no debe ser editable
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Crear panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        addButton = new JButton("Add");
        buttonPanel.add(addButton);

        updateButton = new JButton("Update");
        buttonPanel.add(updateButton);

        deleteButton = new JButton("Delete");
        buttonPanel.add(deleteButton);

        displayButton = new JButton("Display");
        buttonPanel.add(displayButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Configuración de eventos para los botones
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addFriend();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateFriend();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteFriend();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayFriends();
            }
        });
    }

    private void addFriend() {
        String name = nameField.getText();
        String number = numberField.getText();

        if (name.isEmpty() || number.isEmpty()) {
            displayArea.setText("Please enter both name and number.");
            return;
        }

        try {
            long num = Long.parseLong(number); // Verificación de número válido

            File file = new File("friendsContact.txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            boolean found = false;

            // Revisar si el contacto ya existe
            while (raf.getFilePointer() < raf.length()) {
                String nameNumberString = raf.readLine();
                String[] lineSplit = nameNumberString.split("!");
                String existingName = lineSplit[0];

                if (existingName.equalsIgnoreCase(name)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                raf.seek(raf.length()); // Mover al final del archivo para agregar el nuevo contacto
                raf.writeBytes(name + "!" + num + System.lineSeparator());
                displayArea.setText("Friend added.");
            } else {
                displayArea.setText("The friend already exists.");
            }

            raf.close();
        } catch (IOException | NumberFormatException e) {
            displayArea.setText("Error: " + e.getMessage());
        }
    }

    private void updateFriend() {
        String name = nameField.getText();
        String number = numberField.getText();

        if (name.isEmpty() || number.isEmpty()) {
            displayArea.setText("Please enter both name and number.");
            return;
        }

        try {
            long num = Long.parseLong(number); // Verificación de número válido

            File file = new File("friendsContact.txt");
            if (!file.exists()) {
                displayArea.setText("No contacts to update.");
                return;
            }

            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            boolean found = false;

            File tmpFile = new File("temp.txt");
            RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw");

            while (raf.getFilePointer() < raf.length()) {
                String nameNumberString = raf.readLine();
                String[] lineSplit = nameNumberString.split("!");
                String existingName = lineSplit[0];

                if (existingName.equalsIgnoreCase(name)) {
                    nameNumberString = name + "!" + num;
                    found = true;
                }
                tmpraf.writeBytes(nameNumberString + System.lineSeparator());
            }

            if (found) {
                raf.seek(0);
                tmpraf.seek(0);

                while (tmpraf.getFilePointer() < tmpraf.length()) {
                    raf.writeBytes(tmpraf.readLine() + System.lineSeparator());
                }

                raf.setLength(tmpraf.length());
                displayArea.setText("Friend updated.");
            } else {
                displayArea.setText("Friend not found.");
            }

            tmpraf.close();
            raf.close();
            tmpFile.delete();
        } catch (IOException | NumberFormatException e) {
            displayArea.setText("Error: " + e.getMessage());
        }
    }

    private void deleteFriend() {
        String name = nameField.getText();

        if (name.isEmpty()) {
            displayArea.setText("Please enter a name.");
            return;
        }

        try {
            File file = new File("friendsContact.txt");
            if (!file.exists()) {
                displayArea.setText("No contacts to delete.");
                return;
            }

            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            boolean found = false;

            File tmpFile = new File("temp.txt");
            RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw");

            while (raf.getFilePointer() < raf.length()) {
                String nameNumberString = raf.readLine();
                String[] lineSplit = nameNumberString.split("!");
                String existingName = lineSplit[0];

                if (!existingName.equalsIgnoreCase(name)) {
                    tmpraf.writeBytes(nameNumberString + System.lineSeparator());
                } else {
                    found = true;
                }
            }

            if (found) {
                raf.seek(0);
                tmpraf.seek(0);

                while (tmpraf.getFilePointer() < tmpraf.length()) {
                    raf.writeBytes(tmpraf.readLine() + System.lineSeparator());
                }

                raf.setLength(tmpraf.length());
                displayArea.setText("Friend deleted.");
            } else {
                displayArea.setText("Friend not found.");
            }

            tmpraf.close();
            raf.close();
            tmpFile.delete();
        } catch (IOException e) {
            displayArea.setText("Error: " + e.getMessage());
        }
    }

    private void displayFriends() {
        try {
            File file = new File("friendsContact.txt");
            if (!file.exists()) {
                displayArea.setText("No contacts to display.");
                return;
            }

            RandomAccessFile raf = new RandomAccessFile(file, "r");
            StringBuilder contacts = new StringBuilder();

            while (raf.getFilePointer() < raf.length()) {
                String nameNumberString = raf.readLine();
                String[] lineSplit = nameNumberString.split("!");
                String name = lineSplit[0];
                long number = Long.parseLong(lineSplit[1]);

                contacts.append("Name: ").append(name).append(", Number: ").append(number).append("\n");
            }

            displayArea.setText(contacts.toString());
            raf.close();
        } catch (IOException e) {
            displayArea.setText("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FriendsContact app = new FriendsContact();
            app.setVisible(true);
        });
    }
}


