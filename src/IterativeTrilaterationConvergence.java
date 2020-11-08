public class IterativeTrilaterationConvergence {

    public static void main(String[] args) {
        Vector3 measurementPositionStation1 = new Vector3(2, 0, 0);
        Vector3 measurementPositionStation2 = new Vector3(0, 2, 0);
        Vector3 measurementPositionStation3 = new Vector3(-2, 0, 0);
        Vector3 measurementPositionStation4 = new Vector3(0, -2, 0);

        Vector3 actualPosition = new Vector3(0.3, -0.4, 0.9);
        Vector3 trilateratedPosition = new Vector3(0, 0, 1);

        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 20; j++) {
                trilateratedPosition = convergeVectorTo(trilateratedPosition, measurementPositionStation1, actualPosition.minus(measurementPositionStation1).magnitude());
                trilateratedPosition = convergeVectorTo(trilateratedPosition, measurementPositionStation2, actualPosition.minus(measurementPositionStation2).magnitude());
                trilateratedPosition = convergeVectorTo(trilateratedPosition, measurementPositionStation3, actualPosition.minus(measurementPositionStation3).magnitude());
                trilateratedPosition = convergeVectorTo(trilateratedPosition, measurementPositionStation4, actualPosition.minus(measurementPositionStation4).magnitude());
            }

            if(i > 10 && i < 80) {
                //actualPosition = actualPosition.plus(new Vector3(0.01, 0.02, 0.03));
            }

            System.out.println(i + " " + trilateratedPosition);
            //System.out.println(i + " " + trilateratedPosition.minus(actualPosition).magnitude());
        }
    }

    // basically, each call makes the guess closer to the sample point by the error of distance it has from the actual distance it should be having
    public static Vector3 convergeVectorTo(Vector3 guessVector, Vector3 sensorPosition, double readDistanceFromSensor) {
        // looks like 1 is indeed optimal
        // might wanna look more into testing different values though
        double convergenceRate = 1;
        double vectorLengthDifference = sensorPosition.minus(guessVector).magnitude() - readDistanceFromSensor;
        Vector3 delta = sensorPosition.minus(guessVector).scaledToMagnitude(vectorLengthDifference*convergenceRate);
        return guessVector.plus(delta);
    }
}
