[섹션 3. 람다](https://velog.io/@sehyun56/%EA%B9%80%EC%98%81%ED%95%9C%EC%9D%98-%EC%8B%A4%EC%A0%84-%EC%9E%90%EB%B0%94-%EA%B3%A0%EA%B8%89-3%ED%8E%B8-%EB%9E%8C%EB%8B%A4-%EC%8A%A4%ED%8A%B8%EB%A6%BC-%ED%95%A8%EC%88%98%ED%98%95-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D-%EC%84%B9%EC%85%98-3.-%EB%9E%8C%EB%8B%A4)

이번 강의에서는 람다, 고차 함수의 개념을 배우고 다양한 문제 풀이에 람다를 적용할 수 있었다.

## 💡 람다란 ?

- 자바 8부터 새롭게 도입된 개념이다.
- 자바에서 함수형 프로그래밍을 지원하기 위해 나온 기능이다.
- 익명 함수로 이름 없이 함수를 표현한다.
- 간결한 코드로 생산성과 가독성을 높일 수 있다.

📌 람다 형태

```java
// 메서드, 함수 형태
반환타입 메서드명(매개변수) { 
	본문
}
// 람다 형태
(매개변수) -> {본문}
```

⇒ 익명 클래스를 사용하면 new 키워드, 생성할 클래스명, 메서드명 등 나열해야하지만 람다는 매개변수와 본문만 적으면 되기 때문에 간단하게 사용이 가능하다.

📌 람다도 변수처럼 다룰 수 있다 !

```java
Procedure procedure = () -> { // 람다를 변수에 담음 
	System.out.println("hello! lambda");
};
procedure.run(); // 이런식으로 변수를 통해 람다를 실행
```

⇒ 값 대신 람다로 코드를 변수에 줄 수 있다고 생각하면 간단하다.

📌 람다도 익명클래스처럼 클래스가 만들어지고 인스턴스가 생성된다.

```java
public class InstanceMain {
    public static void main(String[] args) {
        Procedure procedure1 = new Procedure() {
            @Override
            public void run() {
                System.out.println("hello! lambda");
            }
        };
        System.out.println("class.class = " + procedure1.getClass());
        System.out.println("class.instance = " + procedure1);

        Procedure procedure2 = () -> {
            System.out.println("hello! lambda");
        };
        System.out.println("class.class = " + procedure2.getClass());
        System.out.println("class.instance = " + procedure2);

    }
}
->
class.class = class lambda.lambda1.InstanceMain$1
class.instance = lambda.lambda1.InstanceMain$1@6f496d9f
class.class = class lambda.lambda1.InstanceMain$$Lambda/0x000000012e003600
class.instance = lambda.lambda1.InstanceMain$$Lambda/0x000000012e003600@10f87f48
```

⇒ 람다도 익명클래스처럼 클래스가 만들어지고 인스턴스도 생성되지만 생성되는 형태가 다르다.

## 💡 함수형 인터페이스란 ?

- 아까 위에서 람다는 함수형 프로그래밍을 지원하기 위해 나온 기능이라고 했는데 여기서 함수형 인터페이스가 람다를 이해하는데 중요한 포인트이다.
- 함수형 인터페이스 : 추상 메서드를 하나만 가지는 인터페이스
- 람다는 함수형 인터페이스에만 할당할 수 있다.
    - 추상 메서드를 두 개 이상 가진 함수형 인터페이스를 람다로 구현하려고 하려면 컴파일 오류 메시지가 나온다.
- 단일 추상 메서드는 SAM(Single Abstract Method)라고 한다.
    - 즉, 함수형 인터페이스는 SAM만을 포함하는 인터페이스
- 람다는 클래스, 추상 클래스에는 할당할 수 없다. → 단일 추상 메서드를 가지는 인터페이스에만 할당 가능하다.

<aside>
🧐 왜 자바는 함수형 인터페이스에만 람다를 허용할까?

→ 람다는 하나의 함수이므로 인터페이스에 두 개 이상의 추상 메서드가 존재하면 어디에 할당해야하는지 결정할 수 없기 때문에 단일 추상 메서드를 가지는 함수형 인터페이스에만 람다를 허용한다!

</aside>

<aside>
🧐함수형 인터페이스가 단 하나의 추상 메서드만을 포함한다는 것을 보장하는 방법은?

→ 인터페이스 위에 @FunctionalInterface 애노테이션을 붙여주면 된다 !

→ 이 애노테이션을 붙이면 추상 메서드를 한 개만 가지고 있지 않으면 컴파일 단계에서 오류가 발생해 함수형 인터페이스임을 보장할 수 있다.

→ @FunctionalInterface을 사용해 함수형 인터페이스임을 선언하면 누가 추상 메서드를 추가할 때 컴파일 오류가 발생한다. 따라서 예방이 가능하다.

→ 람다를 사용할 함수형 인터페이스라면 필수로 @FunctionalInterface을 선언하자!

```java
@FunctionalInterface
public interface SamInterface {
	void run(); 
}
```

</aside>

## 💡 람다와 시그니처

- 람다를 함수형 인터페이스에서 쓰기 위해서는 메서드 시그니처가 일치해야 한다.
- 여기서 메서드 시그니처란?
    - 메서드 이름, 매개변수의 수와 타입(순서 포함), 반환 타입
- 람다는 익명 함수이므로 메서드 이름을 제외한 매개변수, 반환 타입이 함수형 인터페이스에서 선언한 추상 메서드와 일치해야 한다.

📌 메서드 시그니처가 동일해야 람다를 함수형 인터페이스에 할당이 가능하다 !

```java
// 인터페이스
@FunctionalInterface
public interface MyFunction {
    int apply(int a, int b);
}

// 람다로 표현
MyFunction myFunction = (int a, int b) -> {
      return a + b;
};
```

⇒ 매개변수가 동일하고 반환 타입이 동일하므로 람다를 함수형 인터페이스에 할당이 가능하다.

⇒ 매개변수 이름은 무조건 동일할 필요는 없고 타입과 순서만 맞으면 된다.

## 💡 람다와 생략

- 람다는 많은 문법 생략을 지원한다.
- 람다는 보통 간략하게 사용하는 것을 권장한다.

📌 단일 표현식 1

```java
MyFunction function2 = (int a, int b) -> a + b;

// 반환 타입이 void 인 경우 return 생략 가능
MyFunction function3 = (int a, int b) -> {
    System.out.println("람다 실행");
    return a + b;
};     
```

⇒ 단일 표현식일 경우 중괄호와 리턴 생략이 가능하다.

⇒ 단일 표현식이 아닐 경우 중괄호와 리턴 모두 있어야 한다.

📌 람다는 타입 추론이 가능하다. / 매개변수의 괄호 생략이 가능하다.

```java
MyFunction function2 = (a, b) -> a + b; // 타입 추론 사용

MyCall call3 = value -> value * 2;
```

⇒ 람다식의 매개변수에 타입을 정의하지 않아도 인터페이스에 이미 정의되어 있기 때문에 람다식에서 생략이 가능하다.

⇒ 매개변수가 하나일 때는 타입을 생략하고 소괄호를 생략할 수 있다.

⇒ 매개변수가 없는 경우, 둘 이상인 경우에는 소괄호를 무조건 해야한다.

## 💡 람다의 전달

- 람다를 함수형 인터페이스를 통해 변수에 대입하거나, 메서드에 전달하거나 반환할 수 있다.

📌 람다를 변수에 대입

```java
MyFunction add = (a, b) -> a + b;
MyFunction cal = add;
```

⇒ 함수형 인터페이스로 선언한 변수에 람다를 대입하는 것 = 람다 인스턴스의 참조값을 대입하는 것

📌 람다를 메서드에 전달하기

```java
// 변수를 통해 전달
MyFunction add = (a, b) -> a + b;
calculate(add);

// 직접 전달
calculate((a,b)->a+b);
```

⇒ 매개변수를 통해 메서드에 람다를 전달할 수 있다. (람다 인스턴스의 참조값을 전달)

→ 직접 전달도 가능하다.

⇒ 메서드가 람다를 반환할 수 있다. (람다 인스턴스의 참조값을 반환)

📌 람다 반환하기

```java
static MyFunction getOperation(String operator) {
	switch (operator) {
	    case "add":
	        return (a, b) -> a + b;
	    case "sub":
	        return (a, b) -> a - b;
	    default:
	} 
}
```

## 💡 고차 함수란 ?

- 고차 함수 : 함수를 값처럼 다루는 함수
    - 함수를 인자로 받는 함수, 함수를 반환하는 함수 중 하나를 만족하면 고차 함수라고 한다.
- 매개변수나 반환 값에 함수 또는 람다를 활용하는 함수가 고차 함수에 해당한다.
- 예 ) filter, map, reduce

