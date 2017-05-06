(ns gilded-rose.item
  (:require [gilded-rose.attribute :as attribute]
            [gilded-rose.inventory :as inventory]))

(defmulti update-quality inventory/item->type)

(defmethod update-quality :backstage-pass [item]
  (cond
    (< (:sell-in item) 0)
    (attribute/update-quality item 0)

    (< (:sell-in item) 5)
    (attribute/update-quality item (inc (inc (inc (:quality item)))))

    (< (:sell-in item) 10)
    (attribute/update-quality item (inc (inc (:quality item))))

    :else
    (attribute/update-quality item (inc (:quality item)))))

(defmethod update-quality :aged-brie [item]
  (attribute/update-quality item (inc (:quality item))))

(defmethod update-quality :legendary [item]
  item)

(defmethod update-quality :conjured [item]
  (if (< (:sell-in item) 0)
    (attribute/update-quality item (dec (dec (dec (dec (:quality item))))))
    (attribute/update-quality item (dec (dec (:quality item))))))

(defmethod update-quality :default [item]
  (if (< (:sell-in item) 0)
    (attribute/update-quality item (dec (dec (:quality item))))
    (attribute/update-quality item (dec (:quality item)))))

(defmulti update-sell-in inventory/item->type)

(defmethod update-sell-in :legendary [item]
  item)

(defmethod update-sell-in :default [item]
  (attribute/update-sell-in item (dec (:sell-in item))))

(def age
  (comp update-quality update-sell-in))

(defn item [item-name, sell-in, quality]
  {:name item-name, :sell-in sell-in, :quality quality})

(defn conjure [item]
  (assoc item :conjured true))