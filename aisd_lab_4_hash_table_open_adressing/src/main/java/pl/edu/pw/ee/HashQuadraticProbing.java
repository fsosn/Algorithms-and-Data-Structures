package pl.edu.pw.ee;

public class HashQuadraticProbing<T extends Comparable<T>> extends HashOpenAdressing<T> {

    private final double a;
    private final double b;

    public HashQuadraticProbing() {
        super();

        this.a = 0.5;
        this.b = 0.5;
    }

    public HashQuadraticProbing(int size, double a, double b) {
        super(size);

        validateFunctionArguments(a, b);
        this.a = a;
        this.b = b;
    }

    @Override
    int hashFunc(int key, int i) {
        int m = getSize();

        int hash = (int) (key % m + this.a * i + this.b * i * i) % m;

        hash = hash < 0 ? -hash : hash;

        return hash;
    }

    private void validateFunctionArguments(double a, double b) {
        if (a == 0) {
            throw new IllegalArgumentException("Argument 'a' passed to quadratic probing function cannot equal 0.");
        }
        if (b == 0) {
            throw new IllegalArgumentException("Argument 'b' passed to quadratic probing function cannot equal 0.");
        }
    }
}
