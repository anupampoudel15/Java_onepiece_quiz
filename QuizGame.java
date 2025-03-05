package onepiece;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents the main Quiz Game window where players answer questions.
 */
public class QuizGame extends JFrame {
    private String playerName;
    private String playerLevel;
    private JLabel imageLabel, scoreLabel;
    private JButton option1Button, option2Button;
    private int score = 0, questionIndex = 0;
    private ArrayList<Question> questions;

    private static final String URL = "jdbc:mysql://localhost:3306/onepiece";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Constructs a new QuizGame instance.
     * @param playerName The name of the player.
     * @param playerLevel The difficulty level of the quiz.
     */
    public QuizGame(String playerName, String playerLevel) {
        this.playerName = playerName;
        this.playerLevel = playerLevel;
        setTitle("One Piece Quiz - " + playerLevel + " Level");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        imageLabel = new JLabel("", SwingConstants.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        option1Button = new JButton();
        option2Button = new JButton();
        buttonPanel.add(option1Button);
        buttonPanel.add(option2Button);
        add(buttonPanel, BorderLayout.SOUTH);

        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        add(scoreLabel, BorderLayout.NORTH);

        questions = loadQuestionsFromDB();
        if (!questions.isEmpty()) {
            displayQuestion();
        } else {
            JOptionPane.showMessageDialog(this, "No questions found for " + playerLevel + " level!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        option1Button.addActionListener(e -> checkAnswer(option1Button.getText()));
        option2Button.addActionListener(e -> checkAnswer(option2Button.getText()));

        setLocationRelativeTo(null);
    }

    /**
     * Loads questions from the database for the specified difficulty level.
     * @return A list of questions.
     */
    private ArrayList<Question> loadQuestionsFromDB() {
        ArrayList<Question> questionList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT image_path, correct_answer, option_1, option_2 " +
                     "FROM questions WHERE difficulty = ? " +
                     "ORDER BY RAND() LIMIT 10")) {
            stmt.setString(1, playerLevel);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                questionList.add(new Question(
                    rs.getString("image_path"),
                    rs.getString("correct_answer"),
                    rs.getString("option_1"),
                    rs.getString("option_2")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionList;
    }

    /**
     * Displays the current question and answer choices.
     */
    private void displayQuestion() {
        if (questionIndex < questions.size()) {
            Question q = questions.get(questionIndex);
            imageLabel.setIcon(new ImageIcon(q.getImagePath()));
            ArrayList<String> options = new ArrayList<>();
            options.add(q.getOption1());
            options.add(q.getOption2());
            Collections.shuffle(options);
            option1Button.setText(options.get(0));
            option2Button.setText(options.get(1));
        } else {
            updatePlayerScore();
            showCompletionOptions();
        }
    }

    /**
     * Checks if the selected answer is correct and updates the score.
     * @param selectedAnswer The answer chosen by the player.
     */
    private void checkAnswer(String selectedAnswer) {
        if (questions.get(questionIndex).getCorrectAnswer().equals(selectedAnswer)) {
            score++;
            scoreLabel.setText("Score: " + score);
        }
        questionIndex++;
        displayQuestion();
    }

    /**
     * Updates the player's highest score in the database.
     */
    private void updatePlayerScore() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            int playerId = -1;
            String playerQuery = "SELECT id FROM players WHERE name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(playerQuery)) {
                stmt.setString(1, playerName);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    playerId = rs.getInt("id");
                }
            }

            if (playerId != -1) {
                String insertHistoryQuery = "INSERT INTO game_history (player_id, level, score) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertHistoryQuery)) {
                    stmt.setInt(1, playerId);
                    stmt.setString(2, playerLevel);
                    stmt.setInt(3, score);
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays completion options after finishing the quiz.
     */
    private void showCompletionOptions() {
        SwingUtilities.invokeLater(() -> {
            int choice = JOptionPane.showOptionDialog(this, "Quiz Over! Your final score is: " + score + "\nWhat would you like to do next?", "Quiz Completed",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new String[]{"Return to Home", "Quit"}, "Return to Home");
            
            if (choice == 0) {
                new HomePage(playerName).setVisible(true);
            } else {
                System.exit(0);
            }
            dispose();
        });
    }
}
