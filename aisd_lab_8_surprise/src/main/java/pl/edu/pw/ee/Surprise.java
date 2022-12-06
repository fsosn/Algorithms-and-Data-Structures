package pl.edu.pw.ee;

public class Surprise {

    public int countMaxSum(int[] array) {
        checkArray(array);

        int currentIndex = 0;
        int numberOfFields = array.length;
        int maxSum = array[0];
        int max = -100_000;
        int currentValue;
        int move = -1_000_000;
        boolean wasInLoop;

        while (currentIndex + 1 < numberOfFields) {
            wasInLoop = false;
            for (int i = 1; i <= 6; i++) {
                if (currentIndex + i != numberOfFields) {
                    currentValue = array[currentIndex + i];
                    if (currentValue >= max) {
                        max = currentValue;
                        move = i;
                    }
                    wasInLoop = true;
                }
            }
            if (wasInLoop) {
                maxSum += max;
                currentIndex += move;
            }
        }

        return maxSum;
    }

    private void checkArraySize(int[] array) {
        if (array.length < 2 || array.length > 99_999) {
            throw new IllegalArgumentException("Array size cannot be less than 2 or greater than 99 999.");
        }
    }

    private void checkArrayElems(int[] array) {
        for (int i : array) {
            if (i < -99_000 || i > 99_999) {
                throw new IllegalArgumentException("Array elements cannot be less than -99_999 or greater than 99_999.");
            }
        }
    }

    private void checkArray(int[] array) {
        checkArraySize(array);
        checkArrayElems(array);
    }
}
