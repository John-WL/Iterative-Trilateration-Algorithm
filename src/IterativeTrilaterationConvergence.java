// creator: John-William Lebel
// date of creation: 2020-11-07

// description: basic trilateration algorithm that's not peculiarly efficient
// in this example below, it converges in about 20 iterations
// however, the total amount of iterations needed to obtain the desired value depends on a lot of factors, like sensor precision, actual position relative to the sensors, desired precision of calculated position, etc.

public class IterativeTrilaterationConvergence {

    public static void main(String[] args) {
        // all the positions that our mcu/pc thinks each sensor is situated
        Vector3 actualPositionOfSensor1 = new Vector3(2, 0, 0);
        Vector3 actualPositionOfSensor2 = new Vector3(0, 2, 0);
        Vector3 actualPositionOfSensor3 = new Vector3(-2, 0, 0);
        Vector3 actualPositionOfSensor4 = new Vector3(0, -2, 0);

        // guessed position vector
        Vector3 guessedPosition = new Vector3(0, 0, 1);

        // this is where the guessed vector is going to converge to
        Vector3 actualPositionOfTrilateratedObject = new Vector3(0.3, -0.4, 0.9);

        // these are the actual readings that the sensors we're working with are giving us (could be actual GPS data, wifi signals, you name it)
        // in this implementation example, they don't have any uncertainty added to them
        // in real life applications, you'd expect some degree of uncertainty.
        double valueOfSensor1 = actualPositionOfTrilateratedObject.minus(actualPositionOfSensor1).magnitude();
        double valueOfSensor2 = actualPositionOfTrilateratedObject.minus(actualPositionOfSensor2).magnitude();
        double valueOfSensor3 = actualPositionOfTrilateratedObject.minus(actualPositionOfSensor3).magnitude();
        double valueOfSensor4 = actualPositionOfTrilateratedObject.minus(actualPositionOfSensor4).magnitude();

        // loop that makes the guessed vector position converge
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 20; j++) {
                guessedPosition = convergeVectorTo(guessedPosition, actualPositionOfSensor1, valueOfSensor1);
                guessedPosition = convergeVectorTo(guessedPosition, actualPositionOfSensor2, valueOfSensor2);
                guessedPosition = convergeVectorTo(guessedPosition, actualPositionOfSensor3, valueOfSensor3);
                guessedPosition = convergeVectorTo(guessedPosition, actualPositionOfSensor4, valueOfSensor4);
            }

            System.out.println(i + " " + guessedPosition);
        }
    }

    // actual algorithm
    public static Vector3 convergeVectorTo(Vector3 previousGuessedPosition, Vector3 actualSensorPosition, double distanceReadBySensor) {
        // convergence rate can be set to any value between 0 and 1
        // a value of 1 is going to help to converge faster if the noise that the sensors are giving is relatively low
        // if the amount of noise is too high, you're going to want to set it to a lower value
        // basically trial and error unfortunately
        double convergenceRate = 1;
        
        double vectorLengthDifference = actualSensorPosition.minus(previousGuessedPosition).magnitude() - distanceReadBySensor;
        Vector3 delta = actualSensorPosition.minus(previousGuessedPosition).scaledToMagnitude(vectorLengthDifference*convergenceRate);
        return previousGuessedPosition.plus(delta);
    }
}
