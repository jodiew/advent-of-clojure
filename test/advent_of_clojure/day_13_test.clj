(ns advent-of-clojure.day-13-test 
  (:require [clojure.test :refer [deftest is testing]]
            [advent-of-clojure.day-13 :refer :all]))

(def test-input
  "[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]
")

(deftest part-1-test
  (testing "Part 1"
    (is (= true
           (part-1-compare [1 1 3 1 1]
                           [1 1 5 1 1])))
    (is (= true
           (part-1-compare [[1] [2 3 4]]
                           [[1] 4])))
    (is (= false
           (part-1-compare [9]
                           [[8 7 6]])))
    (is (= true
           (part-1-compare [[4 4] 4 4]
                           [[4 4] 4 4 4])))
    (is (= false
           (part-1-compare [7 7 7 7]
                           [7 7 7])))
    (is (= true
           (part-1-compare []
                           [3])))
    (is (= false
           (part-1-compare [[[]]]
                           [[]])))
    (is (= false
           (part-1-compare [1,[2,[3,[4,[5,6,7]]]],8,9]
                           [1,[2,[3,[4,[5,6,0]]]],8,9])))
    (is (= 13
           (part-1 test-input)))
    )
  )

(deftest part-2-test
  (testing "Part Two"
    (is (= 140
           (part-2 test-input)))))
