package ru.otus;

import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;
import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.processor.*;

import java.time.LocalDateTime;
import java.util.List;
public class HomeWork {
//    private int field11;
//    private int field12;
//    private ObjectForMessage field13;
//    private static HistoryListener historyListener;
//
//    public HomeWork(int field11, int field12, ObjectForMessage field13) {
//        this.field11 = field11;
//        this.field12 = field12;
//        this.field13 = field13;
//    }
//private void  logMessage(String message){
//        if (historyListener != null){
//            historyListener.onUpdated(new Message(message));
//        }
//}
//    private static void swapFields(HomeWork odj) {
//        int temp = odj.field11;
//        odj.field11 = odj.field12;
//        odj.field12 = temp;
//
//
//    }
//
//    public static void throwExceptionIfEvenSecond() {
//        Calendar calendar = Calendar.getInstance();
//        int second = calendar.get(Calendar.SECOND);
//        if (second % 2 == 0) {
//            throw new RuntimeException("Even second exception");
//        }
//    }
//    public  static void setHistoryListener(HistoryListener listener){
//        historyListener=listener;
//    }
/*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
             Секунда должна определяьться во время выполнения.
             Тест - важная часть задания
             Обязательно посмотрите пример к паттерну Мементо!
       4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
          Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
          Для него уже есть тест, убедитесь, что тест проходит
     */

    public static void main(String[] args) {
        List<Processor> processors = List.of(new ProcessorSwapFields(),
                new ProcessorFailOnEvenSeconds(() -> LocalDateTime.now().getSecond()));
        var complexProcessor = new ComplexProcessor(processors, ex -> System.out.println("No luck this time: " + ex));
        var historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .build();


        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(historyListener);
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */
    }
}
