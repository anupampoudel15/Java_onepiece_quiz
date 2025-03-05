package onepiece;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

/**
 * Displays player reports and game history statistics for the admin.
 */
public class ReportManagement extends JFrame {
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private static final String URL = "jdbc:mysql://localhost:3306/onepiece";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Constructs the ReportManagement frame.
     * Retrieves and displays player reports.
     */
    public ReportManagement() {
        setTitle("Player Reports & Statistics");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Player Reports - All Player Data", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Set up table to display reports
        String[] columnNames = {"Player ID", "Name", "Level", "Score", "Timestamp"};
        tableModel = new DefaultTableModel(columnNames, 0);
        reportTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(reportTable);
        add(scrollPane, BorderLayout.CENTER);

        loadReports();

        JButton backButton = new JButton("Back to Admin Panel");
        backButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    /**
     * Loads player reports from the database and populates the table.
     */
    private void loadReports() {
        tableModel.setRowCount(0);
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT p.id, p.name, p.level, r.score, r.timestamp " +
                     "FROM players p LEFT JOIN reports r ON p.id = r.player_id " +
                     "ORDER BY r.timestamp DESC")) {
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("level"),
                        rs.getInt("score"),
                        rs.getTimestamp("timestamp")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method to launch the report management window.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReportManagement().setVisible(true));
    }
}
