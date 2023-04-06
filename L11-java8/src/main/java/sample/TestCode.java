package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static java.lang.Thread.sleep;

public class TestCode {

    public void method() throws Exception {
        int x = 5;

//        method1( x -> x * x );


        TestInterface test = () -> {
            System.out.println();
            throw new Exception();
        };

        method1( test );

    }

    public void method1( TestInterface testInterface ) throws Exception {
        testInterface.doSmth();
    }


    /// Тяжеленная загрузка
    public List<Integer> hardLoad() {
        try {
            sleep( 100 * 1000 );
        } catch ( InterruptedException e ) {
            throw new RuntimeException( e );
        }
        return new ArrayList<>();
    }

    public void processing( Supplier<?> supplier ) {
        if ( check() ) {
            supplier.get();
            //process data
        } else {
            // не надо процессить
        }
    }

    public void action() {
        processing( () -> hardLoad() );
    }

    public boolean check() {
        return false;
    }
}
