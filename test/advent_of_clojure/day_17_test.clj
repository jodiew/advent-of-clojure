(ns advent-of-clojure.day-17-test 
  (:require [clojure.test :refer [deftest is testing]]
            [advent-of-clojure.day-17 :refer :all]))

(def test-input ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")

(deftest day-17-test
  (testing "Part One"
    (is (= 3068
           (part-1 test-input))))
;;   (testing "Part Two"
;;     (is (= 1514285714288
;;            (part-2 test-input))))
  )
