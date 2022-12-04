(ns advent-of-code.day-4-test
  (:require [clojure.test :refer [deftest testing is]]
            
            [advent-of-code.day-4 :refer :all]))

(def test-input
  "2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8")

(deftest part-1-test
      (testing "number of containing pairs"
        (is (= 2
               (part-1 test-input)))))

(deftest part-2-test
  (testing "number of overlaping pairs"
    (is (= 4
           (part-2 test-input)))))
