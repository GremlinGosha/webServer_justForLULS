package com.gmail.gremlin.gosha.controller;

import com.gmail.gremlin.gosha.model.Client;
import com.gmail.gremlin.gosha.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //говорит спрингу, что данный класс является REST контроллером.
//в данном классе будет реализована логика обработки клиентских запросов
public class ClientController {
    private final ClientService clientService;
    //Суть модификатора final - сделать дальнейшее изменение объекта невозможным.

    @Autowired //говорит спрингу, что в этом месте необходимо внедрить зависимость.
    //В конструктор мы передаем интерфейс ClientService
    //Реализацию данного сервиса мы пометили аннотацией @Service
    //теперь спринг сможет передать экземпляр этой реализации в конструктор контроллера.
    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }


    // @PostMapping(value = "/clients") данный метод обрабатывает POST запросы на адрес /clients
    // ResponseEntity — специальный класс для возврата ответов.
    // @RequestBody указывает, что значение client необходимо взять из тела запроса
    // почему-то с аннотацией @RequestBody выдает ошибку для запросов из Curl
    // Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported
    // Возвращаем статус 201 Created
    // создав новый объект ResponseEntity и передав в него нужное значение енума HttpStatus.


    //@PostMapping(value = "/clients")
    //public ResponseEntity<?> create(@RequestBody Client client) {
    //    clientService.create(client);
    //    return new ResponseEntity<>(HttpStatus.CREATED);
    //}
    @PostMapping(value = "/clients")
    public ResponseEntity<?> create(@RequestBody Client client) {
        System.out.println("WARNING!!! @PostMapping(value = \"/clients\")");
        System.out.println("Name:    " + client.getName());
        System.out.println("Email:   " + client.getEmail());
        System.out.println("Phone:   " + client.getPhone());
        clientService.create(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping(value = "/clients")
    //@GetMapping(value = "/clients") данный метод обрабатывает GET запросы на адрес /clients
    //возвращаем ResponseEntity<List<Client>>, только в этот раз, помимо HTTP статуса,
    //мы вернем еще и тело ответа, в котором будет список клиентов.
    //В REST контроллерах спринга все POJO объекты, а также коллекции POJO объектов,
    //которые возвращаются в качестве тел ответов, автоматически сериализуются в JSON,
    // если явно не указано иное.
    public ResponseEntity<List<Client>> read() {
        final List<Client> clients = clientService.readAll();

        return clients != null && !clients.isEmpty()
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/clients/{id}")
    //Данный метод будет принимать запросы на uri вида /clients/{id},
    //где вместо {id} может быть любое численное значение.
    //Данное значение, впоследствии, передается переменной int id — параметру метода
    //с помощью аннотации @PathVariable(name = "id").
    public ResponseEntity<Client> read(@PathVariable(name = "id") int id) {
        System.out.println("WARNING!!! @GetMapping(value = \"/clients/id\")");
        final Client client = clientService.read(id);

        return client != null
                ? new ResponseEntity<>(client,HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/clients/{id}")
    //этот метод обрабатывает PUT запросы
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody Client client) {
    //public ResponseEntity<?> update(@PathVariable(name = "id") int id, Client client) {
        final boolean updated = clientService.update(client, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/clients/{id}")
    //этот метод обрабатывает DELETE запросы
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = clientService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
