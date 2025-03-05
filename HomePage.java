package onepiece;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the home page of the One Piece Quiz Game.
 * Players can select difficulty levels, start a game, or view high scores.
 */
public class HomePage extends JFrame {
    private JButton beginnerButton, intermediateButton, advancedButton;
    private JButton startButton, highScoreButton, exitButton;
    private String selectedDifficulty = "Intermediate";
    private String playerName;

    /**
     * Constructs the HomePage window for a given player.
     * @param playerName The name of the player.
     */
    public HomePage(String playerName) {
        this.playerName = playerName;
        setTitle("One Piece Quiz Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));

        JLabel titleLabel = new JLabel("One Piece Quiz Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new GridLayout(1, 1));
        JLabel userLabel = new JLabel("Player: " + playerName, SwingConstants.CENTER);
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userPanel.add(userLabel);
        add(userPanel, BorderLayout.WEST);
        
        JPanel levelPanel = new JPanel();
        levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.Y_AXIS));
        
        JLabel chooseLevelLabel = new JLabel("Choose Level:", SwingConstants.CENTER);
        chooseLevelLabel.setFont(new Font("Arial", Font.BOLD, 16));
        chooseLevelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelPanel.add(chooseLevelLabel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        beginnerButton = new JButton("Beginner");
        intermediateButton = new JButton("Intermediate");
        advancedButton = new JButton("Advanced");
        
        highlightSelected(intermediateButton); // Preselected Level
        
        beginnerButton.addActionListener(e -> selectLevel(beginnerButton));
        intermediateButton.addActionListener(e -> selectLevel(intermediateButton));
        advancedButton.addActionListener(e -> selectLevel(advancedButton));
        
        buttonPanel.add(beginnerButton);
        buttonPanel.add(intermediateButton);
        buttonPanel.add(advancedButton);
        levelPanel.add(buttonPanel);
        
        add(levelPanel, BorderLayout.CENTER);

        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        startButton = new JButton("Start Game");
        highScoreButton = new JButton("View High Scores");
        exitButton = new JButton("Exit");

        startButton.addActionListener(e -> startGame());
        highScoreButton.addActionListener(e -> new HighScoresPage(playerName).setVisible(true));
        exitButton.addActionListener(e -> System.exit(0));

        actionButtonPanel.add(startButton);
        actionButtonPanel.add(highScoreButton);
        actionButtonPanel.add(exitButton);
        add(actionButtonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    /**
     * Sets the selected difficulty level and updates button appearance.
     * @param selectedButton The button representing the selected difficulty level.
     */
    private void selectLevel(JButton selectedButton) {
        selectedDifficulty = selectedButton.getText();
        highlightSelected(selectedButton);
    }

    /**
     * Highlights the selected difficulty button.
     * @param selectedButton The button to be highlighted.
     */
    private void highlightSelected(JButton selectedButton) {
        beginnerButton.setBackground(null);
        intermediateButton.setBackground(null);
        advancedButton.setBackground(null);
        beginnerButton.setOpaque(true);
        intermediateButton.setOpaque(true);
        advancedButton.setOpaque(true);

        selectedButton.setBackground(new Color(144, 238, 144)); // Light Green Highlight
    }

    /**
     * Starts the quiz game with the selected difficulty level.
     */
    private void startGame() {
        new QuizGame(playerName, selectedDifficulty).setVisible(true);
        dispose();
    }

    /**
     * Main method to launch the HomePage.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomePage("Guest").setVisible(true));
    }
}
