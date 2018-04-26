(ns testi.db)

(defonce task-id (atom 0))

(def task-db
  {:tasks (sorted-map)})