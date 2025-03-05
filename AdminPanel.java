package onepiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * AdminPanel provides an interface for administrators to manage the quiz game.
 * Admins can manage quiz questions, view player reports, and navigate to other admin features.
 */
public class AdminPanel extends JFrame {
    /**
     * Constructs the AdminPanel with buttons for managing questions, viewing reports, and logging out.
     */
    public AdminPanel() {
        setTitle("Admin Panel");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        JLabel titleLabel = new JLabel("Admin Panel - Quiz Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel);

        JButton manageQuestionsButton = new JButton("Manage Questions");
        JButton viewReportsButton = new JButton("View Player Reports");
        JButton logoutButton = new JButton("Logout");
        JButton backToUserLoginButton = new JButton("Back to User Login");

        manageQuestionsButton.addActionListener((ActionEvent e) -> new QuizManagement().setVisible(true));
        viewReportsButton.addActionListener((ActionEvent e) -> new ReportManagement().setVisible(true));
        logoutButton.addActionListener((ActionEvent e) -> dispose());

        // Navigate back to User Login
        backToUserLoginButton.addActionListener(e -> {
            new UserLogin().setVisible(true);
            dispose();
        });

        add(manageQuestionsButton);
        add(viewReportsButton);
        add(logoutButton);
        add(backToUserLoginButton);

        setLocationRelativeTo(null);
    }

    /**
     * Main method to launch the Admin Panel.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminPanel().setVisible(true));
    }
}
