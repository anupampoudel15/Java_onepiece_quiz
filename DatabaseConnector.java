package onepiece;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Handles database connection and inserts quiz questions into the database.
 */
public class DatabaseConnector {
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/onepiece"; // Change 'onepiece' if needed
    private static final String USER = "root"; // Change to your MySQL username
    private static final String PASSWORD = ""; // Change to your MySQL password
/**
 * Main method to establish a database connection and insert quiz questions.
 * @param args Command-line arguments.
 */
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to MySQL successfully!");

            // Insert One Piece characters into the database
            insertQuestions(conn);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(" Connection failed!");
        }
    }
/**
 * Inserts predefined quiz questions into the database.
 * @param conn The database connection object.
 */
    private static void insertQuestions(Connection conn) {
        String sql = "INSERT INTO questions (image_path, correct_answer, option_1, option_2, difficulty) VALUES (?, ?, ?, ?, ?)";

        String[][] data = {
            // Beginner Level
            {"images/Dracule_Mihawk.jpg", "Dracule Mihawk", "Dracule Mihawk", "Smoker", "Beginner"},
            {"images/Smoker.jpg", "Smoker", "Alvida", "Smoker", "Beginner"},
            {"images/Alvida.jpg", "Alvida", "Koby", "Alvida", "Beginner"},
            {"images/Koby.jpg", "Koby", "Helmeppo", "Koby", "Beginner"},
            {"images/Helmeppo.jpg", "Helmeppo", "Arlong", "Helmeppo", "Beginner"},
            {"images/Arlong.jpg", "Arlong", "Captain Kuro", "Arlong", "Beginner"},
            {"images/Captain_Kuro.jpg", "Captain Kuro", "Nojiko", "Captain Kuro", "Beginner"},
            {"images/Nojiko.jpg", "Nojiko", "Dadan", "Nojiko", "Beginner"},
            {"images/Dadan.jpg", "Dadan", "roger", "Dadan", "Beginner"},
            {"images/roger.jpg", "roger", "Garp", "roger", "Beginner"},
            {"images/Garp.jpg", "Garp", "Dracule Mihawk", "Garp", "Beginner"},
            
            // Intermediate Level
            {"images/Donquixote_Doflamingo.jpg", "Donquixote Doflamingo", "Boa Hancock", "Donquixote Doflamingo", "Intermediate"},
            {"images/Boa_Hancock.jpg", "Boa Hancock", "Trafalgar D. Water Law", "Boa Hancock", "Intermediate"},
            {"images/Trafalgar_D_Water_Law.jpg", "Trafalgar D. Water Law", "Eustass Kid", "Trafalgar D. Water Law", "Intermediate"},
            {"images/Eustass_Kid.jpg", "Eustass Kid", "Marshall D. Teach (Blackbeard)", "Eustass Kid", "Intermediate"},
            {"images/Marshall_D_Teach_Blackbeard.jpg", "Marshall D. Teach (Blackbeard)", "Jinbe", "Marshall D. Teach (Blackbeard)", "Intermediate"},
            {"images/Jinbe.jpg", "Jinbe", "Silvers Rayleigh", "Jinbe", "Intermediate"},
            {"images/Silvers_Rayleigh.jpg", "Silvers Rayleigh", "Perona", "Silvers Rayleigh", "Intermediate"},
            {"images/Perona.jpg", "Perona", "Rob Lucci", "Perona", "Intermediate"},
            {"images/Rob_Lucci.jpg", "Rob Lucci", "Sabo", "Rob Lucci", "Intermediate"},
            {"images/Sabo.jpg", "Sabo", "Ivankov", "Sabo", "Intermediate"},
            {"images/Ivankov.jpg", "Ivankov", "Crocodile", "Ivankov", "Intermediate"},
            {"images/Crocodile.jpg", "Crocodile", "Enel", "Crocodile", "Intermediate"},
            {"images/Enel.jpg", "Enel", "Kuzan (Aokiji)", "Enel", "Intermediate"},
            {"images/Kuzan_Aokiji.jpg", "Kuzan (Aokiji)", "Sakazuki (Akainu)", "Kuzan (Aokiji)", "Intermediate"},
            {"images/Sakazuki_Akainu.jpg", "Sakazuki (Akainu)", "Donquixote Doflamingo", "Sakazuki (Akainu)", "Intermediate"},
            
            // Advanced Level
            {"images/Kawamatsu.jpg", "Kawamatsu", "vegapunk", "Kawamatsu", "Advanced"},
            {"images/vegapunk.jpg", "vegapunk", "Kanjuro", "vegapunk", "Advanced"},
            {"images/Kanjuro.jpg", "Kanjuro", "Raizo", "Kanjuro", "Advanced"},
            {"images/Raizo.jpg", "Raizo", "katakuri", "Raizo", "Advanced"},
            {"images/katakuri.jpg", "katakuri", "otama", "katakuri", "Advanced"},
            {"images/otama.jpg", "otama", "Orochi", "otama", "Advanced"},
            {"images/Orochi.jpg", "Orochi", "Kaido", "Orochi", "Advanced"},
            {"images/Kaido.jpg", "Kaido", "King", "Kaido", "Advanced"},
            {"images/King.jpg", "King", "Queen", "King", "Advanced"},
            {"images/Queen.jpg", "Queen", "Jack", "Queen", "Advanced"},
            {"images/Jack.jpg", "Jack", "Big Mom", "Jack", "Advanced"},
            {"images/Big_Mom.jpg", "Big Mom", "Kaido", "Big Mom", "Advanced"}
        };

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (String[] question : data) {
                // Print difficulty value before insertion
                System.out.println("Inserting: " + question[1] + " | Difficulty: '" + question[4] + "'");

                stmt.setString(1, question[0]); // image_path
                stmt.setString(2, question[1]); // correct_answer
                stmt.setString(3, question[2]); // option_1
                stmt.setString(4, question[3]); // option_2
                stmt.setString(5, question[4].trim()); // Trim extra spaces just in case
                stmt.executeUpdate();
            }
            System.out.println("Questions inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to insert questions!");
        }
    }
}
