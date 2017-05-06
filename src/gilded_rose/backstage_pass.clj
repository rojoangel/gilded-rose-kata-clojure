(ns gilded-rose.backstage-pass
  (require [gilded-rose.attribute :as attribute]))

(defn update-quality [item]
  (cond
    (< (:sell-in item) 0)
    (attribute/update-quality item 0)

    (< (:sell-in item) 5)
    (attribute/update-quality item (inc (inc (inc (:quality item)))))

    (< (:sell-in item) 10)
    (attribute/update-quality item (inc (inc (:quality item))))

    :else
    (attribute/update-quality item (inc (:quality item)))))
