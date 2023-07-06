package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.otus.api.model.SensorData;
import ru.otus.services.FakeSensorDataGenerator;
import ru.otus.services.SensorDataProcessingFlowImpl;
import ru.otus.services.SensorsDataQueueChannel;
import ru.otus.services.SensorsDataServerImpl;
import ru.otus.services.processors.SensorDataProcessorCommon;
import ru.otus.services.processors.SensorDataProcessorRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AppTest {
    private static final String ALL_ROOMS_BINDING = "*";
    private static final String ROOM_NAME_BINDING = "Комната: 4";
    private static final int SENSORS_COUNT = 4;
    private static final int SENSORS_DATA_QUEUE_CAPACITY = 1000;
    public static final int BINDED_ROOM_NUMBER = 4;

    private RandomGenerator randomGenerator;
    private FakeSensorDataGenerator fakeSensorDataGenerator;
    private SensorDataProcessingFlowImpl sensorDataProcessingFlow;
    private SensorDataProcessorCommon sensorDataProcessorCommon;
    private SensorDataProcessorRoom sensorDataProcessorRoom;

    @BeforeEach
    void setUp() {

        SensorsDataQueueChannel sensorsDataChannel = new SensorsDataQueueChannel(SENSORS_DATA_QUEUE_CAPACITY);
        var sensorsDataServer = new SensorsDataServerImpl(sensorsDataChannel);
        sensorDataProcessingFlow = new SensorDataProcessingFlowImpl(sensorsDataChannel);

        randomGenerator = spy(RandomGenerator.class);
        fakeSensorDataGenerator = new FakeSensorDataGenerator(randomGenerator, sensorsDataServer,
                SENSORS_COUNT);
        sensorDataProcessorCommon = mock(SensorDataProcessorCommon.class);
        sensorDataProcessorRoom = mock(SensorDataProcessorRoom.class);

        sensorDataProcessingFlow.bindProcessor(ALL_ROOMS_BINDING, sensorDataProcessorCommon);
        sensorDataProcessingFlow.bindProcessor(ROOM_NAME_BINDING, sensorDataProcessorRoom);
    }

    @RepeatedTest(10)
    void shouldInvokeCorrectProcessorsAccordingItsBindings() throws InterruptedException {
        AtomicInteger randomIntInvocationsCount = new AtomicInteger();
        doAnswer(a -> (randomIntInvocationsCount.incrementAndGet() % 2 == 0)
                ? BINDED_ROOM_NUMBER
                : a.callRealMethod()
        ).when(randomGenerator).nextInt(1, SENSORS_COUNT + 1);

        var allDataByRooms = new ConcurrentHashMap<String, List<SensorData>>();
        doAnswer(a -> {
            SensorData sd = a.getArgument(0, SensorData.class);
            allDataByRooms.computeIfAbsent(sd.getRoom(), k -> new ArrayList<>()).add(sd);
            return null;
        }).when(sensorDataProcessorCommon).process(any());

        CountDownLatch roomProcessorInvocationCountLatch = new CountDownLatch(10);
        doAnswer(a -> {
            roomProcessorInvocationCountLatch.countDown();
            return null;
        }).when(sensorDataProcessorRoom).process(any());


        fakeSensorDataGenerator.start();
        sensorDataProcessingFlow.startProcessing();

        var waitRes = roomProcessorInvocationCountLatch.await(30, TimeUnit.SECONDS);
        assertThat(waitRes).isTrue();

        sensorDataProcessingFlow.stopProcessing();
        fakeSensorDataGenerator.stop();

        var allData = allDataByRooms.entrySet().stream()
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toList());
        var allDataByBindedRoom = allDataByRooms.getOrDefault(ROOM_NAME_BINDING, new ArrayList<>());

        assertThat(allData).isNotEmpty();

        ArgumentCaptor<SensorData> argumentCaptorForProcessorCommon = ArgumentCaptor.forClass(SensorData.class);
        verify(sensorDataProcessorCommon, times(allData.size())).process(argumentCaptorForProcessorCommon.capture());
        assertThat(argumentCaptorForProcessorCommon.getAllValues()).containsExactlyInAnyOrderElementsOf(allData);

        ArgumentCaptor<SensorData> argumentCaptorForProcessorRoom = ArgumentCaptor.forClass(SensorData.class);
        verify(sensorDataProcessorRoom, times(allDataByBindedRoom.size())).process(argumentCaptorForProcessorRoom.capture());
        assertThat(argumentCaptorForProcessorRoom.getAllValues()).containsExactlyInAnyOrderElementsOf(allDataByBindedRoom);
    }
}