package onepiece;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

/**
 * Handles user login and registration for the quiz game.
 */
public class UserLogin extends JFrame {
    private JTextField nameField;
    private static final String URL = "jdbc:mysql://localhost:3306/onepiece";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Constructs the UserLogin frame, allowing users to enter their name.
     */
    public UserLogin() {
        setTitle("Player Registration");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Enter Your Name:"));
        nameField = new JTextField();
        add(nameField);
        
        JButton loginButton = new JButton("Enter Game");
        add(loginButton);

        JButton adminButton = new JButton("Admin Panel");
        add(adminButton);

        loginButton.addActionListener(e -> checkUser());
        adminButton.addActionListener(e -> {
            new AdminLogin().setVisible(true);
            dispose();
        });

        setLocationRelativeTo(null);
    }

    /**
     * Checks if the user exists in the database; if not, registers the user.
     */
    private void checkUser() {
        String playerName = nameField.getText().trim();

        if (playerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Check if the player exists
            String query = "SELECT id FROM players WHERE name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, playerName);
                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) {
                    // If player does not exist, insert them
                    String insertQuery = "INSERT INTO players (name, score) VALUES (?, 0)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                        insertStmt.setString(1, playerName);
                        insertStmt.executeUpdate();
                    }
                }
            }

            // Navigate to HomePage with player name
            new HomePage(playerName).setVisible(true);
            dispose();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Main method to start the login page.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserLogin().setVisible(true));
    }
}
