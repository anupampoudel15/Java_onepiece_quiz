package onepiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

/**
 * A dialog window for adding new quiz questions to the database.
 */
public class AddQuestionDialog extends JDialog {
    private JTextField imagePathField, correctAnswerField, option1Field, option2Field;
    private JComboBox<String> difficultyBox;
    private static final String URL = "jdbc:mysql://localhost:3306/onepiece";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private QuizManagement parent;

    /**
     * Constructs an AddQuestionDialog instance.
     * @param parent The parent QuizManagement window.
     */
    public AddQuestionDialog(QuizManagement parent) {
        super(parent, "Add New Question", true);
        this.parent = parent;
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

        JButton addButton = new JButton("Add Question");
        addButton.addActionListener(this::addQuestion);
        add(addButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setLocationRelativeTo(parent);
    }

    /**
     * Adds a new question to the database.
     * @param e The event triggered by clicking the add button.
     */
    private void addQuestion(ActionEvent e) {
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
                     "INSERT INTO questions (image_path, correct_answer, option_1, option_2, difficulty) VALUES (?, ?, ?, ?, ?)")
        ) {
            stmt.setString(1, imagePath);
            stmt.setString(2, correctAnswer);
            stmt.setString(3, option1);
            stmt.setString(4, option2);
            stmt.setString(5, difficulty);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Question added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            parent.refreshQuestions();
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding question!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
