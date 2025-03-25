import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.awt.event.ActionEvent;

public class EntryScreen extends JFrame {
    private JTextField txtName; // name
    private JTextField txtDob; // dob
    private JButton cmdSave;
    private JButton cmdCancel;

    private JPanel pnlCommand;
    private JPanel pnlDisplay;

    private ContactListing contactListing;

    /**
     * EntryScreen Constructor
     * @param contactListing The panel that contains the table displaying contacts
     */
    public EntryScreen(ContactListing contactListing) {
        this.contactListing = contactListing;
        setTitle("Entering new person");
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();
        pnlDisplay.add(new JLabel("Name:"));
        txtName = new JTextField(20);
        pnlDisplay.add(txtName);
        pnlDisplay.add(new JLabel("Date Of Birth:"));
        txtDob = new JTextField("yyyy-mm-dd");
        pnlDisplay.add(txtDob);
        pnlDisplay.setLayout(new GridLayout(2, 4));

        cmdSave = new JButton("Save");
        cmdCancel = new JButton("Cancel");

        cmdSave.addActionListener(new SaveButtonListener());
        cmdCancel.addActionListener(new CancelButtonListener());

        pnlCommand.add(cmdSave);
        pnlCommand.add(cmdCancel);
        add(pnlDisplay, BorderLayout.CENTER);
        add(pnlCommand, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    // Closes the EntryScreen Frame/window when the 'Close' button is clicked
    private class CancelButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
        }

    }

    // Checks the validity of the data inputted- adds it to a list of contacts (clist)
    private class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String[] name = txtName.getText().split(" ");
            String firstName = name[0];
            String lastName = name[1];
            String dobText = txtDob.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            if (firstName.isBlank() || lastName.isBlank()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                LocalDate dob = LocalDate.parse(dobText, formatter);
                Contact c = new Contact(firstName + " " + lastName, dob);
                contactListing.addContact(c);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid date of birth (yyyy-mm-dd).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            setVisible(false);
            dispose();
        }

    }
}
