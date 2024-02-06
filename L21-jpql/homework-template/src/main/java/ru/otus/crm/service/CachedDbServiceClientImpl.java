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

public class CachedDbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(CachedDbServiceClientImpl.class);
    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final MyCache<Long, Client> clientCache;

    public CachedDbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.clientCache = new MyCache<>(1000);

        HwListener<Long, Client> listener = (key, value, action) -> log.info("Cache action: Key={}, Value={}, Action={}", key, value, action);

        clientCache.addListener(listener);
    }
    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            var savedClient = client.getId() == null ?
                    clientDataTemplate.insert(session, clientCloned) :
                    clientDataTemplate.update(session, clientCloned);

            log.info("Saved client: {}", savedClient);
            clientCache.put(savedClient.getId(), savedClient);
            log.info("Client added to cache: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        if (clientCache.exists(id)) {
            Client cachedClient = clientCache.get(id);
            log.info("Client retrieved from cache: {}", cachedClient);
            return Optional.ofNullable(cachedClient);
        } else {
            return transactionManager.doInReadOnlyTransaction(session -> {
                var clientOptional = clientDataTemplate.findById(session, id);
                clientOptional.ifPresent(client -> {
                    clientCache.put(client.getId(), client);
                    log.info("Client added to cache: {}", client);
                });
                log.info("Client: {}", clientOptional);
                return clientOptional;
            });
        }
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            clientList.forEach(client -> {
                clientCache.put(client.getId(), client);
                log.info("Client added to cache: {}", client);
            });
            log.info("ClientList: {}", clientList);
            return clientList;
        });
    }
}
