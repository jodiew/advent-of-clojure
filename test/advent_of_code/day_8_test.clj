(ns advent-of-code.day-8-test 
  (:require [advent-of-code.day-8 :refer [part-1 part-2]]
            [clojure.test :refer [deftest is testing]]))

(def test-input
  "30373
25512
65332
33549
35390
")

(deftest part-1-test
  (testing "part 1 test"
    (is (= 21
           (part-1 test-input)))))

(deftest part-2-test
  (testing "part 2 test"
    (is (= 8
           (part-2 test-input)))))
