package onepiece;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

/**
 * The HighScoresPage class displays a leaderboard with the top 2 players per difficulty level
 * and shows the current player's top scores for each difficulty level.
 */
public class HighScoresPage extends JFrame {
    private static final String URL = "jdbc:mysql://localhost:3306/onepiece"; // Database URL
    private static final String USER = "root"; // Database username
    private static final String PASSWORD = ""; // Database password
    private String playerName; // Player's name
    private JButton userTopScoreButton; // Button displaying the user's top score

    /**
     * Constructor to initialize the high scores page for a specific player.
     * 
     * @param playerName The name of the player to display high scores for.
     */
    public HighScoresPage(String playerName) {
        this.playerName = playerName;
        setTitle("High Scores");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title label
        JLabel titleLabel = new JLabel("Leaderboard - Top 2 Players Per Level", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Leaderboard Table
        JPanel leaderboardPanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Name", "Level", "Score"};
        Object[][] data = loadTopPlayers();
        JTable leaderboardTable = new JTable(data, columnNames);
        JScrollPane leaderboardScrollPane = new JScrollPane(leaderboardTable);
        leaderboardPanel.add(leaderboardScrollPane, BorderLayout.CENTER);
        add(leaderboardPanel, BorderLayout.CENTER);

        // User High Scores Panel
        JPanel userScorePanel = new JPanel(new BorderLayout());
        JLabel userScoreTitle = new JLabel("Your High Scores", SwingConstants.CENTER);
        userScoreTitle.setFont(new Font("Arial", Font.BOLD, 18));
        userScorePanel.add(userScoreTitle, BorderLayout.NORTH);

        String[] userColumnNames = {"Level", "Your Best Score"};
        Object[][] userData = loadUserTopScores();
        JTable userScoresTable = new JTable(userData, userColumnNames);
        JScrollPane userScrollPane = new JScrollPane(userScoresTable);
        userScorePanel.add(userScrollPane, BorderLayout.CENTER);

        add(userScorePanel, BorderLayout.SOUTH);

        // User's Top Score Button
        userTopScoreButton = new JButton(getUserTopScoreText(userData));
        userTopScoreButton.setFont(new Font("Arial", Font.BOLD, 16));
        userTopScoreButton.setPreferredSize(new Dimension(400, 50));
        userTopScoreButton.setEnabled(false); // Button is just for display

        // Back Button
        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(e -> {
            new HomePage(playerName).setVisible(true);
            dispose();
        });

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(userTopScoreButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    /**
     * Loads the top 2 players for each difficulty level from the database.
     * 
     * @return A 2D array containing the top 2 players' names, levels, and scores.
     */
    private Object[][] loadTopPlayers() {
        Object[][] data = new Object[6][3]; // 2 players per level, 3 levels total

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(
                     "(SELECT p.name, gh.level, MAX(gh.score) AS score " +
                     " FROM game_history gh " +
                     " JOIN players p ON gh.player_id = p.id " +
                     " WHERE gh.level = 'Beginner' " +
                     " GROUP BY p.name, gh.level " +
                     " ORDER BY score DESC LIMIT 2) " +
                     "UNION " +
                     "(SELECT p.name, gh.level, MAX(gh.score) AS score " +
                     " FROM game_history gh " +
                     " JOIN players p ON gh.player_id = p.id " +
                     " WHERE gh.level = 'Intermediate' " +
                     " GROUP BY p.name, gh.level " +
                     " ORDER BY score DESC LIMIT 2) " +
                     "UNION " +
                     "(SELECT p.name, gh.level, MAX(gh.score) AS score " +
                     " FROM game_history gh " +
                     " JOIN players p ON gh.player_id = p.id " +
                     " WHERE gh.level = 'Advanced' " +
                     " GROUP BY p.name, gh.level " +
                     " ORDER BY score DESC LIMIT 2)")
        ) {
            ResultSet rs = stmt.executeQuery();
            int row = 0;
            while (rs.next() && row < 6) {
                data[row][0] = rs.getString("name");
                data[row][1] = rs.getString("level");
                data[row][2] = rs.getInt("score");
                row++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Loads the user's top scores for each difficulty level from the database.
     * 
     * @return A 2D array containing the user's highest scores for each level.
     */
    private Object[][] loadUserTopScores() {
        Object[][] data = {{"Beginner", 0}, {"Intermediate", 0}, {"Advanced", 0}}; // Default values

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT level, MAX(score) AS high_score FROM game_history " +
                     "WHERE player_id = (SELECT id FROM players WHERE name = ?) " +
                     "GROUP BY level ORDER BY FIELD(level, 'Beginner', 'Intermediate', 'Advanced')")
        ) {
            stmt.setString(1, playerName);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String level = rs.getString("level");
                int highScore = rs.getInt("high_score");
                
                for (int i = 0; i < data.length; i++) {
                    if (data[i][0].equals(level)) {
                        data[i][1] = highScore;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Saves the player's score for a specific difficulty level in the database.
     * 
     * @param level The difficulty level of the game.
     * @param score The score achieved by the player.
     */
    private void savePlayerScore(String level, int score) {
        String insertQuery = "INSERT INTO game_history (player_id, level, score) " +
                             "VALUES ((SELECT id FROM players WHERE name = ?), ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setString(1, playerName);
            stmt.setString(2, level);
            stmt.setInt(3, score);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a string to display the user's top scores across all levels.
     * 
     * @param userData The 2D array containing the user's highest scores for each level.
     * @return A string representing the user's top scores for each level.
     */
    private String getUserTopScoreText(Object[][] userData) {
        int beginnerScore = (int) userData[0][1];
        int intermediateScore = (int) userData[1][1];
        int advancedScore = (int) userData[2][1];

        return "Your Top Score: Beginner - " + beginnerScore + 
               ", Intermediate - " + intermediateScore + 
               ", Advanced - " + advancedScore;
    }

}
