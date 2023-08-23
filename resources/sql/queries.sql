-- :name create-gif! :<!
-- :doc inserts and returns a gif
insert into gifs(ascii, name)
values (:ascii, :name)
returning *;

-- :name get-gif-by-id :? :1
-- :doc gets a single gif given its ID
select *
from gifs
where id = :id;

-- :name list-gifs
-- :doc lists all gifs
select *
from gifs;