(ns gilded-rose.aged-brie
  (:require [gilded-rose.attribute :as attribute]))

(defn update-quality [item]
  (attribute/update-quality item (inc (:quality item))))
