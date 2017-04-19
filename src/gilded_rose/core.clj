(ns gilded-rose.core)

(defn update-quantity [item]
  (cond

    (= (:item-type item) :back-stage-pass)
    (if (< (:sell-in item) 0)
      (merge item {:quality 0})
      (if (< (:sell-in item) 5)
        (merge item {:quality (inc (inc (inc (:quality item))))})
        (if (< (:sell-in item) 10)
          (merge item {:quality (inc (inc (:quality item)))})
          (if (< (:quality item) 50)
            (merge item {:quality (inc (:quality item))})
            item))))

    (= (:name item) "Aged Brie")
    (if (< (:quality item) 50)
      (merge item {:quality (inc (:quality item))})
      item)

    (or (= "+5 Dexterity Vest" (:name item)) (= "Elixir of the Mongoose" (:name item)))
    (if (< (:sell-in item) 0)
      (merge item {:quality (- (:quality item) 2)})
      (merge item {:quality (dec (:quality item))}))

    :else item))

(defn update-sell-in [item]
  (if (= "Sulfuras, Hand of Ragnaros" (:name item))
    item
    (merge item {:sell-in (dec (:sell-in item))})))

(def update-item
  (comp update-quantity update-sell-in))

(defn update-inventory [items]
  (map update-item items))

(defn item [item-name, sell-in, quality]
  {:name item-name, :sell-in sell-in, :quality quality})

; specialized constructors
(defn back-stage-pass [item-name sell-in quality]
   (assoc (item item-name sell-in quality) :item-type :back-stage-pass))