(ns gilded-rose.conjured
  (require [gilded-rose.item :as item :refer [item]]))

(defn conjure [item-name, sell-in, quality]
  (let [item (item item-name, sell-in, quality)]
    (assoc item :conjured true)))

(defn conjured? [item]
  (:conjured item))

