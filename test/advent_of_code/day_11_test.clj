(ns advent-of-code.day-11-test 
  (:require [clojure.test :refer [deftest is testing]]
            [advent-of-code.day-11 :refer :all]))

(def test-input
  "Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
")

(deftest part-1-test
  (testing "Part 1"
    (is (= 10605
           (part-1 test-input)))))

(deftest part-2-test
  (testing "Part 2"
    (is (= 2713310158
           (part-2 test-input)))))


