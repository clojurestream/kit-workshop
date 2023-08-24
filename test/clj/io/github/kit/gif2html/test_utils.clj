(ns io.github.kit.gif2html.test-utils
  (:require
    [io.github.kit.gif2html.core :as core]
    [integrant.repl.state :as state]
    [next.jdbc :as jdbc]
    [migratus.core :as migratus]))

(defn system-state 
  []
  (or @core/system state/system))

(defn clear-db-and-rerun-migrations
  []
  (jdbc/execute! (:db.sql/connection (system-state))
                 ["do
$$
    declare
        row record;
    begin
        for row in select * from pg_tables where schemaname = 'public'
            loop
                execute 'drop table public.' || quote_ident(row.tablename) || ' cascade';
            end loop;
    end;
$$;"])
  (migratus/migrate (:db.sql/migrations (system-state))))

(defn system-fixture
  [f]
  (core/start-app)
  (clear-db-and-rerun-migrations)
  (f))
