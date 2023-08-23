create table if not exists gifs
(
  id         serial primary key,
  ascii      jsonb                     not null,
  name       text                      not null,
  created_at timestamptz default now() not null
);