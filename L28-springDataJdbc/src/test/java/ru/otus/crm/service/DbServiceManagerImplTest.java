package ru.otus.crm.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.otus.crm.model.Manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest()
@Testcontainers
@ContextConfiguration(initializers = {DbServiceManagerImplTest.Initializer.class})
@ActiveProfiles("test")
class DbServiceManagerImplTest {
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13");
    @Autowired
    DBServiceManager dbServiceManager;

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    void saveManager() {
        Manager manager = dbServiceManager.saveManager(new Manager("mngt-1", "ManagerTest1",
                new HashSet<>(), new ArrayList<>(), true));

        assertEquals("mngt-1", manager.getId());
        assertEquals("ManagerTest1", manager.getLabel());
    }

    @Test
    void getManager() {
        Optional<Manager> managerOptional = dbServiceManager.getManager("mgr-1");
        assertTrue(managerOptional.isPresent());
        Manager manager = managerOptional.get();
        assertEquals("mgr-1", manager.getId());
        assertEquals("Manager 1", manager.getLabel());
        assertEquals(3, manager.getClients().size());
    }

    @Test
    void findAll() {
        List<Manager> managers = dbServiceManager.findAll();
        int managerCountBefore = managers.size();

        dbServiceManager.saveManager(new Manager("mngt-A", "ManagerA",
                new HashSet<>(), new ArrayList<>(), true));
        managers = dbServiceManager.findAll();

        assertEquals(managerCountBefore + 1, managers.size());
    }

    @Test
    void findByLabel() {
        List<Manager> managers = dbServiceManager.findByLabel("Manager 1");
        assertEquals(1, managers.size());
        Manager manager = managers.get(0);
        assertEquals("mgr-1", manager.getId());
        assertEquals("Manager 1", manager.getLabel());
        assertEquals(3, manager.getClients().size());
    }
}