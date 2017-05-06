(ns gilded-rose.conjured-test
  (:require [clojure.test :refer :all]
            [gilded-rose.conjured :refer :all]
            [gilded-rose.inventory :as inventory]))

(def conjured-normal-item
  (conjure "Potion" 12 33))

(def sell-date-passed-conjured-normal-item
  (conjure "Potion" 0 33))

(def conjured-aged-brie
  (conjure "Aged Brie" 2 0))

(def far-future-conjured-backstage-pass
  (conjure "Backstage passes to a TAFKAL80ETC concert" 15 20))

(def near-future-conjured-backstage-pass
  (conjure "Backstage passes to a TAFKAL80ETC concert" 9 20))

(def immediate-future-conjured-backstage-pass
  (conjure "Backstage passes to a TAFKAL80ETC concert" 3 20))

(def after-concert-conjured-backstage-pass
  (conjure "Backstage passes to a TAFKAL80ETC concert" 0 20))


(deftest
  about-conjured-item
  (testing
    "Conjured Normal item"
    (testing
      "Normal item degrades in quality twice as fast as normal items"
      (let [quality (:quality conjured-normal-item)]
        (is (= (- quality 2) (:quality (inventory/age conjured-normal-item))))))
    (testing
      "Once the sell by date has passed, normal item quality degrades twice as fast"
      (let [quality (:quality sell-date-passed-conjured-normal-item)]
        (is (= (- quality 4) (:quality (inventory/age sell-date-passed-conjured-normal-item)))))))
  (testing
    "Conjured Aged Brie"
    (testing
      "Aged Brie increases in quality twice as fast as normal items"
      (let [quality (:quality conjured-aged-brie)]
        (is (= (+ quality 2) (:quality (inventory/age conjured-aged-brie)))))))
  (testing
    "Conjured Backstage pass"
    (testing
      "Increases in quality twice as fast as sell in value approaches"
      (let [quality (:quality far-future-conjured-backstage-pass)]
        (is (= (+ quality 2) (:quality (inventory/age far-future-conjured-backstage-pass))))))
    (testing
      "Quality increases by 2 * 2 when sell in is 10 days or less"
      (let [quality (:quality near-future-conjured-backstage-pass)]
        (is (= (+ quality 4) (:quality (inventory/age near-future-conjured-backstage-pass))))))
    (testing
      "Quality increases by 3 * 2 when sell in is 5 days or less"
      (let [quality (:quality immediate-future-conjured-backstage-pass)]
        (is (= (+ quality 6) (:quality (inventory/age immediate-future-conjured-backstage-pass))))))
    (testing
      "Quality drops to 0 after the concert"
      (let [quality (:quality after-concert-conjured-backstage-pass)]
        (is (= 0 (:quality (inventory/age after-concert-conjured-backstage-pass))))))))