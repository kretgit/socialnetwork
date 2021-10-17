package ru.otus.architect.socialnetwork.dao;

import ru.otus.architect.socialnetwork.controller.Controller;
import ru.otus.architect.socialnetwork.model.Person;

import java.util.List;

public interface PersonDao {

    Person registerPerson(Controller.RegistrationRq rq);

    Person updatePerson(Person person);

    List<Person> getAllPersons();

    Person getPersonById(int personId);

    Person getPersonByEmail(String email);
}
