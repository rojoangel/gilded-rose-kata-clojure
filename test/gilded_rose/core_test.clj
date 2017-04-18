(ns gilded-rose.core-test
  (:require [clojure.test :refer :all]
            [gilded-rose.core :refer :all]))

(def normal-item
  (item "+5 Dexterity Vest" 10 20))

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
        (is (= (dec quality) (:quality (first (update-quality [normal-item])))))))))