## 💡문제풀이 2

📌 문제 4

```java
@FunctionalInterface
public interface StringFunction {
    String apply(String s);
}

public class BuildGreeterExample {
    // 고차 함수, greeting 문자열을 받아, "새로운 함수를" 반환
    public static StringFunction buildGreeter(String greeting) {
        return name -> greeting + ", " + name;
    }

    public static void main(String[] args) {
        System.out.println(buildGreeter("Hello").apply("Java"));
        System.out.println(buildGreeter("Hi").apply("Lambda"));
    }
}
->
Hello, Java
Hi, Lambda
```

📌 문제 5

```java
public class ComposeExample {
    // 고차 함수, f1, f2라는 두 함수를 인자로 받아, "f1을 먼저, f2를 나중"에 적용하는 새 함수 반환
    public static MyTransformer compose(MyTransformer f1, MyTransformer f2) {
        return s -> f2.transform(f1.transform(s));
    }
    
    public static void main(String[] args) {
    // f1: 대문자로 변환
        MyTransformer toUpper = s -> s.toUpperCase();
    // f2: 앞 뒤에 "**" 붙이기
        MyTransformer addDeco = s -> "**" + s + "**";
    // 합성: f1→f2 순서로 적용하는 함수
        MyTransformer composeFunc = compose(toUpper, addDeco);
    // 실행
        String result = composeFunc.transform("hello");
        System.out.println(result); // "**HELLO**"
    }
}
```

> 이번 강의를 통해 람다를 더욱 간단하게 작성하는 법, 고차함수 등 개념을 배우며 문제 풀이를 통해 배운 개념을 적용할 수 있었다. 람다를 이용해서 문제를 풀라고 하는 것은 비교적 쉽게 할 수 있었지만 프로젝트에서 내가 직접 설계를 이렇게 할 수 있을까? 라는 생각이 들었다. 간단한 것들부터 람다를 활용해서 직접 설계하고 코드를 짜는 연습을 해서 프로젝트에서도 많이 활용할 수 있으면 좋겠다.
>