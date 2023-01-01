package pl.edu.pw.ee;

class LongestCommonSubsequence {

    private final String topStr;
    private final String leftStr;
    private final MatrixElement[][] matrix;
    private boolean foundLCS;

    public LongestCommonSubsequence(String topStr, String leftStr) {
        checkString(topStr);
        checkString(leftStr);

        this.topStr = topStr;
        this.leftStr = leftStr;
        this.foundLCS = false;
        this.matrix = new MatrixElement[this.leftStr.length() + 1][this.topStr.length() + 1];

        buildMatrix();
    }

    public String findLCS() {
        int row = leftStr.length();
        int column = topStr.length();
        MatrixElement currentElement = matrix[row][column];

        StringBuilder result = new StringBuilder();
        int topStrIndex = topStr.length() - 1;

        while (currentElement.getValue() != 0) {
            currentElement.setVisited(true);
            switch (currentElement.getDirection()) {
                case MatrixElement.DIRECTION_SYMBOL_UP:
                    currentElement = matrix[--row][column];
                    break;
                case MatrixElement.DIRECTION_SYMBOL_LEFT:
                    currentElement = matrix[row][--column];
                    topStrIndex--;
                    break;
                case MatrixElement.DIRECTION_SYMBOL_ACROSS:
                    result.append(topStr.charAt(topStrIndex));
                    currentElement = matrix[--row][--column];
                    topStrIndex--;
                    break;
            }
        }
        this.foundLCS = true;

        return result.reverse().toString();
    }

    public void display() {
        if (!this.foundLCS) {
            findLCS();
        }
        int matrixWidth = topStr.length() + 2;

        printTopRowOfMatrix(matrixWidth);

        int leftStrIdx = 0;
        for (int row = 0; row <= leftStr.length(); row++) {
            if (row == 0) {
                printRowFilledWithZeroes(matrixWidth);
            } else {
                printRowWithDirsAndValsForLeftStrCharAtIdx(leftStrIdx++, row, matrixWidth);
            }
            printTopOrBottomEdgeOfMatrixCells(matrixWidth);
        }
    }

    private void buildMatrix() {
        for (int row = 0; row <= leftStr.length(); row++) {
            matrix[row][0] = new MatrixElement(0, MatrixElement.DIRECTION_SYMBOL_DEFAULT);
        }
        for (int col = 0; col <= topStr.length(); col++) {
            matrix[0][col] = new MatrixElement(0, MatrixElement.DIRECTION_SYMBOL_DEFAULT);
        }

        int row = 1;
        for (char character : leftStr.toCharArray()) {
            for (int col = 1; col <= topStr.length(); col++) {
                if (character == topStr.charAt(col - 1)) {
                    int valueAcross = matrix[row - 1][col - 1].getValue() + 1;
                    matrix[row][col] = new MatrixElement(valueAcross, MatrixElement.DIRECTION_SYMBOL_ACROSS);
                } else {
                    int valueLeft = matrix[row][col - 1].getValue();
                    int valueUp = matrix[row - 1][col].getValue();
                    int compare = valueUp - valueLeft;

                    if (compare >= 0) {
                        matrix[row][col] = new MatrixElement(valueUp, MatrixElement.DIRECTION_SYMBOL_UP);
                    } else {
                        matrix[row][col] = new MatrixElement(valueLeft, MatrixElement.DIRECTION_SYMBOL_LEFT);
                    }
                }
            }
            row++;
        }
    }

    private void printTopRowOfMatrix(int matrixWidth) {
        printTopOrBottomEdgeOfMatrixCells(matrixWidth);
        printSideEdgesOfMatrixCells(matrixWidth);

        printTopStrCharsInMiddleOfCells(matrixWidth);

        printSideEdgesOfMatrixCells(matrixWidth);
        printTopOrBottomEdgeOfMatrixCells(matrixWidth);
    }

