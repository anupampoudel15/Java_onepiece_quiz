package onepiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

/**
 * A dialog window for updating existing quiz questions in the database.
 */
public class UpdateQuestionDialog extends JDialog {
    private JTextField imagePathField, correctAnswerField, option1Field, option2Field;
    private JComboBox<String> difficultyBox;
    private static final String URL = "jdbc:mysql://localhost:3306/onepiece";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private QuizManagement parent;
    private int questionId;

    /**
     * Constructs an UpdateQuestionDialog instance.
     * @param parent The parent QuizManagement window.
     * @param questionId The ID of the question to update.
     */
    public UpdateQuestionDialog(QuizManagement parent, int questionId) {
        super(parent, "Update Question", true);
        this.parent = parent;
        this.questionId = questionId;
        setSize(400, 300);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Image Path:"));
        imagePathField = new JTextField();
        add(imagePathField);

        add(new JLabel("Correct Answer:"));
        correctAnswerField = new JTextField();
        add(correctAnswerField);

        add(new JLabel("Option 1:"));
        option1Field = new JTextField();
        add(option1Field);

        add(new JLabel("Option 2:"));
        option2Field = new JTextField();
        add(option2Field);

        add(new JLabel("Difficulty:"));
        difficultyBox = new JComboBox<>(new String[]{"Beginner", "Intermediate", "Advanced"});
        add(difficultyBox);

        loadQuestionDetails();

        JButton updateButton = new JButton("Update Question");
        updateButton.addActionListener(this::updateQuestion);
        add(updateButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setLocationRelativeTo(parent);
    }

    /**
     * Loads question details from the database and fills in the fields.
     */
    private void loadQuestionDetails() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM questions WHERE id = ?")) {
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                imagePathField.setText(rs.getString("image_path"));
                correctAnswerField.setText(rs.getString("correct_answer"));
                option1Field.setText(rs.getString("option_1"));
                option2Field.setText(rs.getString("option_2"));
                difficultyBox.setSelectedItem(rs.getString("difficulty"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the question details in the database based on user input.
     * @param e The event triggered by clicking the update button.
     */
    private void updateQuestion(ActionEvent e) {
        String imagePath = imagePathField.getText().trim();
        String correctAnswer = correctAnswerField.getText().trim();
        String option1 = option1Field.getText().trim();
        String option2 = option2Field.getText().trim();
        String difficulty = (String) difficultyBox.getSelectedItem();

        if (imagePath.isEmpty() || correctAnswer.isEmpty() || option1.isEmpty() || option2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE questions SET image_path = ?, correct_answer = ?, option_1 = ?, option_2 = ?, difficulty = ? WHERE id = ?")) {
            stmt.setString(1, imagePath);
            stmt.setString(2, correctAnswer);
            stmt.setString(3, option1);
            stmt.setString(4, option2);
            stmt.setString(5, difficulty);
            stmt.setInt(6, questionId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Question updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            parent.refreshQuestions();
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating question!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
