(ns gilded-rose.attribute)

(defn update-attribute-value [item attribute value]
  (merge item {attribute value}))

(defn update-quality-value [item value]
  (cond
    (< value 0)
    (update-attribute-value item :quality 0)

    (> value 50)
    (update-attribute-value item :quality 50)

    :else
    (update-attribute-value item :quality value)))

(defn update-sell-in-value [item value]
  (update-attribute-value item :sell-in value))