    private void printRowWithDirsAndValsForLeftStrCharAtIdx(int leftStrIdx, int row, int matrixWidth) {
        printDirectionsInTopLineOfCellsInRow(row);
        printLeftSideCharacter(leftStr.charAt(leftStrIdx));
        printValuesInMiddleofCellsInRow(row);
        printSideEdgesOfMatrixCells(matrixWidth);
    }

    private void printRowFilledWithZeroes(int matrixWidth) {
        printSideEdgesOfMatrixCells(matrixWidth);
        System.out.print("|     |");
        for (int col = 0; col <= topStr.length(); col++) {
            System.out.print("  " + matrix[0][col].getValue() + "  |");
        }
        System.out.print(System.lineSeparator());
        printSideEdgesOfMatrixCells(matrixWidth);
    }

    private void printTopOrBottomEdgeOfMatrixCells(int matrixWidth) {
        System.out.print("+");
        for (int i = 0; i < matrixWidth; i++) {
            System.out.print("-----+");
        }
        System.out.print(System.lineSeparator());
    }

    private void printSideEdgesOfMatrixCells(int matrixWidth) {
        System.out.print("|");
        for (int i = 0; i < matrixWidth; i++) {
            System.out.print("     |");
        }
        System.out.print(System.lineSeparator());
    }

    private void printTopStrCharsInMiddleOfCells(int matrixWidth) {
        System.out.print("|");
        int topStrIdx = 0;
        for (int i = 0; i < matrixWidth; i++) {
            if (i < 2) {
                System.out.print("     |");
            } else {
                printCharacterInMiddleOfCell(topStr.charAt(topStrIdx++));
            }
        }
        System.out.print(System.lineSeparator());
    }

    private void printCharacterInMiddleOfCell(char charAtIndex) {
        String currentChar = replaceEscapeSequence(charAtIndex);
        if (currentChar.length() == 1) {
            System.out.print("  " + currentChar + "  |");
        } else {
            System.out.print(" " + currentChar + "  |");
        }
    }

    private void printDirectionsInTopLineOfCellsInRow(int row) {
        System.out.print("|     |");
        for (int col = 0; col <= topStr.length(); col++) {
            if (!matrix[row][col].getVisited()) {
                System.out.print("     |");
            } else {
                char direction = matrix[row][col].getDirection();
                switch (direction) {
                    case MatrixElement.DIRECTION_SYMBOL_ACROSS:
                        System.out.print(String.valueOf(direction) + "    |");
                        break;
                    case MatrixElement.DIRECTION_SYMBOL_UP:
                        System.out.print("  " + String.valueOf(direction) + "  |");
                        break;
                    default:
                        System.out.print("     |");
                }
            }
        }
        System.out.print(System.lineSeparator());
    }

    private void printValuesInMiddleofCellsInRow(int row) {
        for (int col = 0; col <= topStr.length(); col++) {
            if (matrix[row][col].getDirection() == MatrixElement.DIRECTION_SYMBOL_LEFT && matrix[row][col].getVisited()) {
                System.out.print(MatrixElement.DIRECTION_SYMBOL_LEFT + " " + matrix[row][col].getValue() + "  |");
            } else {
                System.out.print("  " + matrix[row][col].getValue() + "  |");
            }
        }
        System.out.print(System.lineSeparator());
    }

    private void printLeftSideCharacter(char charAtIndex) {
        System.out.print("|");
        printCharacterInMiddleOfCell(charAtIndex);
    }

    private String replaceEscapeSequence(char character) {
        switch (character) {
            case '\n':
                return "\\n";
            case '\t':
                return "\\t";
            case '\r':
                return "\\r";
            case '\f':
                return "\\f";
            case '\b':
                return "\\b";
            default:
                return String.valueOf(character);
        }
    }

    private void checkString(String inputString) {
        if (inputString == null) {
            throw new IllegalArgumentException("Input string cannot be null.");
        }
        if (inputString.equals("")) {
            throw new IllegalArgumentException("Input string cannot be empty.");
        }
    }
}
