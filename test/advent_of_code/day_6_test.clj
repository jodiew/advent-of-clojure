(ns advent-of-code.day-6-test 
  (:require [advent-of-code.day-6 :refer [part-1 part-2]]
            [clojure.test :refer [deftest is testing]]))

(deftest part-1-test
  (testing "Part 1"
    (is (= 7
           (part-1 "mjqjpqmgbljsphdztnvjfqwrcgsmlb\n")))
    (is (= 5
           (part-1 "bvwbjplbgvbhsrlpgdmjqwftvncz\n")))
    (is (= 6
           (part-1 "nppdvjthqldpwncqszvftbrmjlhg\n")))
    (is (= 10
           (part-1 "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg\n")))
    (is (= 11
           (part-1 "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw\n")))))

(deftest part-2-test
  (testing "Part 2"
    (is (= 19
           (part-2 "mjqjpqmgbljsphdztnvjfqwrcgsmlb\n")))
    (is (= 23
           (part-2 "bvwbjplbgvbhsrlpgdmjqwftvncz\n")))
    (is (= 23
           (part-2 "nppdvjthqldpwncqszvftbrmjlhg\n")))
    (is (= 29
           (part-2 "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg\n")))
    (is (= 26
           (part-2 "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw\n")))))