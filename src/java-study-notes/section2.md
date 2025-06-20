[섹션 2. 람다가 필요한 이유](https://velog.io/@sehyun56/%EA%B9%80%EC%98%81%ED%95%9C%EC%9D%98-%EC%8B%A4%EC%A0%84-%EC%9E%90%EB%B0%94-%EA%B3%A0%EA%B8%89-3%ED%8E%B8-%EB%9E%8C%EB%8B%A4-%EC%8A%A4%ED%8A%B8%EB%A6%BC-%ED%95%A8%EC%88%98%ED%98%95-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D-%EC%84%B9%EC%85%98-2.-%EB%9E%8C%EB%8B%A4%EA%B0%80-%ED%95%84%EC%9A%94%ED%95%9C-%EC%9D%B4%EC%9C%A0)
# 섹션 2. 람다가 필요한 이유

이번 강의에서는 다양한 예시를 간단하게 바꾸는 식으로 리팩토링함으로써 람다가 필요한 이유를 알 수 있었다.

## 💡 값 매개변수화란 ?

- 밑의 코드를 간단하게 리팩토링 해봄으로써 값 매개변수화 개념에 대해 알 수 있다.

📌 리팩토링 전 코드

```java
package lambda.start;

public class Ex0Main {
    public static void helloJava() {
        System.out.println("프로그램 시작");
        System.out.println("Hello Java");
        System.out.println("프로그램 종료");
    }
    public static void helloSpring() {
        System.out.println("프로그램 시작");
        System.out.println("Hello Spring");
        System.out.println("프로그램 종료");
    }
    public static void main(String[] args) {
        helloJava();
        helloSpring();
    }
}
->
프로그램 시작
Hello Java
프로그램 종료
프로그램 시작
Hello Spring
프로그램 종료
```

⇒ 출력을 보면 프로그램 시작, 프로그램 종료 부분이 중복되면서 변하지 않는 부분이다.

⇒ 여기서 핵심은 변하지 않는 부분과 변하는 부분을 분리하여 변하지 않는 부분은 그대로 유지하고, 변하는 부분은 외부에서 전달 받아서 처리한다.

📌 리팩토링 후 코드

```java
package lambda.start;

public class Ex0RefMain {

    public static void hello(String str) {
        System.out.println("프로그램 시작"); // 변하지 않는 부분
        System.out.println(str); // 변하는 부분
        System.out.println("프로그램 종료"); // 변하지 않는 부분
    }
    public static void main(String[] args) {
        hello("Hello Java");
        hello("Hello Spring");

    }
}
```

⇒ 이 코드처럼 상황에 따라 변하는 문자열 데이터를 매개변수(파라미터)를 통해 외부에서 전달 받아서 출력하면 된다.

⇒ 리팩토링 전 코드와 비교하면 메서드의 재사용성이 높아졌다는 것을 알 수 있다.

<aside>
💡

값 매개변수화

→ 아까 위에서 고친 코드처럼 구체적인 값을 메서드에 두는 것이 아닌, 매개변수를 통해 값을 외부에서 받도록 해서 메서드의 동작을 각자 다르게 하고 재사용성을 높이는 방법을 값 매개변수화라고 한다.

</aside>

## 💡 동적 매개변수화란 ?

- 밑의 코드를 리팩토링 해봄으로써 동적 매개변수화 개념에 대해 알 수 있다.

📌 리팩토링 전 코드

```java
package lambda.start;
import java.util.Random;

public class Ex1Main {
    public static void helloDice() {
        long startNs = System.nanoTime();

        //코드 조각 시작
        int randomValue = new Random().nextInt(6) + 1;
        System.out.println("주사위 = " + randomValue);

        //코드 조각 종료
        long endNs = System.nanoTime();
        System.out.println("실행 시간: " + (endNs - startNs) + "ns");
    }

    public static void helloSum() {
        long startNs = System.nanoTime();

        //코드 조각 시작
        for (int i = 1; i <= 3; i++) {
            System.out.println("i = " + i);
        }

        //코드 조각 종료
        long endNs = System.nanoTime();
        System.out.println("실행 시간: " + (endNs - startNs) + "ns");
    }

    public static void main(String[] args) {
        helloDice();
        helloSum();
    }
}
->
주사위 = 3
실행 시간: 37517366ns
i = 1
i = 2
i = 3
실행 시간: 639286ns
```

⇒ 아까 코드와 달리 값만 변하는 것이 아닌 코드가 변하는 부분이다.

⇒ 이 때, 인터페이스를 정의하고 구현 클래스를 만들면 코드 조각을 전달할 수 있다.

📌 리팩토링 후 코드

```java
package lambda;

public interface Procedure {
    void run();
}
```

```java
package lambda.start;
import lambda.Procedure;

import java.util.Random;

public class Ex1RefMain {
    public static void hello(Procedure procedure) {
        long startNs = System.nanoTime();

        //코드 조각 시작
        procedure.run();

        //코드 조각 종료
        long endNs = System.nanoTime();
        System.out.println("실행 시간: " + (endNs - startNs) + "ns");

    }

    static class Dice implements Procedure {
        @Override
        public void run() {
            int randomValue = new Random().nextInt(6) + 1;
            System.out.println("주사위 = " + randomValue);
        }
    }

    static class Sum implements Procedure {
        @Override
        public void run() {
            for (int i = 1; i <= 3; i++) {
                System.out.println("i = " + i);
            }
        }
    }

    public static void main(String[] args) {
        Procedure dice = new Dice();
        Procedure sum = new Sum();

        hello(dice);
        hello(sum);
    }
}
```

⇒ 코드 조각을 전달하기 위한 방법으로, 인터페이스를 이용해 변하는 코드 부분을 구현하하고, Procedure 인터페이스를 오버라이딩해 다른 동작을 하는 클래스를 만들었다. 클래스들을 인스턴스로 생성한 뒤, 메서드의 매개변수로 전달함으로써 변하는 코드 조각을 외부에서 주입해 실행할 수 있도록 했다.

⇒ 다형성을 활용해서 외부에서 전달되는 인스턴스에 따라 각각 다른 코드 조각이 실행된다. 이를 통해 고정된 로직과 변하는 로직을 분리하고, 메서드의 재사용성을 높일 수 있다.

<aside>
💡

동적 매개변수화

→ 아까 위에서 고친 코드처럼 매개변수를 통해 외부에서 코드 조각을 전달 받도록 해서 메서드의 동작을 각자 다르게 하고 재사용성을 높이는 방법을 동적 매개변수화라고 한다.

→ 자바8에서 나온 람다를 사용하면 코드 조각을 매우 간단하게 전달할 수 있다.

</aside>

## 💡 익명 클래스를 사용해서 동적 매개변수화 구현하기

- 익명 클래스로 동적 매개변수화를 구현해봄으로써 람다를 사용하면 편리하다는 것을 알 수 있다.

📌 익명 클래스 코드 1

```java
package lambda.start;
import lambda.Procedure;

import java.util.Random;

// 익명 클래스 사용
public class Ex1RefMain2 {
    public static void hello(Procedure procedure) {
        long startNs = System.nanoTime();

        //코드 조각 시작
        procedure.run();

        //코드 조각 종료
        long endNs = System.nanoTime();
        System.out.println("실행 시간: " + (endNs - startNs) + "ns");

    }

    public static void main(String[] args) {
        Procedure dice = new Procedure() {
            @Override
            public void run() {
                int randomValue = new Random().nextInt(6) + 1;
                System.out.println("주사위 = " + randomValue);
            }
        };
        Procedure sum = new Procedure() {
            @Override
            public void run() {
                for (int i = 1; i <= 3; i++) {
                    System.out.println("i = " + i);
                }
            }
        };

        hello(dice);
        hello(sum);
    }
}
```

⇒ 코드를 구현하고 객체를 만들어서 코드 조각을 전달하는 것보다 객체를 생성하면서 바로 코드를 구현하는 것이 좀 더 간단하다는 것을 알 수 있다.

📌 익명 클래스 코드 2 - 참조값 직접 전달

```java
package lambda.start;
import lambda.Procedure;

import java.util.Random;

// 익명 클래스 사용, 변수 제거, 익명 클래스의 참조값을 매개변수(파라미터)에 직접 전달
public class Ex1RefMain3 {
    public static void hello(Procedure procedure) {
        long startNs = System.nanoTime();

        //코드 조각 시작
        procedure.run();

        //코드 조각 종료
        long endNs = System.nanoTime();
        System.out.println("실행 시간: " + (endNs - startNs) + "ns");

    }

    public static void main(String[] args) {

        hello(new Procedure() {
            @Override
            public void run() {
                int randomValue = new Random().nextInt(6) + 1;
                System.out.println("주사위 = " + randomValue);
            }
        });
        // 인라인 단축키 : option+command+n
        hello(new Procedure() {
            @Override
            public void run() {
                for (int i = 1; i <= 3; i++) {
                    System.out.println("i = " + i);
                }
            }
        });
    }
}
```

⇒ 익명 클래스 코드 1와 달리, 지역 변수로 익명 클래스의 참조값을 담아두지 않고 hello 매개변수에 참조값을 직접 전달한다.

## 💡 람다란 ?

- 자바에서 메서드에 매개변수에 인수로 전달할 수 있는 것은 int, double 과 같은 기본형 타입, Procedure와 같은 참조형 타입(인스턴스)이다.
- 람다를 통해서 클래스나 인스턴스를 생성하는 복잡한 과정을 거치지 않고도 코드 블럭을 간단하게 인수로 전달할 수 있다.

📌 람다 코드

```java
package lambda.start;
import lambda.Procedure;

import java.util.Random;

// 람다 사용
public class Ex1RefMain4 {
    public static void hello(Procedure procedure) {
        long startNs = System.nanoTime();

        //코드 조각 시작
        procedure.run();

        //코드 조각 종료
        long endNs = System.nanoTime();
        System.out.println("실행 시간: " + (endNs - startNs) + "ns");

    }

    public static void main(String[] args) {

        // 람다 사용
        hello(() -> {
            int randomValue = new Random().nextInt(6) + 1;
            System.out.println("주사위 = " + randomValue);
        });

        hello(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("i = " + i);
            }
        });
    }
}
```

⇒ 람다를 사용한 부분은 () → { } 이다.

⇒ 람다를 사용함으로써 클래스나 인스턴스를 정의하지 않고, 다른 방법들보다 간단하게 코드 블럭을 직접 정의하고 전달할 수 있다.

📌 매개변수가 없는 익명 클래스 코드 → 람다로 변경

```java
package lambda.lambda1;

import lambda.Procedure;

public class ProcedureMain1 {
    public static void main(String[] args) {
        Procedure procedure = new Procedure() {
            @Override
            public void run() {
                System.out.println("hello! lambda");
            };

        };
        procedure.run();
    }
}

->

// 람다로 변경
public class ProcedureMain2 {
    public static void main(String[] args) {
        Procedure procedure = () -> {
            System.out.println("hello! lambda");
        };
        procedure.run();
    }
}
```

⇒ Procedure의 run() 메서드는 매개변수가 없고, 반환값이 없다.

⇒ () : 메서드의 매개변수, {} : 코드 조각이 들어가는 본문

⇒ 람다를 사용할 때는 이름, 반환 타입은 생략하고, 매개변수와 본문만 간단하게 적으면 된다.

⇒ 람다를 사용함으로써 코드 조각을 훨씬 편리하게 전달할 수 있다.

📌 매개변수가 있는 익명 클래스 코드 → 람다로 변경

```java
package lambda;

public interface MyFunction {
    int apply(int a, int b);
}
```

```java
import lambda.MyFunction;

public class MyFunction1 {
    public static void main(String[] args) {
        MyFunction myFunction = new MyFunction() {
            @Override
            public int apply(int a, int b) {
                return a+b;
            }
        };
        int result = myFunction.apply(1,2);
        System.out.println("result = " + result);
    }
}

->

// 람다로 변경
public class MyFunction2 {
    public static void main(String[] args) {
        MyFunction myFunction = (int a, int b) -> {
            return a+b;
        };
        int result = myFunction.apply(1,2);
        System.out.println("result = " + result);
    }
}

```

⇒ 매개변수가 있기 때문에 (int a, int b) 처럼 () 부분에 매개변수를 적으면 된다.

## 💡 함수와 메서드의 차이?

- 함수는 독립적으로 존재하고 보통 이름(매개변수) 형태로 호출된다.
- 메서드는 클래스 또는 객체에 속해 있는 함수이고 보통 객체(인스턴스).메서드이름(매개변수) 형태로 호출된다.
- 함수와 메서드는 수행하는 역할은 같지만 크게 독립적인지 클래스에 소속되어 있는지에 따라 차이가 난다.

> 이번 강의를 통해 람다는 익명 클래스(클래스 정의와 인스턴스 생성 동시에 하여 간단하게 구현 가능)처럼 추상 메서드의 구현 코드를 간단하게 작성할 수 있는 방식이라는 점을 알게 되었다. 추가로 찾아보니, 추상 메서드가 하나만 있는 인터페이스를 함수형 인터페이스라고 부르고, 람다는 이 함수형 인터페이스를 간결한 문법으로 구현할 수 있는 표현식이라는 것을 이해하게 되었다. 그리고 익명 클래스는 추상 메서드가 여러 개이거나 복잡한 상황에서는 여전히 유용하다는 것도 알게 되었다.
>
>
> 처음에는 람다가 일회성 용도로만 쓰이는 줄 알았지만, 찾아보면서 람다도 변수에 저장하거나 재사용할 수 있다는 사실도 알게 되었다. 앞으로 강의를 보면서 람다에 대한 궁금증을 해소하고 프로젝트에서도 람다를 활용해볼 수 있으면 좋을 것 같다.
>