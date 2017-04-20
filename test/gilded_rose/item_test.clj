(ns gilded-rose.item-test
  (:require [clojure.test :refer :all]
            [gilded-rose.item :as item :refer [item]]))

(def normal-item
  (item "+5 Dexterity Vest" 10 20))

(def sell-date-passed-item
  (item "+5 Dexterity Vest" 0 20))

(def zero-quality-item
  (item "+5 Dexterity Vest" 10 0))

(def standard-quality-aged-brie
  (item "Aged Brie" 2 0))

(def topped-quality-aged-brie
  (item "Aged Brie" 2 50))

(def sulfuras
  (item "Sulfuras, Hand Of Ragnaros" 0 80))

(def far-future-backstage-pass
  (item "Backstage passes to a TAFKAL80ETC concert" 15 20))

(def near-future-backstage-pass
  (item "Backstage passes to a TAFKAL80ETC concert" 9 20))

(def immediate-future-backstage-pass
  (item "Backstage passes to a TAFKAL80ETC concert" 3 20))

(def after-concert-backstage-pass
  (item "Backstage passes to a TAFKAL80ETC concert" 0 20))

(def conjured-item
  (item "Conjured Potion" 12 33))

(def sell-date-passed-conjured-item
  (item "Conjured Potion" 0 33))

(deftest
  about-gilded-rose-items
  (testing
    "Normal item"
    (testing
      "At the end of each day our system lowers sell-in value for an item"
      (let [sell-in (:sell-in normal-item)]
        (is (= (dec sell-in) (:sell-in (item/age normal-item))))))
    (testing
      "At the end of each day our system lowers quality value for an item"
      (let [quality (:quality normal-item)]
        (is (= (dec quality) (:quality (item/age normal-item))))))
    (testing
      "Once the sell by date has passed, quality degrades twice as fast"
      (let [quality (:quality sell-date-passed-item)]
        (is (= (- quality 2) (:quality (item/age sell-date-passed-item))))))
    (testing
      "The quality of an item is never negative"
      (is (= 0 (:quality (item/age zero-quality-item))))))
  (testing
    "Aged Brie"
    (testing
      "Increases in quality the older it gets"
      (let [quality (:quality standard-quality-aged-brie)]
        (is (= (inc quality) (:quality (item/age standard-quality-aged-brie))))))
    (testing
      "The quality is never more than 50"
      (is (= 50 (:quality (item/age topped-quality-aged-brie))))))
  (testing
    "Sulfuras"
    (testing
      "Never has to be sold"
      (let [sell-in (:sell-in sulfuras)]
        (is sell-in (:sell-in (item/age sulfuras)))))
    (testing
      "Never decreases in quality"
      (is 80 (:quality (item/age sulfuras)))))
  (testing
    "Backstage pass"
    (testing
      "Increases in quality as sell in value approaches"
      (let [quality (:quality far-future-backstage-pass)]
        (is (= (inc quality) (:quality (item/age far-future-backstage-pass))))))
    (testing
      "Quality increases by 2 when sell in is 10 days or less"
      (let [quality (:quality near-future-backstage-pass)]
        (is (= (+ quality 2) (:quality (item/age near-future-backstage-pass))))))
    (testing
      "Quality increases by 3 when sell in is 5 days or less"
      (let [quality (:quality immediate-future-backstage-pass)]
        (is (= (+ quality 3) (:quality (item/age immediate-future-backstage-pass))))))
    (testing
      "Quality drops to 0 after the concert"
      (let [quality (:quality after-concert-backstage-pass)]
        (is (= 0 (:quality (item/age after-concert-backstage-pass)))))))
  (testing
    "Conjured"
    (testing
      "Degrade in quality twice as fast as normal items"
      (let [quality (:quality conjured-item)]
        (is (= (- quality 2) (:quality (item/age conjured-item))))))
    (testing
      "Once the sell by date has passed, quality degrades twice as fast"
      (let [quality (:quality sell-date-passed-conjured-item)]
        (is (= (- quality 4) (:quality (item/age sell-date-passed-conjured-item))))))))
