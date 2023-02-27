(ns advent-of-clojure.day-1-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-clojure.day-1 :refer [parse-input sum-calories part-1 part-2]]))

(def test-input "1000
2000
3000

4000

5000
6000

7000
8000
9000

10000")

(deftest parse-input-test
      (testing "Parse the string input"
        (is (= [[1000 2000 3000]
                [4000]
                [5000 6000]
                [7000 8000 9000]
                [10000]]
               (parse-input test-input)))))

(deftest sum-calories-test
      (testing "List the total calories each elf is carrying"
        (is (= [6000 4000 11000 24000 10000]
               (sum-calories [[1000 2000 3000]
                              [4000]
                              [5000 6000]
                              [7000 8000 9000]
                              [10000]])))))

(deftest part-1-test
      (testing "Find the largest amount of calories carried by an elf"
        (is (= 24000
               (part-1 test-input)))))

(deftest part-2-test
      (testing "The number of calories carried by the top 3 elves"
        (is (= 45000
               (part-2 test-input)))))
