(ns advent-of-code.day-4 
  (:require [clojure.string :as string]))

;; --- Day 4: Camp Cleanup ---
;; Part 1

(defn parse-input [input]
  (string/split input #"[\n,]"))

(defn ->range [[a b]]
  (range (Integer/parseInt a)
         (inc (Integer/parseInt b))))

(defn fully-contains? [[a b]]
  (or (and (>= (first a) (first b))
           (<= (last a)  (last b)))
      (and (>= (first b) (first a))
           (<= (last b)  (last a)))))

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