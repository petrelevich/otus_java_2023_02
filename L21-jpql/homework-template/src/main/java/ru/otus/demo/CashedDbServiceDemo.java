package ru.otus.demo;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.CashedDbServiceClientImpl;

import java.util.List;

public class CashedDbServiceDemo {
    private static final Logger log = LoggerFactory.getLogger(CashedDbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
///
        var cashedDbServiceClient = new CashedDbServiceClientImpl(transactionManager, clientTemplate);
        cashedDbServiceClient.saveClient(new Client("dbServiceFirst", new Address("Butlerova 1"), List.of(new Phone("39409504340"))));

        var clientSecond = cashedDbServiceClient.saveClient(new Client("dbServiceSecond", new Address("Butlerova 1"), List.of(new Phone("39409504340"))));
        var clientSecondSelected = cashedDbServiceClient.getClient(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        log.info("clientSecondSelected:{}", clientSecondSelected);
///
        cashedDbServiceClient.saveClient(new Client(clientSecondSelected.getId(), "dbServiceSecondUpdated", new Address("Butlerova 1"), List.of(new Phone("39409504340"))));
        var clientUpdated = cashedDbServiceClient.getClient(clientSecondSelected.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecondSelected.getId()));
        log.info("clientUpdated:{}", clientUpdated);

        log.info("All clients");
        cashedDbServiceClient.findAll().forEach(client -> log.info("client:{}", client));
    }
}
