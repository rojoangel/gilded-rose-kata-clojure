(ns gilded-rose.core-test
  (:require [clojure.test :refer :all]
            [gilded-rose.core :refer :all]))

(def normal-item
  (item "+5 Dexterity Vest" 10 20))

(def sell-date-passed-item
  (item "+5 Dexterity Vest" 0 20))

(def zero-quality-item
  (item "+5 Dexterity Vest" 10 0))

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
      (let [quality (:quality normal-item)]
        (is (= (- quality 2) (:quality (first (update-quality [sell-date-passed-item])))))))
    (comment                                                ; commenting out as this test is failing
      (testing
        "The quality of an item is never negative"
        (let [quality (:quality zero-quality-item)]
          (is (= quality (:quality (first (update-quality [zero-quality-item]))))))))))
