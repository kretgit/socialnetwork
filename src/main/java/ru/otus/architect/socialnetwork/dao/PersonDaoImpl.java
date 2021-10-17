package ru.otus.architect.socialnetwork.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.otus.architect.socialnetwork.controller.Controller;
import ru.otus.architect.socialnetwork.model.Person;
import ru.otus.architect.socialnetwork.utils.CommonUtils;
import ru.otus.architect.socialnetwork.utils.Integration;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

import static ru.otus.architect.socialnetwork.utils.CommonUtils.alterValue;

@Component
public class PersonDaoImpl extends NamedParameterJdbcDaoSupport implements PersonDao {

    @Autowired
    public void init(DataSource dataSource) {
        setDataSource(dataSource);
    }

    private final RowMapper<Person> PERSON_ROW_MAPPER = (rs, rowNum) ->
         Person.builder()
                 .id(rs.getInt("id"))
                 .firstName(rs.getString("first_name"))
                 .lastName(rs.getString("last_name"))
                 .gender(Person.Gender.valueOf(rs.getString("gender")))
                 .email(rs.getString("email"))
                 .city(rs.getString("city"))
                 .hobbies(Integration.listFromJson(String.class, rs.getString("hobbies")))
                 .age(rs.getInt("age"))
                 .build();

    private int getNextId() {
        return getNamedParameterJdbcTemplate().queryForObject("select count(*) + 1 as next_id from persons",
                Collections.emptyMap(), int.class);
    }

    @Override
    public Person registerPerson(Controller.RegistrationRq rq) {

        if (getPersonByEmail(rq.getEmail()) != null) {
            return null;
        }

        int personId = getNextId();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("person_id", personId);
        params.addValue("first_name", rq.getFirstName());
        params.addValue("last_name", rq.getLastName());
        params.addValue("age", rq.getAge());
        params.addValue("gender", rq.getGender().name());
        params.addValue("hobbies", Integration.toJsonCompact(rq.getHobbies()).toString());
        params.addValue("city", rq.getCity());
        params.addValue("email", rq.getEmail());
        params.addValue("password", new BCryptPasswordEncoder().encode(rq.getPassword()));
        getNamedParameterJdbcTemplate().update(CommonUtils.resourceAsString("sql/register_person.sql"), params);

        return Person.builder()
                .id(personId)
                .firstName(rq.getFirstName())
                .lastName(rq.getLastName())
                .age(rq.getAge())
                .gender(rq.getGender())
                .hobbies(rq.getHobbies())
                .city(rq.getCity())
                .email(rq.getEmail())
                .build();
    }

    @Override
    public Person updatePerson(Person person) {
        Person oldPerson = getPersonById(person.getId());

        Person newPerson = Person.builder()
                .id(person.getId())
                .firstName(alterValue(person.getFirstName(), oldPerson.getFirstName()))
                .lastName(alterValue(person.getLastName(), oldPerson.getLastName()))
                .email(alterValue(person.getEmail(), oldPerson.getEmail()))
                .city(alterValue(person.getCity(), oldPerson.getCity()))
                .hobbies(alterValue(person.getHobbies(), oldPerson.getHobbies()))
                .age(alterValue(person.getAge(), oldPerson.getAge()))
                .gender(alterValue(person.getGender(), oldPerson.getGender()))
                .build();

        savePerson(newPerson);
        return newPerson;
    }

    private void savePerson(Person person) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("first_name", person.getFirstName());
        params.addValue("last_name", person.getLastName());
        params.addValue("age", person.getAge());
        params.addValue("gender", person.getGender().name());
        params.addValue("hobbies", Integration.toJsonCompact(person.getHobbies()).toString());
        params.addValue("email", person.getEmail());
        params.addValue("city", person.getCity());
        getNamedParameterJdbcTemplate().update(CommonUtils.resourceAsString("sql/update_person.sql"), params);
    }

    @Override
    public Person getPersonById(int personId) {
        Person person = null;
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("person_id", personId);
        try {
            person = getNamedParameterJdbcTemplate().queryForObject("select * from persons where id = :person_id",
                    param, PERSON_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            e.getMessage();
        }
        return person;
    }

    @Override
    public Person getPersonByEmail(String email) {
        Person person = null;
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("email", email);
        try {
            person = getNamedParameterJdbcTemplate().queryForObject("select * from persons where email = :email",
                    param, PERSON_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            e.getMessage();
        }
        return person;
    }

    @Override
    public List<Person> getAllPersons() {
        return getNamedParameterJdbcTemplate().query("select * from persons", PERSON_ROW_MAPPER);
    }
}
