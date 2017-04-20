(ns gilded-rose.attribute)

(defn- update-value [item attribute value]
  (merge item {attribute value}))

(defn update-quality [item value]
  (cond
    (< value 0)
    (update-value item :quality 0)

    (> value 50)
    (update-value item :quality 50)

    :else
    (update-value item :quality value)))

(defn update-sell-in [item value]
  (update-value item :sell-in value))
