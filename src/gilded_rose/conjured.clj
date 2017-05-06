(ns gilded-rose.conjured
  (require [gilded-rose.item :as item :refer [item]]))

(defn conjure [item-name, sell-in, quality]
  (assoc (item item-name, sell-in, quality) :conjured true))

(defn conjured? [item]
  (:conjured item))

