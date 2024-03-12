package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.cachehw.HwListener;
import ru.otus.crm.cachehw.MyCache;
import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public class CashedDbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(CashedDbServiceClientImpl.class);
    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final MyCache<Long, Client> clientMyCache;

    public CashedDbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.clientMyCache = new MyCache<>(1000);


        HwListener<Long, Client> listener = new HwListener<Long, Client>() {
            @Override
            public void notify(Long key, Client value, String action) {
                log.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };

        clientMyCache.addListener(listener);
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                var savedClient = clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                return savedClient;
            }
            var savedClient = clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", savedClient);
            clientMyCache.put(savedClient.getId(), savedClient);
            log.info("added to cash client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        if (clientMyCache.exists(id)) {
            Client cashedCLient = clientMyCache.get(id);
            log.info("client from cash: {}", cashedCLient);
            return Optional.ofNullable(cashedCLient);
        } else {
            return transactionManager.doInReadOnlyTransaction(session -> {
                var clientOptional = clientDataTemplate.findById(session, id);
                clientOptional.ifPresent(client -> {
                    clientMyCache.put(client.getId(), client);
                    log.info("added to cash client");
                });
                log.info("client from database: {}", clientOptional);
                return clientOptional;
            });
        }
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            for (Client client : clientList) {
                clientMyCache.put(client.getId(), client);
            }
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }
}
