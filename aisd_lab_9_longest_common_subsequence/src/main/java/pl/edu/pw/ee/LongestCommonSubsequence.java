package pl.edu.pw.ee;

class LongestCommonSubsequence {

    private String topStr;
    private String leftStr;
    private MatrixElem[][] matrix;

    class MatrixElem {

        int value;
        char direction;

        MatrixElem(int value, char direction) {
            this.value = value;
            this.direction = direction;
        }

        public int getValue() {
            return this.value;
        }

        public char getDirection() {
            return this.direction;
        }
    }

    public LongestCommonSubsequence(String topStr, String leftStr) {
        checkString(topStr);
        checkString(leftStr);

        this.topStr = topStr;
        this.leftStr = leftStr;
        this.matrix = new MatrixElem[leftStr.length() + 1][topStr.length() + 1];
        buildMatrix();
        fillMatrix();
    }

    private void buildMatrix() {
        for (int row = 0; row <= leftStr.length(); row++) {
            for (int col = 0; col <= topStr.length(); col++) {
                matrix[row][col] = new MatrixElem(0, '\0');
            }
            System.out.println();
        }
    }

    private void fillMatrix() {
        for (int row = 0; row <= leftStr.length(); row++) {
            matrix[row][0] = new MatrixElem(0, '\0');
        }
        for (int col = 0; col <= topStr.length(); col++) {
            matrix[0][col] = new MatrixElem(0, '\0');
        }

        int row = 1;
        for (char character : leftStr.toCharArray()) {
            for (int col = 1; col <= topStr.length(); col++) {
                if (character == topStr.charAt(col - 1)) {
                    int valueAcross = matrix[row - 1][col - 1].getValue() + 1;
                    matrix[row][col].value = valueAcross;
                    matrix[row][col].direction = '\\';
                } else {
                    int left = matrix[row][col - 1].getValue();
                    int up = matrix[row - 1][col].getValue();
                    int compare = up - left;

                    if (compare >= 0) {
                        matrix[row][col].value = up;
                        matrix[row][col].direction = '^';
                    } else {
                        matrix[row][col].value = left;
                        matrix[row][col].direction = '<';
                    }
                }
            }
            row++;
        }
    }

    public String findLCS() {
        int row = leftStr.length();
        int column = topStr.length();
        
        MatrixElem current = matrix[row][column];
        StringBuilder sb = new StringBuilder("");
        
        while (current.getValue() != 0) {
            char direction = current.getDirection();
            if(direction == '<')
            {
                current = matrix[row][--column];
            }
            else if(direction == '^'){
                current = matrix[--row][column];
            }
            else if(direction == '\\'){
                sb.append(topStr.charAt(column));
                current = matrix[--row][--column];
            }
        }
        
        return sb.toString();
    }


    public void display() {
        for (int row = 0; row <= leftStr.length(); row++) {
            for (int col = 0; col <= topStr.length(); col++) {
                System.out.print(matrix[row][col].direction);
                System.out.print(matrix[row][col].value);
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    private void checkString(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }
        if (input.equals("")) {
            throw new IllegalArgumentException("Input string cannot be empty");
        }
    }
}
