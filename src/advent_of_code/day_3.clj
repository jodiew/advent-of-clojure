(ns advent-of-code.day-3 
  (:require [clojure.set :as set]
            [clojure.string :as string]))

;; --- Day 3: Rucksack Reorganization ---
;; Part 1

(defn parse-input [input]
  (->> input
       string/split-lines
       (map #(split-at (/ (count %) 2) %))))

(defn find-dup [[a b]]
  (first (set/intersection (set a) (set b))))

(defn char->int [x]
  (if (< (int x) (int \a))
    (- (int x) 38)
    (- (inc (int x)) (int \a))))

(defn part-1 [input]
  (->> input
       parse-input
       (map find-dup)
       (map char->int)
       (apply +)))

(part-1 (slurp "resources/day_3_input.txt"))
;; => 7850

;; Part 2

(defn part-2 [input]
  (->> input
       string/split-lines
       (map set)
       (partition 3)
       (map #(first (apply set/intersection %)))
       (map char->int)
       (apply +)))

(part-2 (slurp "resources/day_3_input.txt"))
;; => 2581
