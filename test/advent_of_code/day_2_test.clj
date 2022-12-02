(ns advent-of-code.day-2-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code.day-2 :refer :all]))

(def test-input
  "A Y
B X
C Z")

(deftest part-1-test
  (testing "Parse input"
    (is (= [["A" "Y"]
            ["B" "X"]
            ["C" "Z"]]
           (parse-input test-input))))
  (testing "Calculate round"
    (is (= 8
           (calc-round ["A" "Y"])))
    (is (= 1
           (calc-round ["B" "X"])))
    (is (= 6
           (calc-round ["C" "Z"]))))
      (testing "Calculate the total score"
        (is (= 15
               (part-1 test-input))))
  )

(deftest part-2-test
      (testing "Calculate round"
        (is (= 4
               (calc-round-2 ["A" "Y"])))
        (is (= 1
               (calc-round-2 ["B" "X"])))
        (is (= 7
               (calc-round-2 ["C" "Z"]))))
  (testing "Calculate total"
    (is (= 12
           (part-2 test-input)))))
