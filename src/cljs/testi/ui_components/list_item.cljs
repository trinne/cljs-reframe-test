(ns testi.ui-components.list-item
  (:require [stylefy.core :as stylefy]
            [testi.ui-components.list-item-styles :refer [default-style]]))

(defn default [attributes & content]
      [:li (stylefy/use-style default-style attributes) content])