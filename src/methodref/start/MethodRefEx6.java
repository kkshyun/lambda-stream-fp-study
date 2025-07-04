package methodref.start;

import methodref.Person;

import java.util.function.BiFunction;

// 매개변수 추가
public class MethodRefEx6 {
    public static void main(String[] args) {
        Person person = new Person("Kim");

        // 람다
        BiFunction<Person, Integer, String> fun1 =
                (Person p, Integer number) -> p.introduceWithNumber(number);

        System.out.println("fun1.apply(person,1) = " + fun1.apply(person,1));

        // 메서드 참조, 타입이 첫 번째 매개변수가 됨, 그리고 첫 번째 매개변수의 메서드를 호출
        // 나머지는 순서대로 매개변수에 전달
        // 타입::메서드명
        BiFunction<Person, Integer, String> fun2 = Person::introduceWithNumber;

    }
}
