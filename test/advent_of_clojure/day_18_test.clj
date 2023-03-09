(ns advent-of-clojure.day-18-test 
  (:require [clojure.test :refer [deftest is testing]]
            [advent-of-clojure.day-18 :refer :all]))

(def test-input-small
  "1,1,1\n2,1,1\n")

(def test-input-large
  "2,2,2
1,2,2
3,2,2
2,1,2
2,3,2
2,2,1
2,2,3
2,2,4
2,2,6
1,2,5
3,2,5
2,1,5
2,3,5
")

(deftest day-18-test
  (testing "Part One"
    (is (= 10
           (part-1 test-input-small)))
    (is (= 64
           (part-1 test-input-large))))
  (testing "Part Two"
    (is (= 10
           (part-2 test-input-small)))
    (is (= 58
           (part-2 test-input-large)))))
