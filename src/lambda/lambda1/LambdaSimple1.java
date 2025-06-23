package lambda.lambda1;

import lambda.MyFunction;

public class LambdaSimple1 {
    public static void main(String[] args) {
        // 기본
        MyFunction function1 = (int a, int b) -> {
            return a + b;
        };
        System.out.println("function1 : " + function1.apply(1,2));

        // 단일 표현식인 경우 중괄호와 리턴 생략 가능
        MyFunction function2 = (int a, int b) -> a + b;
        System.out.println("function2 : " + function2.apply(1,2));

        // 단일 표현식이 아닐 경우 중괄호와 리턴 모두 필수
        // 중괄호가 있으면 반드시 리턴이 있어야함
        // 반환 타입이 void 인 경우 return 생략 가능
        MyFunction function3 = (int a, int b) -> {
            System.out.println("람다 실행");
            return a + b;
        };
        System.out.println("function3 : " + function3.apply(1,2));

    }
}
