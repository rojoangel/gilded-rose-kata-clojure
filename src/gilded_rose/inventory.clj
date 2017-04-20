(ns gilded-rose.inventory)

(def inventory
  {"+5 Dexterity Vest" :normal
   "Aged Brie" :aged-brie
   "Elixir of the Mongoose" :normal
   "Sulfuras, Hand of Ragnaros" :legendary
   "Backstage passes to a TAFKAL80ETC concert" :backstage-pass
   "Conjured Potion" :conjured
   })

(defn item->type [item]
  (get inventory (:name item)))
