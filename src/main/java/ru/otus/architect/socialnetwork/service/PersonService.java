package ru.otus.architect.socialnetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import ru.otus.architect.socialnetwork.controller.Controller;
import ru.otus.architect.socialnetwork.dao.PersonDao;
import ru.otus.architect.socialnetwork.exception.CommonException;
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
        Person person = getPersonById(personId);
        Person friend = getPersonById(friendId);

        try {
            personDao.makeFriends(person.getId(), friend.getId());
        } catch (DuplicateKeyException e) {
            throw new CommonException("you are already friends");
        }
    }

    public List<Person> getPersonFriends(String id) {
        return personDao.getPersonFriends(id);
    }
}
