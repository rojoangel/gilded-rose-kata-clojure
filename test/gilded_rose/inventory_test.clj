(ns gilded-rose.inventory-test
  (:require [clojure.test :refer :all]
            [gilded-rose.inventory :refer :all]
            [gilded-rose.item :as item :refer [item]]))

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

(deftest
  about-gilded-rose-items
  (testing
    "Sulfuras"
    (testing
      "Never has to be sold"
      (let [sell-in (:sell-in sulfuras)]
        (is sell-in (:sell-in (age sulfuras)))))
    (testing
      "Never decreases in quality"
      (is 80 (:quality (age sulfuras)))))
  (testing
    "Backstage pass"
    (testing
      "Increases in quality as sell in value approaches"
      (let [quality (:quality far-future-backstage-pass)]
        (is (= (inc quality) (:quality (age far-future-backstage-pass))))))
    (testing
      "Quality increases by 2 when sell in is 10 days or less"
      (let [quality (:quality near-future-backstage-pass)]
        (is (= (+ quality 2) (:quality (age near-future-backstage-pass))))))
    (testing
      "Quality increases by 3 when sell in is 5 days or less"
      (let [quality (:quality immediate-future-backstage-pass)]
        (is (= (+ quality 3) (:quality (age immediate-future-backstage-pass))))))
    (testing
      "Quality drops to 0 after the concert"
      (let [quality (:quality after-concert-backstage-pass)]
        (is (= 0 (:quality (age after-concert-backstage-pass))))))))
