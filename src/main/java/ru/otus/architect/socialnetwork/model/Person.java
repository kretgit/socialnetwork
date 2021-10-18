package ru.otus.architect.socialnetwork.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Person {

    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;
    private List<String> hobbies;
    private String city;
    private String email;

    public enum Gender {
        MALE,
        FEMALE
    }

}
