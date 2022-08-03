package com.gmail.gremlin.gosha.service;

import com.gmail.gremlin.gosha.model.Client;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service //что данный класс является сервисом
//Это специальный тип классов, в котором реализуется некоторая бизнес логика приложения.
//Впоследствии, благодаря этой аннотации Spring будет предоставлять нам экземпляр данного класса
//в местах, где это, нужно с помощью Dependency Injection.
public class ClientServiceImpl implements ClientService {
    //хранилище клиентов, типа вместо БД
    private static final Map<Integer, Client> CLIENT_REPOSITORY_MAP = new HashMap<>();

    private static final AtomicInteger CLIENT_ID_HOLDER = new AtomicInteger();


    @Override //данная аннотация добавляется
    //если перегружать метод (исполнять интерфейс)
    //через выбор по горячей клавише ctrl+o
    public void create(Client client) {
        final int clientId = CLIENT_ID_HOLDER.incrementAndGet();
        client.setId(clientId);
        CLIENT_REPOSITORY_MAP.put(clientId,client);
    }

    @Override
    public List<Client> readAll() {
        return new ArrayList<>(CLIENT_REPOSITORY_MAP.values());
    }

    @Override
    public Client read(int id) {
        System.out.println("in ClientServiceImpl.read(id)");
        return CLIENT_REPOSITORY_MAP.get(id);
    }

    @Override
    public boolean update(Client client, int id) {
        if (CLIENT_REPOSITORY_MAP.containsKey(id)) {
            client.setId(id);
            CLIENT_REPOSITORY_MAP.put(id, client);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        return CLIENT_REPOSITORY_MAP.remove(id) != null;
    }
}
