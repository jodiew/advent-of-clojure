(ns advent-of-clojure.day-7-test 
  (:require [advent-of-clojure.day-7 :refer [calc-sizes part-1 part-2]]
            [clojure.test :refer [deftest is testing]]))

(def test-input
  "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k
")

(deftest part-1-test
  (testing "part 1"
    (is (= {["/"] 48381165
            ["/" "a"] 94853
            ["/" "a" "e"] 584
            ["/" "d"] 24933642}
           (calc-sizes test-input)))
    (is (= 95437
           (part-1 test-input)))))

(deftest part-2-test
  (testing "part 2"
    (is (= 24933642
           (part-2 test-input)))))
