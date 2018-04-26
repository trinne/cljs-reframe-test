(ns testi.views
  (:require [reagent.core :as r]
            [re-frame.core :as re-frame]
            [stylefy.core :as stylefy]
            [testi.subs :as subs]
            [testi.events :as events]
            [testi.ui-components.list-item :as list-item]))

(defn delete-icon [id]
  [:span {:on-click #(re-frame/dispatch-sync [::events/remove-task id])}
   "\u2715"])

(defn task-item [task]
  (list-item/default {:key (:id task)
                      :on-click #(re-frame/dispatch-sync [::events/toggle-task-done (:id task)])}
      [:span {:class (if (:done task) "done")} (:title task)]
      [delete-icon (:id task)]))

(defn task-list-content []
  (let [tasks (re-frame/subscribe [::subs/tasks])]
    [:section
     (if (pos? (count @tasks))
       [:ul (map task-item (vals @tasks))]
       [:p "All tasks completed!"])
     ]))

(defn task-addition-content []
  (let [val (r/atom "")
        stop #(reset! val "")
        save #(do (re-frame/dispatch-sync [::events/add-task @val])
                  (stop))]
    (fn []
      [:input {:type "text"
               :value @val
               :placeholder "Add task"
               :on-change #(reset! val (-> % .-target .-value))
               :on-key-down #(case (.-which %)
                               13 (save)
                               27 (stop)
                               nil)}])))

(defn tasks-done []
  (let [tasks (re-frame/subscribe [::subs/tasks])
        done (count (filter (comp true? :done) (vals @tasks)))
        total (count (vals @tasks))]
    (if (pos? total)
      [:span (str done "/" total)])))

(defn archive-tasks []
  [:a {:href ""
       :on-click #(do (-> % .preventDefault)
                      (re-frame/dispatch-sync [::events/archive-finished-tasks]))}
   "Archive"])

(defn main-todo []
  [:div.todo-app
   [:heading [:h1 "Tasks"]]
   [:section [task-list-content]]
   [:section [task-addition-content]]
   [:footer  [:p
              [tasks-done]
              [archive-tasks]]]])