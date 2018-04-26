(ns testi.events
  (:require [re-frame.core :as re-frame]
            [testi.db :as db]))

(re-frame/reg-event-db
  ::initialize-db
  (fn  [_ _]
    db/task-db))

(re-frame/reg-event-fx
  ::add-task
  (fn [{:keys [db]} [_ task]]
    {:db (assoc
           db
           :tasks (assoc
                    (:tasks db)
                    (keyword (str (swap! db/task-id inc))) {:id    @db/task-id
                                                            :title task
                                                            :done  false}))}))

(re-frame/reg-event-fx
  ::remove-task
  (fn [{:keys [db]} [_ id]]
    {:db (assoc
           db
           :tasks (dissoc
                    (:tasks db)
                    (keyword (str id))))}))

(re-frame/reg-event-fx
  ::toggle-task-done
  (fn [{:keys [db]} [_ id]]
    (let [toggle (not (:done ((keyword (str id)) (:tasks db))))]
      (if(contains? (:tasks db) (keyword (str id)))
        {:db (assoc-in
               db
               [:tasks (keyword (str id)) :done] toggle)}))))

(re-frame/reg-event-fx
  ::archive-finished-tasks
  (fn [{:keys [db]} _]
    (let [notFinished (apply hash-map (flatten (filter (comp false? :done val) (:tasks db))))]
      {:db (assoc
             db
             :tasks notFinished)})))