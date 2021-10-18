select lower(persons.email) as username,
       'ROLE_USER'      as authority
from persons
where lower(email) = ?