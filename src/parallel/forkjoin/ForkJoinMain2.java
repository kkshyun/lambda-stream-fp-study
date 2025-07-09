package parallel.forkjoin;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import static util.MyLogger.log;

public class ForkJoinMain2 {
    public static void main(String[] args) {

        int processorCount = Runtime.getRuntime().availableProcessors();
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        log("processCount = " + processorCount + ", commonPool =" + commonPool.getParallelism() );

        List<Integer> data = IntStream.rangeClosed(1, 8)
                .boxed()
                .toList();

        log("[생성] " + data);

        // ForkJoinPool 생성 및 작업 수행
        SumTask task = new SumTask(data);
        Integer result = task.invoke(); // 공용 풀 사용
        log("최종 결과: " + result);

    }
}
