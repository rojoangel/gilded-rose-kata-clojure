(ns gilded-rose.conjured-test
  (:require [clojure.test :refer :all]
            [gilded-rose.conjured :refer :all]
            [gilded-rose.inventory :as inventory]
            [gilded-rose.item :as item :refer [item]]))

(def conjured-item
  (conjure (item "Potion" 12 33)))

(def sell-date-passed-conjured-item
  (conjure (item "Potion" 0 33)))

(deftest
  about-conjured-item
  (testing
    "Conjured"
    (testing
      "Degrade in quality twice as fast as normal items"
      (let [quality (:quality conjured-item)]
        (is (= (- quality 2) (:quality (inventory/age conjured-item))))))
    (testing
      "Once the sell by date has passed, quality degrades twice as fast"
      (let [quality (:quality sell-date-passed-conjured-item)]
        (is (= (- quality 4) (:quality (inventory/age sell-date-passed-conjured-item))))))))