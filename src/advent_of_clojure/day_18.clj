(ns advent-of-clojure.day-18
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.set :as set]))

;; --- Day 18: Boiling Boulders ---
(def puzzle-input (slurp (io/resource "day_18_input.txt")))

;; --- Part One ---

(def parse-droplet
  (comp vec
        (partial map parse-long)
        #(string/split % #",")))

(defn parse-droplets [input]
  (->> input
       string/split-lines
       (map parse-droplet)
       set))

(defn adjacent [[x y z]]
  #{[(dec x) y z]
    [(inc x) y z]
    [x (dec y) z]
    [x (inc y) z]
    [x y (dec z)]
    [x y (inc z)]})

(defn exposed [droplets droplet]
  (count (set/difference (adjacent droplet) droplets)))

(defn part-1 [input]
  (let [droplets (parse-droplets input)]
    (->> droplets
         (map (partial exposed droplets))
         (apply +))))

(comment
  (parse-droplets "1,1,1\n2,1,1\n")
  ;; => #{[1 1 1] [2 1 1]}

  (adjacent [1 1 1])
  ;; => #{[0 1 1] [1 2 1] [2 1 1] [1 1 0] [1 1 2] [1 0 1]}

  (exposed #{[1 1 1] [2 1 1]} [1 1 1])
  ;; => 5

  (part-1 "1,1,1\n2,1,1\n")
  ;; => 10

  (part-1 puzzle-input)
  ;; => 4536

  )