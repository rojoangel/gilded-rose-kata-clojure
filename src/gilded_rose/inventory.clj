(ns gilded-rose.inventory
  (require [gilded-rose.backstage-pass :as backstage-pass]
           [gilded-rose.aged-brie :as aged-brie]
           [gilded-rose.legendary :as legendary]
           [gilded-rose.item :as item]
           [gilded-rose.conjured :as conjured]))

(def inventory
  {"+5 Dexterity Vest" :normal
   "Aged Brie" :aged-brie
   "Elixir of the Mongoose" :normal
   "Sulfuras, Hand of Ragnaros" :legendary
   "Backstage passes to a TAFKAL80ETC concert" :backstage-pass
   "Conjured Potion" :conjured
   })

(defn item->type [item]
  (get inventory (:name item)))

; update-quality multi-method
(defmulti update-quality item->type)

(defmethod update-quality :backstage-pass [item]
  (backstage-pass/update-quality item))

(defmethod update-quality :aged-brie [item]
  (aged-brie/update-quality item))

(defmethod update-quality :legendary [item]
  (legendary/update-quality item))

(defmethod update-quality :default [item]
  (item/update-quality item))

; update-sell-in multi-method
(defmulti update-sell-in item->type)

(defmethod update-sell-in :legendary [item]
  (legendary/update-sell-in item))

(defmethod update-sell-in :default [item]
  (item/update-sell-in item))

; age multi-method
(defmulti age conjured/conjured?)

(defmethod age true [item]
  ((comp update-quality update-quality update-sell-in) item))

(defmethod age :default [item]
  ((comp update-quality update-sell-in) item))
