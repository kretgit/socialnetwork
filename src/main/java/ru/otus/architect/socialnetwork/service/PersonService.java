package ru.otus.architect.socialnetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.architect.socialnetwork.controller.Controller;
import ru.otus.architect.socialnetwork.dao.PersonDao;
import ru.otus.architect.socialnetwork.model.Person;

import java.util.List;

@Component
public class PersonService {

    @Autowired
    PersonDao personDao;

    public Person registerPerson(Controller.RegistrationRq rq) {
        return personDao.registerPerson(rq);
    }

    public List<Person> getAllPersons() {
        return personDao.getAllPersons();
    }

    public Person getPersonById(String personId) {
        return personDao.getPersonById(personId);
    }

    public Person getPersonByEmail(String email) {
        return personDao.getPersonByEmail(email);
    }

    public void makeFriends(String personId, String friendId) {
        personDao.makeFriends(personId, friendId);
    }

    public List<Person> getPersonFriends(String id) {
        return personDao.getPersonFriends(id);
    }
}
