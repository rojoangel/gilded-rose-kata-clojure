(ns gilded-rose.core)

(defmulti update-quality :item-type)

(defmethod update-quality :backstage-pass [item]
  (if (< (:sell-in item) 0)
    (merge item {:quality 0})
    (if (< (:sell-in item) 5)
      (merge item {:quality (inc (inc (inc (:quality item))))})
      (if (< (:sell-in item) 10)
        (merge item {:quality (inc (inc (:quality item)))})
        (if (< (:quality item) 50)
          (merge item {:quality (inc (:quality item))})
          item)))))

(defmethod update-quality :aged-brie [item]
  (if (< (:quality item) 50)
    (merge item {:quality (inc (:quality item))})
    item))

(defmethod update-quality :legendary [item]
  item)

(defmethod update-quality :default [item]
  (let [new-quantity (if (< (:sell-in item) 0)
                       (- (:quality item) 2)
                       (dec (:quality item)))]
    (if (< new-quantity 0)
      (merge item {:quality 0})
      (merge item {:quality new-quantity}))))

(defmulti update-sell-in :item-type)

(defmethod update-sell-in :legendary [item]
  item)

(defmethod update-sell-in :default [item]
  (merge item {:sell-in (dec (:sell-in item))}))

(def update-item
  (comp update-quality update-sell-in))

(defn update-inventory [items]
  (map update-item items))

(defn item [item-name, sell-in, quality]
  {:name item-name, :sell-in sell-in, :quality quality})

; specialized constructors
(defn back-stage-pass [item-name sell-in quality]
  (assoc (item item-name sell-in quality) :item-type :backstage-pass))

(defn aged-brie [item-name sell-in quality]
  (assoc (item item-name sell-in quality) :item-type :aged-brie))

(defn legenday [item-name sell-in quality]
  (assoc (item item-name sell-in quality) :item-type :legendary))
