package lambda.start;

public abstract class Hello_Me {
    public void logic() {
        long startNs = System.nanoTime(); // 변하지 않는 부분

        algo();

        //코드 조각 종료 // 변하지 않는 부분
        long endNs = System.nanoTime();
        System.out.println("실행 시간: " + (endNs - startNs) + "ns");
    }

    public abstract void algo();
}
