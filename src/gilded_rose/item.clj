(ns gilded-rose.item
  (:require [gilded-rose.attribute :as attribute]))

(def inventory
  {"+5 Dexterity Vest" :normal
   "Aged Brie" :aged-brie
   "Elixir of the Mongoose" :normal
   "Sulfuras, Hand of Ragnaros" :legendary
   "Backstage passes to a TAFKAL80ETC concert" :backstage-pass
   })

(defn- item->type [item]
  (get inventory (:name item)))

(defmulti update-quality item->type)

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
