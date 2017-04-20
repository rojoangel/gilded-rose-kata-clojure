(ns gilded-rose.core)

(defn update-attribute-value [item attribute value]
  (merge item {attribute value}))

(defn update-quality-value [item value]
  (update-attribute-value item :quality value))

(defn update-sell-in-value [item value]
  (update-attribute-value item :sell-in value))

(defmulti update-quality :item-type)

(defmethod update-quality :backstage-pass [item]
  (if (< (:sell-in item) 0)
    (update-quality-value item 0)
    (if (< (:sell-in item) 5)
      (update-quality-value item (inc (inc (inc (:quality item)))))
      (if (< (:sell-in item) 10)
        (update-quality-value item (inc (inc (:quality item))))
        (if (< (:quality item) 50)
          (update-quality-value item (inc (:quality item)))
          item)))))

(defmethod update-quality :aged-brie [item]
  (if (< (:quality item) 50)
    (update-quality-value item (inc (:quality item)))
    item))

(defmethod update-quality :legendary [item]
  item)

(defmethod update-quality :default [item]
  (let [new-quality (if (< (:sell-in item) 0)
                       (- (:quality item) 2)
                       (dec (:quality item)))]
    (if (< new-quality 0)
      (update-quality-value item 0)
      (update-quality-value item new-quality))))

(defmulti update-sell-in :item-type)

(defmethod update-sell-in :legendary [item]
  item)

(defmethod update-sell-in :default [item]
  (update-sell-in-value item (dec (:sell-in item))))

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
