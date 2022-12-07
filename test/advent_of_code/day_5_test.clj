(ns advent-of-code.day-5-test
  (:require [advent-of-code.day-5 :refer [do-move do-move-2 move-a-crate
                                          parse-input part-1 part-2]]
            [clojure.test :refer [deftest is testing]]))

(def test-input
  "    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2")

(deftest part-1-test
  (testing "parse the input"
    (is (= {:stacks [[\N \Z]
                     [\D \C \M]
                     [\P]]
            :moves [{:move 1 :from 2 :to 1}
                    {:move 3 :from 1 :to 3}
                    {:move 2 :from 2 :to 1}
                    {:move 1 :from 1 :to 2}]}
           (parse-input test-input))))
  (testing "move a crate"
    (is (= [[\D \N \Z]
            [\C \M]
            [\P]]
           (move-a-crate 2 1
                        [[\N \Z]
                         [\D \C \M]
                         [\P]])))) 
  (testing "do a move"
    (is (= [[]
            [\C \M]
            [\Z \N \D \P]]
           (do-move [[\D \N \Z]
                     [\C \M]
                     [\P]]
                     {:move 3 :from 1 :to 3})))
    (is (= [[\M \C]
            []
            [\Z \N \D \P]]
           (do-move  [[]
                      [\C \M]
                      [\Z \N \D \P]]
                     {:move 2 :from 2 :to 1}))))
  (testing "top crates"
    (is (= "CMZ"
           (part-1 test-input))))
  )

(deftest part-2-test
  (testing "do move 2"
    (is (= [[\D \N \Z]
            [\C \M]
            [\P]]
           (do-move-2 [[\N \Z]
                       [\D \C \M]
                       [\P]]
                      {:move 1 :from 2 :to 1}))))
  (testing "part 2"
    (is (= "MCD"
           (part-2 test-input)))))
