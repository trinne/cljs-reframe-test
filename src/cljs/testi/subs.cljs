(ns testi.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
  ::tasks
  (fn [db]
    (:tasks db)))