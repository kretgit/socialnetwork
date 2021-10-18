select *
from persons
where id in (
    select second_friend_id from friends where friends.approved = true and first_friend_id = :person_id)