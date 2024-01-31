import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntityClassMetaDataImpl;
import ru.otus.jdbc.mapper.EntityReflector;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertTrue;
@SuppressWarnings("unckecked")
public class EntityReflectorTest {
    @Test
    public void happyPathSuccess() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Manager man = new Manager(1L,"test", "testparam");
        Class<Manager> cls = Manager.class;
        EntityClassMetaData<Manager> meta = new EntityClassMetaDataImpl<>(cls);
        EntityReflector<Manager> refl = new EntityReflector<Manager>();
        var p=meta.getIdField();
        var params = refl.getFieldsValue(meta.getFieldsWithoutId(),man);
        assertTrue(true);
    }
}
