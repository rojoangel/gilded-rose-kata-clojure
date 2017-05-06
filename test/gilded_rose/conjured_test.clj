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
        (is (= (+ quality 2) (:quality (inventory/age conjured-aged-brie))))))))