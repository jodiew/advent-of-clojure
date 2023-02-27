(ns advent-of-clojure.day-8 
  (:require [clojure.string :as string]))

;; --- Day 8: Treetop Tree House ---

(def puzzle-input (slurp "resources/day_8_input.txt"))

;; --- Part One ---

(defn parse-input [input]
  (->> input
       string/split-lines
       (map (comp
             vec
             (partial map parse-long)
             #(string/split % #"")))
       vec))

(defn count-edges [trees]
  (+ (* 2 (count trees))
     (* 2 (count (first trees)))
     -4))

(defn count-inner [trees]
  (count (filter (partial some empty?)
                 (for [x (range 1 (dec (count (first trees))))
                       y (range 1 (dec (count trees)))]
                   (let [tree ((trees y) x)
                         taller? #(>= % tree)
                         west (take x (trees y))
                         east (drop (inc x) (trees y))
                         col (map #(% x) trees)
                         north (take y col)
                         south (drop (inc y) col)]
                     (map (partial filter taller?) [west east north south])))))
  )

(defn part-1 [input]
  (let [parsed-input (parse-input input)]
    (+ (count-edges parsed-input)
       (count-inner parsed-input))))

(comment
  (part-1 puzzle-input)
;;   => 1794
  )

;; --- Part Two ---

(defn can-see [h xs]
  (reduce (fn [acc x]
            (if (< x h)
              (inc acc)
              (reduced (inc acc))))
          0
          xs))

(defn view-scores [trees]
  (for [y (range (count trees))]
    (for [x (range (count (first trees)))]
      (let [tree ((trees y) x)
            shorter (partial can-see tree)
            west (shorter (reverse (take x (trees y))))
            east (shorter (drop (inc x) (trees y)))
            n-s (map #(% x) trees)
            north (shorter (reverse (take y n-s)))
            south (shorter (drop (inc y) n-s))]
        (* north east south west)))))

(defn part-2 [input]
  (->> input
       parse-input
       view-scores
       flatten
       (reduce max)
       ))

(comment
  (part-2 puzzle-input)
;;   => 199272
  )
