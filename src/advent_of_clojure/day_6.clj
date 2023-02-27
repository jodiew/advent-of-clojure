(ns advent-of-clojure.day-6 
  (:require [clojure.string :as string]))

;; --- Day 6: Tuning Trouble ---

(def puzzle-input (slurp "resources/day_6_input.txt"))

;; --- Part 1 ---

(defn solver [marker-len input]
  (->> input
       string/trim
       (partition marker-len 1)
       (take-while #(< (count (set %)) marker-len))
       count
       (+ marker-len)))

(def part-1 (partial solver 4))

(part-1 puzzle-input)
;; => 1920

;; --- Part 2 ---

(def part-2 (partial solver 14))

(part-2 puzzle-input)
;; => 2334
