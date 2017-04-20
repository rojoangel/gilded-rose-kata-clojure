(ns gilded-rose.item
  (:require [gilded-rose.attribute :as attribute]))

;; update-quality multi-method
(defmulti update-quality :item-type)

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

(defmethod update-quality :default [item]
  (if (< (:sell-in item) 0)
    (attribute/update-quality item (dec (dec (:quality item))))
    (attribute/update-quality item (dec (:quality item)))))

(defmulti update-sell-in :item-type)

(defmethod update-sell-in :legendary [item]
  item)

(defmethod update-sell-in :default [item]
  (attribute/update-sell-in item (dec (:sell-in item))))

(def update
  (comp update-quality update-sell-in))

(defn item [item-name, sell-in, quality]
  {:name item-name, :sell-in sell-in, :quality quality})

; specialized constructors
(defn back-stage-pass [item-name sell-in quality]
  (assoc (item item-name sell-in quality) :item-type :backstage-pass))

(defn aged-brie [item-name sell-in quality]
  (assoc (item item-name sell-in quality) :item-type :aged-brie))

(defn legendary [item-name sell-in quality]
  (assoc (item item-name sell-in quality) :item-type :legendary))
