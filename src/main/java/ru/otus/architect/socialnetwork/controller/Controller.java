package ru.otus.architect.socialnetwork.controller;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.architect.socialnetwork.model.Person;
import ru.otus.architect.socialnetwork.service.PersonService;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private PersonService personService;

    @RequiredArgsConstructor
    @Getter
    public static final class RegistrationRq {
        @NonNull
        private final String firstName;
        @NonNull
        private final String lastName;
        @NonNull
        private final String email;
        @NonNull
        private final int age;
        @NonNull
        private final Person.Gender gender;
        @NonNull
        private final List<String> hobbies;
        @NonNull
        private final String city;
        @NonNull
        private final String password;
    }

    @RequestMapping(value = "/ping", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    @PostMapping("/register")
    public ResponseEntity<Person> register(@RequestBody RegistrationRq rq) {
        return ResponseEntity.ok(personService.registerPerson(rq));
    }

    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getAllPersons() {
        return ResponseEntity.ok(personService.getAllPersons());
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable int id) {
        return ResponseEntity.ok(personService.getPersonById(id));
    }

}
