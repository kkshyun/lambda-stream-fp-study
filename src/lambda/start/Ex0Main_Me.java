package lambda.start;

public class Ex0Main_Me extends Language_Me {
    public Ex0Main_Me(String str) {
        startFunction();
        helloFunction(str);
        endFunction();
    }

    public static void main(String[] args) {
        String str = "Java";
        new Ex0Main_Me(str);
        str = "Spring";
        new Ex0Main_Me(str);
    }
}

