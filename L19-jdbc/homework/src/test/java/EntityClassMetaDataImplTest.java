import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;
import ru.otus.jdbc.mapper.EntityClassMetaDataImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EntityClassMetaDataImplTest {
    @Test
    public void simpleTestSuccess() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        var t = new EntityClassMetaDataImpl<>(Client.class);
        System.out.println(t.getName());
        var Client = t.getConstructor().newInstance();
        Client.setId(1L);
        Client.setName("rrr");
        assertTrue(true);
    }
}
