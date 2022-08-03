package com.gmail.gremlin.gosha.service;

import com.gmail.gremlin.gosha.model.Client;

import java.util.List;

public interface ClientService {
    void create(Client client); //создание нового клиента
    List<Client> readAll(); //возвращает список всех клиентов
    Client read(int id); //возвращает клиента по его Id
    boolean update(Client client, int id); //обновляет клиента с заданным Id
    boolean delete(int id); //удаляет клиента с указанным Id
}
