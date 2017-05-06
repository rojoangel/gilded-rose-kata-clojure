(ns gilded-rose.aged-brie-test
  (:require [clojure.test :refer :all]
            [gilded-rose.inventory :refer :all]
            [gilded-rose.item :as item :refer [item]]))

(def standard-quality-aged-brie
  (item "Aged Brie" 2 0))

(def topped-quality-aged-brie
  (item "Aged Brie" 2 50))

(deftest
  about-gilded-rose-items
  (testing
    "Aged Brie"
    (testing
      "Increases in quality the older it gets"
      (let [quality (:quality standard-quality-aged-brie)]
        (is (= (inc quality) (:quality (age standard-quality-aged-brie))))))
    (testing
      "The quality is never more than 50"
      (is (= 50 (:quality (age topped-quality-aged-brie)))))))