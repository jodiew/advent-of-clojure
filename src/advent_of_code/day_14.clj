(ns advent-of-code.day-14 
  (:require [clojure.string :as string]))

;; --- Day 14: Regolith Reservoir ---

(def puzzle-input (slurp "resources/day_14_input.txt"))

;; --- Part One ---

(defn parse-input [input]
  (->> input
       string/split-lines
       (map (comp
             (partial partition 2 1)
             (partial partition 2)
             (partial map parse-long)
             #(string/split % #"\D+")))
       (apply concat)))

(defn build-wall [[[x y] [X Y]]]
  (cond
    (< y Y) (for [yr (range y (inc Y))]
              [x yr])
    (< x X) (for [xr (range x (inc X))]
              [xr y])
    (> y Y) (for [yr (range y (dec Y) -1)]
              [x yr])
    (> x X) (for [xr (range x (dec X) -1)]
              [xr y])))

(defn print-cave [rocks]
  (let [xs (map first (keys rocks))
        ys (map second (keys rocks))
        min-x (apply min xs)
        max-x (apply max xs)
        max-y (apply max ys)]
    (println
     (string/join "\n"
                  (for [y (range 0 (inc max-y))]
                    (string/join " "
                                 (for [x (range min-x (inc max-x))]
                                   (condp = (get rocks [x y])
                                     :rock   "#"
                                     :source "+"
                                     :sand   "O"
                                     ".")))))))
  rocks)

(defn drop-sand [caves [x y] max-y]
  (cond
    (>= y max-y)                         :falling
    (nil? (get caves [x (inc y)]))       (recur caves [x (inc y)]       max-y)
    (nil? (get caves [(dec x) (inc y)])) (recur caves [(dec x) (inc y)] max-y)
    (nil? (get caves [(inc x) (inc y)])) (recur caves [(inc x) (inc y)] max-y)
    :else (assoc caves [x y] :sand)))

(defn fill-sand [caves n lowest-rock]
;;   (println "\n===== Fill sand: " n " =====")
  (let [dropped (drop-sand caves [500 0] lowest-rock)
        ;; _       (if (not= dropped :falling)
        ;;           (print-cave dropped)
        ;;           (println dropped))
        ]
    (if (= dropped :falling)
      n
      (recur dropped (inc n) lowest-rock))))

(defn part-1 [input]
  (let [cave-system (zipmap (->> input
                                 parse-input
                                 (map build-wall)
                                 (apply concat))
                            (repeat :rock))
        lowest-rock (apply max (map second (keys cave-system)))]
    (-> cave-system
        (assoc [500 0] :source)
        (fill-sand 0 lowest-rock))))

(comment
  (part-1 puzzle-input)
;;   => 728
  )
