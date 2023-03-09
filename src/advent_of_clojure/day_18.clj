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

;; --- Part Two ---

(defn get-min-boundry [droplets n]
  (->> droplets
       (map #(nth % n))
       (apply min)
       dec))

(defn get-max-boundry [droplets n]
  (->> droplets
       (map #(nth % n))
       (apply max)
       inc))

(defn get-bounds [n droplets]
  [(get-min-boundry droplets n)
   (get-max-boundry droplets n)])

(def x-bounds (partial get-bounds 0))
(def y-bounds (partial get-bounds 1))
(def z-bounds (partial get-bounds 2))

(defn in-bounds? [droplets [x y z]]
  (let [[min-x max-x] (x-bounds droplets)
        [min-y max-y] (y-bounds droplets)
        [min-z max-z] (z-bounds droplets)]
    (and (<= min-x x max-x)
         (<= min-y y max-y)
         (<= min-z z max-z))))

(defn not-filled? [filled n]
  (not (contains? filled n)))

(defn start-steam [droplets]
  (vec (map first [(x-bounds droplets)
                   (y-bounds droplets)
                   (z-bounds droplets)])))

(defn flood-fill [droplets]
  (loop [q [(start-steam droplets)]
         filled #{}]
    (let [n (peek q)]
      (if n
        (if (contains? droplets n)
          (recur (pop q) filled)
          (let [adj (->> (adjacent n)
                         (filter (partial in-bounds? droplets))
                         (filter (partial not-filled? filled)))]
            (recur (apply conj (pop q) adj) (conj filled n))))
        filled))))

(defn full-steam [droplets]
  (let [[min-x max-x] (x-bounds droplets)
        [min-y max-y] (y-bounds droplets)
        [min-z max-z] (z-bounds droplets)]
    (set (for [x (range min-x (inc max-x))
               y (range min-y (inc max-y))
               z (range min-z (inc max-z))]
           [x y z]))))

(defn part-2 [input]
;;   this is pretty slow on the full puzzle input, but it works
  (let [droplets (parse-droplets input)
        steam-droplets (set/difference (full-steam droplets)
                                       (flood-fill droplets))]
    (->> steam-droplets
         (map (partial exposed steam-droplets))
         (apply +))))

(comment
  (get-min-boundry #{[1 1 1] [2 1 1]} 0)
  ;; => 0

  (get-max-boundry #{[1 1 1] [2 1 1]} 0)
  ;; => 3

  (set/difference (full-steam #{[1 1 1] [2 1 1]})
                  (flood-fill #{[1 1 1] [2 1 1]}))
  ;; => #{[1 1 1] [2 1 1]}

  (start-steam #{[1 1 1] [2 1 1]})
  ;; => [0 0 0]

  (part-2 puzzle-input)
  ;; => 2606
  )
