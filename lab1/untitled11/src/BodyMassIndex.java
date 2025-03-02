/**
 * Class for calculating and interpreting a person's Body Mass Index (BMI).
 */
public class BodyMassIndex {
    // Constants for BMI threshold values
    private static final double UNDERWEIGHT_LIMIT = 18.5;
    private static final double NORMAL_LIMIT = 25.0;
    private static final double OVERWEIGHT_LIMIT = 30.0;

    private double weight; // Person's weight in kg
    private double height; // Person's height in meters
    private double bmiValue; // Calculated BMI value

    /**
     * Constructor.
     *
     * @param weight Person's weight in kilograms
     * @param height Person's height in meters
     * @throws IllegalArgumentException if the weight or height values are invalid
     */
    public BodyMassIndex(double weight, double height) {
        validateParameters(weight, height);
        this.weight = weight;
        this.height = height;
        calculateBMI();
    }

    /**
     * Validates the input parameters.
     *
     * @param weight Person's weight
     * @param height Person's height
     * @throws IllegalArgumentException if the weight or height values are invalid
     */
    private void validateParameters(double weight, double height) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be a positive number");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be a positive number");
        }
    }

    /**
     * Calculates the BMI value.
     */
    private void calculateBMI() {
        bmiValue = weight / (height * height);
    }

    /**
     * Returns the person's weight.
     *
     * @return Weight in kilograms
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets a new weight value.
     *
     * @param weight New weight in kilograms
     * @throws IllegalArgumentException if the weight value is invalid
     */
    public void setWeight(double weight) {
        validateParameters(weight, this.height);
        this.weight = weight;
        calculateBMI();
    }

    /**
     * Returns the person's height.
     *
     * @return Height in meters
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets a new height value.
     *
     * @param height New height in meters
     * @throws IllegalArgumentException if the height value is invalid
     */
    public void setHeight(double height) {
        validateParameters(this.weight, height);
        this.height = height;
        calculateBMI();
    }

    /**
     * Returns the calculated BMI value.
     *
     * @return BMI value
     */
    public double getBmiValue() {
        return bmiValue;
    }

    /**
     * Returns a textual interpretation of the BMI.
     *
     * @return Textual BMI interpretation
     */
    public String getResult() {
        if (bmiValue < UNDERWEIGHT_LIMIT) {
            return String.format("Underweight (BMI: %.2f)", bmiValue);
        } else if (bmiValue < NORMAL_LIMIT) {
            return String.format("Normal weight (BMI: %.2f)", bmiValue);
        } else if (bmiValue < OVERWEIGHT_LIMIT) {
            return String.format("Overweight! (BMI: %.2f)", bmiValue);
        } else {
            return String.format("Obesity (BMI: %.2f)", bmiValue);
        }
    }
}