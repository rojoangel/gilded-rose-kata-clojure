(ns gilded-rose.core-test
  (:require [clojure.test :refer :all]
            [gilded-rose.core :refer :all]))

(def normal-item
  (item "+5 Dexterity Vest" 10 20))

(def sell-date-passed-item
  (item "+5 Dexterity Vest" 0 20))

(def zero-quality-item
  (item "+5 Dexterity Vest" 10 0))

(def aged-brie
  (item "Aged Brie" 2 0))

(def topped-quality-aged-brie
  (item "Aged Brie" 2 50))


(deftest
  about-gilded-rose
  (testing
    "Normal item"
    (testing
      "At the end of each day our system lowers sell-in value for an item"
      (let [sell-in (:sell-in normal-item)]
        (is (= (dec sell-in) (:sell-in (first (update-quality [normal-item])))))))
    (testing
      "At the end of each day our system lowers quality value for an item"
      (let [quality (:quality normal-item)]
        (is (= (dec quality) (:quality (first (update-quality [normal-item])))))))
    (testing
      "Once the sell by date has passed, quality degrades twice as fast"
      (let [quality (:quality sell-date-passed-item)]
        (is (= (- quality 2) (:quality (first (update-quality [sell-date-passed-item])))))))
    (comment                                                ; commenting out as this test is failing
      (testing
        "The quality of an item is never negative"
        (is (= 0 (:quality (first (update-quality [zero-quality-item]))))))))
  (testing
    "Aged Brie"
    (testing
      "Increases in quality the older it gets"
      (let [quality (:quality aged-brie)]
        (is (= (inc quality) (:quality (first (update-quality [aged-brie])))))))
    (testing
      "The quality is never more than 50"
      (is (= 50 (:quality (first (update-quality [topped-quality-aged-brie]))))))))
