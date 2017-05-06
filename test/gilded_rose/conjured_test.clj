(ns gilded-rose.conjured-test
  (:require [clojure.test :refer :all]
            [gilded-rose.conjured :refer :all]
            [gilded-rose.inventory :as inventory]))

(def normal-conjured-item
  (conjure "Potion" 12 33))

(def sell-date-passed-normal-conjured-item
  (conjure "Potion" 0 33))

(deftest
  about-conjured-item
  (testing
    "Conjured"
    (testing
      "Normal item degrades in quality twice as fast as normal items"
      (let [quality (:quality normal-conjured-item)]
        (is (= (- quality 2) (:quality (inventory/age normal-conjured-item))))))
    (testing
      "Once the sell by date has passed, normal item quality degrades twice as fast"
      (let [quality (:quality sell-date-passed-normal-conjured-item)]
        (is (= (- quality 4) (:quality (inventory/age sell-date-passed-normal-conjured-item))))))))