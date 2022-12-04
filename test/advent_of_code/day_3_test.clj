(ns advent-of-code.day-3-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code.day-3 :refer :all]))

(def test-input
  "vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw")

(deftest part-1-test
      (testing "parse input"
        (is (= [[(seq "vJrwpWtwJgWr") (seq "hcsFMMfFFhFp")]
                [(seq "jqHRNqRjqzjGDLGL") (seq "rsFMfFZSrLrFZsSL")]
                [(seq "PmmdzqPrV") (seq "vPwwTWBwg")]
                [(seq "wMqvLMZHhHMvwLH") (seq "jbvcjnnSBnvTQFn")]
                [(seq "ttgJtRGJ") (seq "QctTZtZT")]
                [(seq "CrZsJsPPZsGz") (seq "wwsLwLmpwMDw")]]
               (parse-input test-input))))
  (testing "find duplicate"
    (is (= \p
           (find-dup [(seq "vJrwpWtwJgWr") (seq "hcsFMMfFFhFp")])))
    (is (= \L
           (find-dup [(seq "jqHRNqRjqzjGDLGL") (seq "rsFMfFZSrLrFZsSL")]))))
  (testing "character to integer"
    (is (= 1
           (char->int \a)))
    (is (= 26
           (char->int \z)))
    (is (= 27
           (char->int \A)))
    (is (= 52
           (char->int \Z))))
  (testing "part 1"
    (is (= 157
           (part-1 test-input)))))

(deftest part-2-test
  (testing "part 2"
    (is (= 70
           (part-2 test-input))))) 
