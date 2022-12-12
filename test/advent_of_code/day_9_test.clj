(ns advent-of-code.day-9-test 
  (:require [clojure.test :refer [deftest is testing]]
            [advent-of-code.day-9 :refer :all]))

(def test-input
  "R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2
")

(deftest part-1-test
  (testing "Part 1"
    (is (= 13
           (part-1 test-input)))))

(def test-input-2
  "R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20
")

(deftest part-2-test
  (testing "Part 2"
    (is (= 1
           (part-2 test-input)))
    (is (= 36
           (part-2 test-input-2)))))
