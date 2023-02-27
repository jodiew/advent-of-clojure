(ns advent-of-clojure.day-2 
  (:require [clojure.string :as string]))

;; --- Day 2: Rock Paper Scissors ---

;; Part 1
(defn parse-input [input]
  (->> input
       string/split-lines
       (map #(string/split % #" "))))

(def shape-score
  {"X" 1 ; Rock
   "Y" 2 ; Paper
   "Z" 3 ; Scissors
   })

(def outcome-score
  {["A" "Z"] 0
   ["A" "X"] 3
   ["A" "Y"] 6
   ["B" "Z"] 6
   ["B" "X"] 0
   ["B" "Y"] 3
   ["C" "Z"] 3
   ["C" "X"] 6
   ["C" "Y"] 0})

(defn calc-round [[opp you]]
  (+ (shape-score you)
     (outcome-score [opp you])))

(defn part-1 [input]
  (->> input
       parse-input
       (map calc-round)
       (apply +)))

(part-1 (slurp "resources/day_2_input.txt"))
;; => 13809

;; Part 2

(def outcome-score-2
  {"X" 0
   "Y" 3
   "Z" 6})

(def shape-score-2
  {["A" "X"] 3
   ["A" "Y"] 1
   ["A" "Z"] 2
   ["B" "X"] 1
   ["B" "Y"] 2
   ["B" "Z"] 3
   ["C" "X"] 2
   ["C" "Y"] 3
   ["C" "Z"] 1})

(defn calc-round-2 [[opp you]]
  (+ (outcome-score-2 you)
     (shape-score-2 [opp you])))

(defn part-2 [input]
  (->> input
       parse-input
       (map calc-round-2)
       (apply +)))

(part-2 (slurp "resources/day_2_input.txt"))
;; => 12316
