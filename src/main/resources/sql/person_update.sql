update persons
set first_name = :first_name,
    last_name = :last_name,
    email = :email,
    city = :city,
    gender = :gender,
    hobbies = :hobbies,
    age = :age,
    updated = now()
where id = :person_id