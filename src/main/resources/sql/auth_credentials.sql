select lower(persons.email) as username,
       password      as password,
       true          as enabled
from persons
where lower(email) = ?