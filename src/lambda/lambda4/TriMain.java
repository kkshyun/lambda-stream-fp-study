package lambda.lambda4;

public class TriMain {
    public static void main(String[] args) {
        TriFunction<Integer, Integer, Integer, Integer> triFunction =
                (a, b, c)-> a + b + c;
    }
    @FunctionalInterface
    interface TriFunction<A, B, C, R> {
        R apply(A a, B b, C c);
    }
}
