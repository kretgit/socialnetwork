insert into friends (first_friend_id,
                     second_friend_id,
                     created)
    VALUE (
           :first_friend_id,
           :second_friend_id,
           now())