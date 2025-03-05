package onepiece;

/**
 * Represents a quiz question with an image and multiple-choice options.
 */
public class Question {
    private final String imagePath;
    private final String correctAnswer;
    private final String option1;
    private final String option2;

    /**
     * Constructs a Question object.
     * @param imagePath The path to the question image.
     * @param correctAnswer The correct answer for the question.
     * @param option1 The first answer option.
     * @param option2 The second answer option.
     */
    public Question(String imagePath, String correctAnswer, String option1, String option2) {
        this.imagePath = imagePath;
        this.correctAnswer = correctAnswer;
        this.option1 = option1;
        this.option2 = option2;
    }

    /**
     * Gets the image path of the question.
     * @return The image path as a string.
     */
    public String getImagePath() { return imagePath; }

    /**
     * Gets the correct answer.
     * @return The correct answer as a string.
     */
    public String getCorrectAnswer() { return correctAnswer; }

    /**
     * Gets the first answer option.
     * @return The first answer option as a string.
     */
    public String getOption1() { return option1; }

    /**
     * Gets the second answer option.
     * @return The second answer option as a string.
     */
    public String getOption2() { return option2; }
}
