package ru.otus.architect.socialnetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.architect.socialnetwork.exception.CommonException;
import ru.otus.architect.socialnetwork.model.Person;
import ru.otus.architect.socialnetwork.service.PersonService;
import ru.otus.architect.socialnetwork.utils.CommonUtils;

import java.util.List;

@RestController
@Tag(name = "Контроллер для работы в социальной сети", description = "Для выполнения части операций необхдимо авторизоваться")
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

    @RequiredArgsConstructor
    @Getter
    public static final class FriendshipRq {
        @NonNull
        private final String firstFriendId;
        @NonNull
        private final String secondFriendId;
    }

    @Operation(summary = "Проверка доступности приложения")
    @GetMapping(value = "/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    @Operation(summary = "Регистрация нового пользовтеля")
    @PostMapping("/register")
    public ResponseEntity<Person> register(@RequestBody RegistrationRq rq) {
        return ResponseEntity.ok(personService.registerPerson(rq));
    }

    @Operation(summary = "Получить всех пользователей", description = "Требуется авторизация")
    @SecurityRequirement(name = "swagger")
    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getAllPersons() {
        return ResponseEntity.ok(personService.getAllPersons());
    }

    @Operation(summary = "Получить поьзователя по ID", description = "Требуется авторизация")
    @SecurityRequirement(name = "swagger")
    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable String id) {
        return ResponseEntity.ok(personService.getPersonById(id));
    }

    @Operation(summary = "Подружиться с пользователем", description = "Требуется авторизация")
    @SecurityRequirement(name = "swagger")
    @PostMapping("/persons/friends")
    public ResponseEntity makeFriends (@RequestBody FriendshipRq rq, @RequestHeader HttpHeaders httpHeaders) {
        if (rq.firstFriendId.equals(rq.secondFriendId)) {
            throw new CommonException("you can't get friendship with yourself");
        }
        String email = CommonUtils.getLoginFromHeaders(httpHeaders);
        Person person = personService.getPersonByEmail(email);
        if (!person.getId().equalsIgnoreCase(rq.firstFriendId) && !person.getId().equalsIgnoreCase(rq.secondFriendId)) {
            throw new CommonException("you can't make friends another persons. Only admin can do this");
        }
        personService.makeFriends(rq.firstFriendId, rq.secondFriendId);
        return ResponseEntity.ok("you are friends now!");
    }

    @Operation(summary = "Получить всех друзей пользователя по ID", description = "Требуется авторизация")
    @SecurityRequirement(name = "swagger")
    @GetMapping("/persons/friends/{id}")
    public ResponseEntity<List<Person>> getPersonFriends(@PathVariable String id) {
        return ResponseEntity.ok(personService.getPersonFriends(id));
    }

}
