(ns advent-of-code.day-4 
  (:require [clojure.string :as string]))

;; --- Day 4: Camp Cleanup ---
;; Part 1

(defn parse-input [input]
  (string/split input #"[\n,]"))

(defn ->range [[a b]]
  [(Integer/parseInt a) (Integer/parseInt b)])

(defn fully-contains? [[[a b] [x y]]]
  (or (and (>= a x) (<= b y))
      (and (>= x a) (<= y b))))

(defn part-1 [input]
  (->> input
       parse-input
       (map (comp
             ->range
             #(string/split % #"-")))
       (partition 2)
       (filter fully-contains?)
       count))

(part-1 (slurp "resources/day_4_input.txt"))
;; => 540

;; Part 2

(defn overlaps? [[[a b] [x y]]]
  (or (<= x a y)
      (<= a x b)))

(defn part-2 [input]
  (->> input
       parse-input
       (map (comp
             ->range
             #(string/split % #"-")))
       (partition 2)
       (filter overlaps?)
       count
       ))

(part-2 (slurp "resources/day_4_input.txt"))
;; => 872
