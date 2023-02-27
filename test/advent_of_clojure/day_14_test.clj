(ns advent-of-clojure.day-14-test 
  (:require [clojure.test :refer [deftest is testing]]
            [advent-of-clojure.day-14 :refer :all]))

(def test-input
  "498,4 -> 498,6 -> 496,6
503,4 -> 502,4 -> 502,9 -> 494,9
")

(deftest part-1-test
  (testing "Part One"
    (is (= 24
           (part-1 test-input)))))

(deftest part-2-test
  (testing "Part Two"
    (is (= 93
           (part-2 test-input)))))
