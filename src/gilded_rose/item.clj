(ns gilded-rose.item
  (:require [gilded-rose.attribute :as attribute]))

(defn update-quality [item]
  (if (< (:sell-in item) 0)
    (attribute/update-quality item (dec (dec (:quality item))))
    (attribute/update-quality item (dec (:quality item)))))

(defn update-sell-in [item]
  (attribute/update-sell-in item (dec (:sell-in item))))

(defn item [item-name, sell-in, quality]
  {:name item-name, :sell-in sell-in, :quality quality})
