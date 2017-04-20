(ns gilded-rose.attribute-test
  (:require [clojure.test :refer :all]
            [gilded-rose.item :refer [item]]
            [gilded-rose.attribute :refer :all]))

(def an-item
  (item "+5 Dexterity Vest" 10 20))

(deftest
  about-gilded-rose-item-attributes
  (testing
    "Quality attribute"
    (testing
      "Updates properly"
      (is (= (:quality (update-quality an-item 22)) 22)))
    (testing
      "Is never negative"
      (is (= (:quality (update-quality an-item -10)) 0)))
    (testing
      "Is never greater than 50"
      (is (= (:quality (update-quality an-item 99)) 50))))
  (testing
    "Sell-in attribute"
    (testing
      "Updates properly"
      (is (= (:sell-in (update-sell-in an-item 99)) 99)))))