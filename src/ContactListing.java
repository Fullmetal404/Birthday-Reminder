import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class ContactListing extends JPanel{
    private ArrayList<Contact> clist;
    private JScrollPane scrollPane;

    private JTable table;
    private DefaultTableModel model;

    //Create table for displaying contacts
    public ContactListing() {
        super(new GridLayout(1, 0));
        clist = loadContacts("Contacts.dat");
        String[] columnNames = {"First Name", "Last Name", "Date of birth", "Age"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        table.setBackground(Color.DARK_GRAY);
        table.setForeground(Color.WHITE);
        showTable(clist);
        scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    /**
     * showTable Mutator Method
     * @param clist the arraylist that contain contact
     */
    private void showTable(ArrayList<Contact> clist) {
        for (Contact c : clist) {
            addToTable(c);
        }
    }

    /**
     * addToTable Mutator Method 
     * @param c a contact
     */
    private void addToTable(Contact c) {
        DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        String[] name = c.getName().split(" ");
        String[] item = {name[0], name[1], "" + c.getDob().format(Formatter), "" + c.getAge()};
        model.addRow(item);
    }

    /**
     * addContact Mutator Method
     * @param c a contact
     */
    public void addContact(Contact c) {
        clist.add(c);
        addToTable(c);
        try {
            FileWriter writer = new FileWriter("contacts.dat", true);
            // write the contact to the text file
            writer.write(c.toString());
            writer.write("\n");
            writer.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * loadContact Method
     * @param cfile the file containing contact data
     * @return a arraylist of contacts 
     */
    private ArrayList<Contact> loadContacts(String cfile) {
        Scanner cscan = null;
        ArrayList<Contact> clist = new ArrayList<Contact>();
        try {
            cscan = new Scanner(new File(cfile));
            while (cscan.hasNext()) {
                String[] nextLine = cscan.nextLine().split(" ");
                String name = nextLine[0] + " " + nextLine[1];
                LocalDate dob = LocalDate.parse(nextLine[2]);
                Contact c = new Contact(name, dob);
                clist.add(c);
            }
            cscan.close();
        } 
        catch (IOException e) {}
        return clist;
    }

    /**
     * main Main Method
     * @param args a string array argument
     */
    public static void main(String args[])
    {
        //Creating the Frame and adding components
        JFrame frame = new JFrame("Birthday Reminder App");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.setResizable(false);
        frame.setSize(700, 500);
        ImageIcon image = new ImageIcon("Cake.PNG");
        frame.setIconImage(image.getImage());

        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu cMenu = new JMenu("Contacts");
         //JMenu m2 = new JMenu("Reminder");
        mb.add(cMenu);
        //mb.add(m2);
        JButton addButton = new JButton("Add");
        JMenu sMenu = new JMenu("Sort");
        //JRadioButton m21 = new JRadioButton("1 Day");
        //JRadioButton m23 = new JRadioButton("3 Days");
        //JRadioButton m20 = new JRadioButton("5 Days");
        cMenu.add(addButton);
        cMenu.add(sMenu);
        JRadioButtonMenuItem nSort = new JRadioButtonMenuItem("by Name");
        JRadioButtonMenuItem aSort = new JRadioButtonMenuItem("by Age");
        JRadioButtonMenuItem dSort = new JRadioButtonMenuItem("by DOB");
        ButtonGroup sortGroup = new ButtonGroup();
        sortGroup.add(nSort);
        sortGroup.add(aSort);
        sortGroup.add(dSort);
        sMenu.add(nSort);
        sMenu.add(aSort);
        sMenu.add(dSort);
        //m2.add(m21);
        //m2.add(m23);
        //m2.add(m20);

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel();
        panel.setBackground(Color.lightGray);
        JLabel label = new JLabel("Find");
        JTextField tf = new JTextField(20);
        JButton remove = new JButton("Remove");
        panel.add(label);
        panel.add(tf);
        panel.add(remove);

        // Create and set up the content pane.
        ContactListing newContentPane = new ContactListing();
        //newContentPane.setOpaque(false);
        //frame.setContentPane(newContentPane);

        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, newContentPane);
        frame.setVisible(true);

        //Create a birthday notification for contacts whom birthday is today
        for (Contact contact : newContentPane.clist) {
            LocalDate dob = contact.getDob();
            if ((dob.getMonthValue() == LocalDate.now().getMonthValue()) &&  (dob.getDayOfMonth() == LocalDate.now().getDayOfMonth())) {
                String name = contact.getName();
                String age = ""+contact.getAge();
                JOptionPane.showMessageDialog(null, "Today is " + name + " " + age + "'s birthday.", "Birthday Reminder", JOptionPane.PLAIN_MESSAGE);
            }
            try {
                Thread.sleep(160);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        //Remove selected contact
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = newContentPane.table.getSelectedRow();
                if(selectedRow != -1) {
                    newContentPane.model.removeRow(selectedRow);
                    newContentPane.clist.remove(selectedRow);
                    try {
                        FileWriter writer = new FileWriter("Contacts.dat");
                        for (Contact c : newContentPane.clist) {
                            writer.write(c.toString());
                            writer.write("\n");
                        }
                        writer.close();
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                    };
                }
            }
        });

        //Open window for adding new contact
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EntryScreen(newContentPane);
            }
        });

        //Sort table by name
        nSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sort the contacts by DOB using a Comparator
                Collections.sort(newContentPane.clist, new Comparator<Contact>() {
                    @Override
                    public int compare(Contact c1, Contact c2) {
                        return c1.getFirstName().compareTo(c2.getFirstName());
                    }
                });
                newContentPane.model.setRowCount(0);
                newContentPane.showTable(newContentPane.clist);
            }
        });

        //Sort table by age
        aSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sort the contacts by DOB using a Comparator
                Collections.sort(newContentPane.clist, new Comparator<Contact>() {
                    @Override
                    public int compare(Contact c1, Contact c2) {
                        return c2.getAge()-(c1.getAge());
                    }
                });
                newContentPane.model.setRowCount(0);
                newContentPane.showTable(newContentPane.clist);
            }
        });

        //Sort table by date 
        dSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sort the contacts by DOB using a Comparator
                Collections.sort(newContentPane.clist, new Comparator<Contact>() {
                    @Override
                    public int compare(Contact c1, Contact c2) {
                        return c2.getDob().compareTo(c1.getDob());
                    }
                });
                newContentPane.model.setRowCount(0);
                newContentPane.showTable(newContentPane.clist);
            }
        });

        //Toggle through the table for searched text
        tf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            String searchText = tf.getText();
            searchTable(searchText);
            }
            /**
             * searchTable Mutator Method
             * @param searchText the text input when searching in the table
             */
            public void searchTable(String searchText) {
                for (int row = 0; row < newContentPane.table.getRowCount(); row++) {
                    for (int col = 0; col < newContentPane.table.getColumnCount(); col++) {
                        Object value = newContentPane.table.getValueAt(row, col);
                        if (value != null && value.toString().contains(searchText)) {
                            newContentPane.table.changeSelection(row, col, false, false);
                            return;
                        }
                    }
                }
            }
        });
    }
}