(ns gilded-rose.inventory
  (:require [gilded-rose.item :as item]))

(defn update-inventory [items]
  (map item/update-item items))
