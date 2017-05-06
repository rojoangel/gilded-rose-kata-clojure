(ns gilded-rose.conjured)

(defn conjure [item]
  (assoc item :conjured true))

(defn conjured? [item]
  (:conjured item))