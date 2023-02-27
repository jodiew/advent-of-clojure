(ns advent-of-clojure.day-12-test 
  (:require [clojure.test :refer [deftest is testing]]
            [advent-of-clojure.day-12 :refer :all]))

(def test-input
  "Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi
")

(deftest part-1-test
  (testing "Part 1" 
    (is (= 31
           (part-1 test-input)))))

(deftest part-2-test
  (testing "Part 2"
    (is (= 29
           (part-2 test-input)))))