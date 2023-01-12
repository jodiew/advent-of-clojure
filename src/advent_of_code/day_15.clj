(ns advent-of-code.day-15 
  (:require [clojure.string :as string]))

;; --- Day 15: Beacon Exclusion Zone ---

(def puzzle-input (slurp "resources/day_15_input.txt"))

;; --- Part One ---

(def parse-numbers (comp
                    (partial map parse-long)
                    (partial re-seq #"-?\d+")))

(defn consolidate-ranges [acc [a b]]
  (let [prev (first acc)
        [x y] prev]
    (cond
      (nil? prev) (conj acc [a b])
      (> a y)     (conj acc [a b])
      (> b y)     (-> acc
                      (disj prev)
                      (conj [x b]))
      :else acc)))

(defn part-1 [y-final input]
  (as-> input $
    (string/split-lines $)
    (reduce (fn [acc line]
              (let [[x y bx by] (parse-numbers line)
                    man-dist (+ (abs (- x bx))
                                (abs (- y by)))
                    covered (first (for [y-off (range (- man-dist) (inc man-dist))
                                         :when (= y-final (+ y y-off))]
                                     (let [x-off (- man-dist (abs y-off))]
                                       [(- x x-off) (+ x x-off)])))]
            ;;   (println covered)
                (-> acc
                    (update :sensors conj [x y])
                    (update :beacons conj [bx by])
                    (update :covered conj covered))))
            {:sensors #{}
             :beacons #{}
             :covered []}
            $)
    (:covered $)
    (filter not-empty $)
    (sort $)
    (reduce consolidate-ranges
            #{}
            $)
    (reduce (fn [acc [a b]]
              (+ acc (- b a)))
            0
            $)
    ))

(comment
  (part-1 2000000 puzzle-input)
;;   => 5688618
  )
