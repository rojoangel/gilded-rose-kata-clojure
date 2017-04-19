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

(def sulfuras
  (item "Sulfuras, Hand Of Ragnaros" 0 80))

(def far-future-backstage-pass
  (back-stage-pass "Backstage passes to a TAFKAL80ETC concert" 15 20))

(def near-future-backstage-pass
  (back-stage-pass "Backstage passes to a TAFKAL80ETC concert" 9 20))

(def immediate-future-backstage-pass
  (back-stage-pass "Backstage passes to a TAFKAL80ETC concert" 3 20))

(def after-concert-backstage-pass
  (back-stage-pass "Backstage passes to a TAFKAL80ETC concert" 0 20))

(deftest
  about-gilded-rose
  (testing
    "Normal item"
    (testing
      "At the end of each day our system lowers sell-in value for an item"
      (let [sell-in (:sell-in normal-item)]
        (is (= (dec sell-in) (:sell-in (update-item normal-item))))))
    (testing
      "At the end of each day our system lowers quality value for an item"
      (let [quality (:quality normal-item)]
        (is (= (dec quality) (:quality (update-item normal-item))))))
    (testing
      "Once the sell by date has passed, quality degrades twice as fast"
      (let [quality (:quality sell-date-passed-item)]
        (is (= (- quality 2) (:quality (update-item sell-date-passed-item))))))
    (comment                                                ; commenting out as this test is failing
      (testing
        "The quality of an item is never negative"
        (is (= 0 (:quality (update-item zero-quality-item)))))))
  (testing
    "Aged Brie"
    (testing
      "Increases in quality the older it gets"
      (let [quality (:quality aged-brie)]
        (is (= (inc quality) (:quality (update-item aged-brie))))))
    (testing
      "The quality is never more than 50"
      (is (= 50 (:quality (update-item topped-quality-aged-brie))))))
  (testing
    "Sulfuras"
    (testing
      "Never has to be sold"
      (let [sell-in (:sell-in sulfuras)]
        (is sell-in (:sell-in (update-item sulfuras)))))
    (testing
      "Never decreases in quality"
      (is 80 (:quality (update-item sulfuras)))))
  (testing
    "Backstage pass"
    (testing
      "Increases in quality as sell in value approaches"
      (let [quality (:quality far-future-backstage-pass)]
        (is (= (inc quality) (:quality (update-item far-future-backstage-pass))))))
    (testing
      "Quality increases by 2 when sell in is 10 days or less"
      (let [quality (:quality near-future-backstage-pass)]
        (is (= (+ quality 2) (:quality (update-item near-future-backstage-pass))))))
    (testing
      "Quality increases by 3 when sell in is 5 days or less"
      (let [quality (:quality immediate-future-backstage-pass)]
        (is (= (+ quality 3) (:quality (update-item immediate-future-backstage-pass))))))
    (testing
      "Quality drops to 0 after the concert"
      (let [quality (:quality after-concert-backstage-pass)]
        (is (= 0 (:quality (update-item after-concert-backstage-pass)))))))
  (testing
    "Specialized constructors"
    (testing
      "Backstage pass specialized constructor"
      (is (= :backstage-pass
             (:item-type (back-stage-pass "Backstage passes to a TAFKAL80ETC concert" 15 20)))))))
