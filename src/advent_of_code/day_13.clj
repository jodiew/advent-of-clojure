(ns advent-of-code.day-13 
  (:require [clojure.string :as string]
            ;; [clojure.edn :refer [read-string]]
            ))

;; --- Day 13: Distress Signal ---
(def puzzle-input (slurp "resources/day_13_input.txt"))

;; --- Part One ---

(defn parse-input [input]
  (->> input
       string/split-lines
       (filter not-empty)
       (map read-string)
       (partition 2)))

(defn part-1-compare [left right]
;;   (println "Compare " left " vs " right)
  (cond
    ;; both integers
    (and (int? left)
         (int? right)) (cond
                         (< left right) true
                         (> left right) false
                         (= left right) :cont)
    ;; both lists
    (and (coll? left)
         (coll? right)) (cond
                          (and (empty? left)
                               (empty? right)) :cont
                          (empty? left)        true
                          (empty? right)       false
                          :else                (condp = (part-1-compare (first left) (first right))
                                                 true  true
                                                 false false
                                                 :cont (recur (rest left) (rest right))))
    ;; one list, one int
    (and (coll? left)
         (int? right))  (recur left [right])
    (and (int? left)
         (coll? right)) (recur [left] right)
    :else "No match")
  )

(defn part-1 [input]
  (let [pairs (parse-input input)
        check (map part-1-compare (map first pairs) (map second pairs))]
    (reduce-kv (fn [acc i v]
                 (if v
                   (+ acc (inc i))
                   acc))
               0
               (vec check))
    ))

(comment
  (part-1 puzzle-input)
;;   => 5340
  )

;; --- Part Two ---

(defn part-2 [input]
  (let [parsed  (->> input
                     string/split-lines
                     (filter not-empty)
                     (map read-string))
        sorted-packets (vec (sort part-1-compare (conj parsed [[2]] [[6]])))]
    (reduce-kv (fn [acc i v]
                 (condp = v
                   [[2]] (* acc (inc i))
                   [[6]] (* acc (inc i))
                   acc))
               1
               sorted-packets)))

(comment
  (part-2 puzzle-input)
;;   => 21276
  )