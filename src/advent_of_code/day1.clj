(ns advent-of-code.day1 
  (:require [clojure.string :as string]))

;; Part 1
(def test-input
  "1000
2000
3000

4000

5000
6000

7000
8000
9000

10000")

(slurp "resources/day_1_input.txt")

(string/split test-input #"\n\n")

(string/split-lines test-input)

(map string/split-lines (string/split test-input #"\n\n"))

(apply max
       (for [elf (map string/split-lines (string/split test-input #"\n\n"))]
         (reduce + (map #(Integer/parseInt %) elf))))

(defn parse-input [input]
  (map string/split-lines (string/split input #"\n\n")))

(defn sum-calories [elves]
  (for [elf elves]
    (->> elf
         (map #(Integer/parseInt %))
         (reduce +))))

(->> test-input
     parse-input
     sum-calories
     (apply max))

(->> (slurp "resources/day_1_input.txt")
     parse-input
     sum-calories
     (apply max))

;; Part 2

(->> test-input
     parse-input
     sum-calories
     (sort >)
     (take 3)
     (reduce +))

(->> (slurp "resources/day_1_input.txt")
     parse-input
     sum-calories
     (sort >)
     (take 3)
     (reduce +))
