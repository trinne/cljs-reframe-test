(ns testi.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [testi.events :as events]
            [testi.views :as views]
            [testi.config :as config]
            [stylefy.core :as stylefy]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-todo]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (stylefy/init)
  (mount-root))