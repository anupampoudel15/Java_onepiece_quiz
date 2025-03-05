package onepiece;

import javax.swing.*;
import java.awt.*;

/**
 * Provides an authentication system for administrators.
 * Admins can log in using predefined credentials.
 */
public class AdminLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123"; // Change this for security

    /**
     * Constructs the AdminLogin window.
     */
    public AdminLogin() {
        setTitle("Admin Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        add(loginButton);

        JButton cancelButton = new JButton("Cancel");
        add(cancelButton);

        // New "Back to User Login" Button
        JButton backToUserLoginButton = new JButton("Back to User Login");
        add(backToUserLoginButton);

        loginButton.addActionListener(e -> validateLogin());
        cancelButton.addActionListener(e -> dispose());

        // Action to go back to the User Login page
        backToUserLoginButton.addActionListener(e -> {
            new UserLogin().setVisible(true);
            dispose();
        });

        setLocationRelativeTo(null);
    }

    /**
     * Validates admin login credentials and grants access to the Admin Panel.
     */
    private void validateLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            new AdminPanel().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Main method to launch the Admin Login window.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminLogin().setVisible(true));
    }
}
