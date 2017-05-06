(ns gilded-rose.legendary-test
  (:require [clojure.test :refer :all]
            [gilded-rose.inventory :refer :all]
            [gilded-rose.item :as item :refer [item]]))

(def sulfuras
  (item "Sulfuras, Hand Of Ragnaros" 0 80))

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
      (is 80 (:quality (age sulfuras))))))