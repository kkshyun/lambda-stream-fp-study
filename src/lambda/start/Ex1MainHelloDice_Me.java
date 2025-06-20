package lambda.start;

import java.util.Random;

public class Ex1MainHelloDice_Me extends Hello_Me {

    @Override
    public void algo() {
        int randomValue = new Random().nextInt(6) + 1;
        System.out.println("주사위 = " + randomValue);
    }
}
