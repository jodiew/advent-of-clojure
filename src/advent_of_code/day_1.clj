(ns advent-of-code.day-1 
  (:require [clojure.string :as string]))

;; --- Day 1: Calorie Counting ---

;; Part 1

(defn parse-input [input]
  (->> input
       string/split-lines
       (map #(when (not-empty %) (Integer/parseInt %)))
       (partition-by nil?)
       (filter #(first %))))

(defn sum-calories [elves]
  (map #(reduce + %) elves))

(defn part-1 [input]
  (->> input
      parse-input
      sum-calories
      (apply max)))

(part-1 (slurp "resources/day_1_input.txt"))
;; => 70613

;; Part 2

(defn part-2 [input]
  (->> input
       parse-input
       sum-calories
       (sort >)
       (take 3)
       (reduce +)))

(part-2 (slurp "resources/day_1_input.txt"))
;; => 205805
