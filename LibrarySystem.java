import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.*;
import javax.swing.border.*;

public class LibrarySystem extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private JTextField nameField, rollField, bookTitleField, issueDateField, returnDateField;
    private JTextArea remarksArea;
    private JComboBox<String> categoryBox;
    private JRadioButton newEdition, oldEdition;
    private ButtonGroup editionGroup;
    private JButton issueBtn, resetBtn, exitBtn;

    static class EmptyFieldException extends Exception {
        public EmptyFieldException(String message) { super(message); }
    }

    static class InvalidRollNumberException extends Exception {
        public InvalidRollNumberException(String message) { super(message); }
    }

    static class InvalidDateException extends Exception {
        public InvalidDateException(String message) { super(message); }
    }

    static class NullSelectionException extends Exception {
        public NullSelectionException(String message) { super(message); }
    }
    
    static class BookUnavailableException extends Exception {
        public BookUnavailableException(String message) { super(message); }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LibrarySystem frame = new LibrarySystem();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public LibrarySystem() {
        setTitle("Library Book Issue System - UCP");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(150, 80, 750, 650);
        setResizable(false);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(245, 245, 245));
        setContentPane(contentPane);

        buildHeader();
        buildForm();
        buildButtons();
    }

    private void buildHeader() {
        JPanel header = new JPanel(null);
        header.setPreferredSize(new Dimension(750, 60));
        header.setBackground(new Color(30, 60, 100));
        
        JLabel title = new JLabel("LIBRARY BOOK ISSUE SYSTEM");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(0, 0, 750, 60);
        
        header.add(title);
        contentPane.add(header, BorderLayout.NORTH);
    }

    private void buildForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color labelColor = new Color(50, 50, 50);

        int row = 0;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        JLabel nameLabel = new JLabel("Student Name :");
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(labelColor);
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        nameField = new JTextField();
        nameField.setFont(inputFont);
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(nameField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel rollLabel = new JLabel("Roll Number :");
        rollLabel.setFont(labelFont);
        rollLabel.setForeground(labelColor);
        formPanel.add(rollLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        rollField = new JTextField();
        rollField.setFont(inputFont);
        rollField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(rollField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel bookLabel = new JLabel("Book Title :");
        bookLabel.setFont(labelFont);
        bookLabel.setForeground(labelColor);
        formPanel.add(bookLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        bookTitleField = new JTextField();
        bookTitleField.setFont(inputFont);
        bookTitleField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(bookTitleField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel catLabel = new JLabel("Book Category :");
        catLabel.setFont(labelFont);
        catLabel.setForeground(labelColor);
        formPanel.add(catLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        String[] categories = { "-- Select Category --", "Programming", "AI", "Databases", "Networking" };
        categoryBox = new JComboBox<>(categories);
        categoryBox.setFont(inputFont);
        formPanel.add(categoryBox, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel typeLabel = new JLabel("Book Type :");
        typeLabel.setFont(labelFont);
        typeLabel.setForeground(labelColor);
        formPanel.add(typeLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        radioPanel.setBackground(Color.WHITE);
        newEdition = new JRadioButton("New Edition");
        oldEdition = new JRadioButton("Old Edition");
        newEdition.setFont(inputFont);
        oldEdition.setFont(inputFont);
        editionGroup = new ButtonGroup();
        editionGroup.add(newEdition);
        editionGroup.add(oldEdition);
        radioPanel.add(newEdition);
        radioPanel.add(oldEdition);
        formPanel.add(radioPanel, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel issueDateLabel = new JLabel("Issue Date (YYYY-MM-DD) :");
        issueDateLabel.setFont(labelFont);
        issueDateLabel.setForeground(labelColor);
        formPanel.add(issueDateLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        issueDateField = new JTextField();
        issueDateField.setFont(inputFont);
        issueDateField.setText(LocalDate.now().toString());
        issueDateField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(issueDateField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel returnDateLabel = new JLabel("Return Date (YYYY-MM-DD) :");
        returnDateLabel.setFont(labelFont);
        returnDateLabel.setForeground(labelColor);
        formPanel.add(returnDateLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        returnDateField = new JTextField();
        returnDateField.setFont(inputFont);
        returnDateField.setText(LocalDate.now().plusDays(14).toString());
        returnDateField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(returnDateField, gbc);
        row++;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel remarksLabel = new JLabel("Remarks :");
        remarksLabel.setFont(labelFont);
        remarksLabel.setForeground(labelColor);
        formPanel.add(remarksLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        remarksArea = new JTextArea(3, 20);
        remarksArea.setFont(inputFont);
        remarksArea.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(remarksArea);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        formPanel.add(scroll, gbc);

        contentPane.add(formPanel, BorderLayout.CENTER);
    }

    private void buildButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        issueBtn = createButton("Issue Book", new Color(30, 100, 180));
        resetBtn = createButton("Reset", new Color(100, 100, 100));
        exitBtn = createButton("Exit", new Color(180, 40, 40));

        buttonPanel.add(resetBtn);
        buttonPanel.add(issueBtn);
        buttonPanel.add(exitBtn);

        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        issueBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        exitBtn.addActionListener(this);
    }

    private JButton createButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(120, 35));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == issueBtn) {
            performIssueLogic();
        } else if (e.getSource() == resetBtn) {
            clearFields();
        } else if (e.getSource() == exitBtn) {
            System.exit(0);
        }
    }

    private void performIssueLogic() {
        try {
            String name = nameField.getText().trim();
            String roll = rollField.getText().trim();
            String bookTitle = bookTitleField.getText().trim();
            String category = (String) categoryBox.getSelectedItem();
            String issueDateStr = issueDateField.getText().trim();
            String returnDateStr = returnDateField.getText().trim();
            
            String edition = "";
            if (newEdition.isSelected()) edition = "New Edition";
            else if (oldEdition.isSelected()) edition = "Old Edition";

            validateInputs(name, roll, bookTitle, category, edition, issueDateStr, returnDateStr);

            if (bookTitle.toLowerCase().contains("restricted")) {
                throw new BookUnavailableException("This book is restricted and cannot be issued.");
            }

            String msg = String.format(
                "Book Issued Successfully!\n\nStudent: %s\nRoll: %s\nBook: %s\nCategory: %s\nType: %s\nIssue: %s\nReturn: %s",
                name, roll, bookTitle, category, edition, issueDateStr, returnDateStr
            );
            JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();

        } catch (EmptyFieldException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Missing Information", JOptionPane.WARNING_MESSAGE);
        } catch (InvalidRollNumberException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid Roll Number", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidDateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Date Error", JOptionPane.ERROR_MESSAGE);
        } catch (NullSelectionException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Selection Required", JOptionPane.WARNING_MESSAGE);
        } catch (BookUnavailableException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Unavailable", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Internal Error: Numeric format invalid.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            System.out.println("Operation Completed: Issue Attempt Processed.");
        }
    }

    private void validateInputs(String name, String roll, String bookTitle, 
                                String category, String edition, 
                                String issueDateStr, String returnDateStr) 
            throws EmptyFieldException, InvalidRollNumberException, NullSelectionException, InvalidDateException {
        
        if (name.isEmpty() || roll.isEmpty() || bookTitle.isEmpty() || issueDateStr.isEmpty() || returnDateStr.isEmpty()) {
            throw new EmptyFieldException("All text fields (Name, Roll, Title, Dates) are required.");
        }

        if (category == null || category.startsWith("--")) {
            throw new NullSelectionException("Please select a valid Book Category.");
        }

        if (edition.isEmpty()) {
            throw new NullSelectionException("Please select a Book Type (New/Old).");
        }

        if (!roll.matches("\\d+")) {
            throw new InvalidRollNumberException("Roll Number must contain only digits (e.g., 12345).");
        }

        try {
            LocalDate issueDate = LocalDate.parse(issueDateStr);
            LocalDate returnDate = LocalDate.parse(returnDateStr);

            if (returnDate.isBefore(issueDate)) {
                throw new InvalidDateException("Return Date cannot be earlier than Issue Date.");
            }
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Invalid Date Format. Please use YYYY-MM-DD.");
        }
    }

    private void clearFields() {
        nameField.setText("");
        rollField.setText("");
        bookTitleField.setText("");
        categoryBox.setSelectedIndex(0);
        editionGroup.clearSelection();
        issueDateField.setText(LocalDate.now().toString());
        returnDateField.setText(LocalDate.now().plusDays(14).toString());
        remarksArea.setText("");
        nameField.requestFocus();
    }
}