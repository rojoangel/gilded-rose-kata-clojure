(ns gilded-rose.core)

(defn update-quantity [item]
  (cond

    (= (:name item) "Backstage passes to a TAFKAL80ETC concert")
    (if (< (:sell-in item) 0)
      (merge item {:quality 0})
      (if (and (>= (:sell-in item) 5) (< (:sell-in item) 10))
        (merge item {:quality (inc (inc (:quality item)))})
        (if (and (>= (:sell-in item) 0) (< (:sell-in item) 5))
          (merge item {:quality (inc (inc (inc (:quality item))))})
          (if (< (:quality item) 50)
            (merge item {:quality (inc (:quality item))})
            item))))

    (= (:name item) "Aged Brie")
    (if (< (:quality item) 50)
      (merge item {:quality (inc (:quality item))})
      item)

    (< (:sell-in item) 0)
    (if (or (= "+5 Dexterity Vest" (:name item)) (= "Elixir of the Mongoose" (:name item)))
      (merge item {:quality (- (:quality item) 2)})
      item)

    (or (= "+5 Dexterity Vest" (:name item)) (= "Elixir of the Mongoose" (:name item)))
    (merge item {:quality (dec (:quality item))})

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
