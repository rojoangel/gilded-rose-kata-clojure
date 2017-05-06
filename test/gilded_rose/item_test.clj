(ns gilded-rose.item-test
  (:require [clojure.test :refer :all]
            [gilded-rose.inventory :refer :all]
            [gilded-rose.item :as item :refer [item]]))

(def normal-item
  (item "+5 Dexterity Vest" 10 20))

(def sell-date-passed-item
  (item "+5 Dexterity Vest" 0 20))

(def zero-quality-item
  (item "+5 Dexterity Vest" 10 0))

(deftest
  about-gilded-rose-items
  (testing
    "Normal item"
    (testing
      "At the end of each day our system lowers sell-in value for an item"
      (let [sell-in (:sell-in normal-item)]
        (is (= (dec sell-in) (:sell-in (age normal-item))))))
    (testing
      "At the end of each day our system lowers quality value for an item"
      (let [quality (:quality normal-item)]
        (is (= (dec quality) (:quality (age normal-item))))))
    (testing
      "Once the sell by date has passed, quality degrades twice as fast"
      (let [quality (:quality sell-date-passed-item)]
        (is (= (- quality 2) (:quality (age sell-date-passed-item))))))
    (testing
      "The quality of an item is never negative"
      (is (= 0 (:quality (age zero-quality-item)))))))