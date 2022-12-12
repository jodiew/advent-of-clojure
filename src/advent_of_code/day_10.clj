(ns advent-of-code.day-10 
  (:require [clojure.string :as string]))

;; --- Day 10: Cathode-Ray Tube ---

(def puzzle-input (slurp "resources/day_10_input.txt"))

;; --- Part One ---

(defn parse-input [input]
  (->> input
       string/split-lines
       (reduce (fn [acc inst]
                 (if (= inst "noop")
                   (conj acc 0)
                   (-> acc
                       (conj 0)
                       (conj (parse-long (last (string/split inst #" ")))))))
               [0])))

(defn calc-x [acc ops n]
  (if (< (count ops) n)
    acc
    (let [x (apply + (last acc) (take n ops))]
      (recur (conj acc x)
             (drop n ops)
             n))))

(defn part-1 [input]
  (let [ops (parse-input input)]
    (apply + (map *
                  (range 20 (count ops) 40)
                  (calc-x [(apply + 1 (take 20 ops))] (drop 20 ops) 40)))))

(comment
  (part-1 puzzle-input)
;;   => 13180
  )

;; --- Part Two ---

(defn part-2 [input]
  (let [ops (parse-input input)
        sprite-pos (vec (rest (calc-x [1] ops 1)))]
    (string/join "\n"
                 (for [r (range 6)]
                   (string/join ""
                                (for [c (range 40)]
                                  (let [cycle (+ (* r 40) c)
                                        sprite (sprite-pos cycle)]
                                    (if (< (- sprite 2) c (+ sprite 2))
                                      "#"
                                      "."))))))))

(comment
  (println (part-2 puzzle-input))
;;   => EZFCHJAB
  )
