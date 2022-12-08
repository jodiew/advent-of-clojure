(ns advent-of-code.day-7 
  (:require [clojure.string :as string]
            [clojure.walk :refer [postwalk]]))

;; --- Day 7: No Space Left On Device ---

(def input-txt (slurp "resources/day_7_input.txt"))

;; --- Part One ---

(defn calc-sizes [input]
  (:sizes (reduce (fn [acc line]
                   (cond
                     (= "$ ls" line) acc
                     (= "$ cd .." line) (update acc :path pop)
                     (string/starts-with? line "$ cd") (let [name (last (string/split line #" "))]
                                                         (update acc :path conj name))
                     (string/starts-with? line "dir ") (let [name (last (string/split line #" "))]
                                                         (assoc-in acc [:sizes (conj (:path acc) name)] 0))
                     :else (let [[size _] (string/split line #" ")
                                 size (parse-long size)]
                             (loop [a acc
                                    p (:path acc)]
                               (if (seq p)
                                 (recur (update-in a [:sizes p] + size)
                                        (pop p))
                                 a)))))
                 {:path []
                  :sizes {["/"] 0}}
                 (string/split-lines input))))

(defn part-1 [input]
  (->> input
       calc-sizes
       vals
       (filter #(<= % 100000))
       (apply +)))

(comment
  (part-1 input-txt)
  ;; => 1367870
  )

;; --- Part Two ---

(defn part-2 [input]
  (let [dir-sizes (->> input
                       calc-sizes
                       vals
                       sort)
        outermost (last dir-sizes)
        unused (- 70000000 outermost)
        required (- 30000000 unused)]
    (first (drop-while #(< % required) dir-sizes))))

(comment
  (part-2 input-txt)
  ;; => 549173
  )
