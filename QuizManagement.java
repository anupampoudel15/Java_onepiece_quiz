package onepiece;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

/**
 * Provides a graphical interface for managing quiz questions.
 * Allows adding, updating, and deleting quiz questions from the database.
 */
public class QuizManagement extends JFrame {
    private JTable questionTable;
    private DefaultTableModel tableModel;
    private static final String URL = "jdbc:mysql://localhost:3306/onepiece";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Constructs the QuizManagement interface and loads quiz questions.
     */
    public QuizManagement() {
        setTitle("Quiz Management");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Manage Quiz Questions", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        // Set up table for displaying quiz questions
        String[] columnNames = {"ID", "Image Path", "Correct Answer", "Option 1", "Option 2", "Difficulty"};
        tableModel = new DefaultTableModel(columnNames, 0);
        questionTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(questionTable);
        add(scrollPane, BorderLayout.CENTER);

        refreshQuestions();

        // Buttons for quiz management
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Question");
        JButton updateButton = new JButton("Update Question");
        JButton deleteButton = new JButton("Delete Question");
        JButton backButton = new JButton("Back to Admin Panel");

        addButton.addActionListener(e -> new AddQuestionDialog(this).setVisible(true));
        updateButton.addActionListener(e -> updateQuestion());
        deleteButton.addActionListener(e -> deleteQuestion());
        backButton.addActionListener(e -> dispose());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    /**
     * Retrieves quiz questions from the database and populates the table.
     */
    public void refreshQuestions() {
        tableModel.setRowCount(0);
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM questions ORDER BY id ASC")) {
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("image_path"),
                        rs.getString("correct_answer"),
                        rs.getString("option_1"),
                        rs.getString("option_2"),
                        rs.getString("difficulty")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to update the selected quiz question.
     */
    private void updateQuestion() {
        int selectedRow = questionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a question to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int questionId = (int) tableModel.getValueAt(selectedRow, 0);
        new UpdateQuestionDialog(this, questionId).setVisible(true);
    }

    /**
     * Deletes the selected quiz question from the database.
     */
    private void deleteQuestion() {
        int selectedRow = questionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a question to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int questionId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this question?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM questions WHERE id = ?")) {
                stmt.setInt(1, questionId);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Question deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshQuestions();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting question.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Main method to launch the quiz management panel.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizManagement().setVisible(true));
    }
}
