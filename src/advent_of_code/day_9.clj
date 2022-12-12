(ns advent-of-code.day-9 
  (:require [clojure.string :as string]
            [clojure.core.match :refer [match]]))

;; --- Day 9: Rope Bridge ---
(def puzzle-input (slurp "resources/day_9_input.txt"))

;; --- Part One ---

(defn move-head [curr move]
  (match move
    "L" (update curr 0 dec)
    "R" (update curr 0 inc)
    "U" (update curr 1 inc)
    "D" (update curr 1 dec)))

(defn move-tail [t-curr h-curr]
  (let [[x y] (map - h-curr t-curr)]
    ;; (println "h: " h-curr " t: " t-curr " diff: " [x y])
    (match [x y]
      [2 0]   (update t-curr 0 inc)
      [0 2]   (update t-curr 1 inc)
      [-2 0]  (update t-curr 0 dec)
      [0 -2]  (update t-curr 1 dec)
      [2 2]   (-> t-curr
                  (update 0 inc)
                  (update 1 inc))
      [-2 -2] (-> t-curr
                  (update 0 dec)
                  (update 1 dec))
      [2 -2] (-> t-curr
                 (update 0 inc)
                 (update 1 dec))
      [-2 2] (-> t-curr
                 (update 0 dec)
                 (update 1 inc))
      [1 2]   (-> t-curr
                  (update 0 inc)
                  (update 1 inc))
      [2 1]   (-> t-curr
                  (update 0 inc)
                  (update 1 inc))
      [-2 1]  (-> t-curr
                  (update 0 dec)
                  (update 1 inc))
      [-1 2]  (-> t-curr
                  (update 0 dec)
                  (update 1 inc))
      [2 -1]  (-> t-curr
                  (update 0 inc)
                  (update 1 dec))
      [1 -2]  (-> t-curr
                  (update 0 inc)
                  (update 1 dec))
      [-2 -1] (-> t-curr
                  (update 0 dec)
                  (update 1 dec))
      [-1 -2] (-> t-curr
                  (update 0 dec)
                  (update 1 dec))
      [0 1] t-curr
      [1 0] t-curr
      [0 0] t-curr
      [1 1] t-curr
      [0 -1] t-curr
      [-1 0] t-curr
      [-1 -1] t-curr
      [1 -1] t-curr
      [-1 1] t-curr
      :else (println "h:" h-curr " t: " t-curr " diff: " [x y]))))

(defn print-state [h t]
  (println
   (string/join "\n"
                (for [y (reverse (range 6))]
                  (string/join " "
                               (for [x (range 6)]
                                 (cond
                                   (= [x y] h)     "H"
                                   (= [x y] t)     "T"
                                   (= [x y] [0 0]) "s"
                                   :else           ".")))))))

(defn parse-input [txt]
  (->> txt
       string/split-lines
       (map (comp
             #(update % 1 parse-long)
             #(string/split % #" ")))
       (mapcat (fn [[d n]] (take n (repeat d))))))

(defn part-1 [input]
  (let [h-moves (parse-input input)]
    (count (:t-pos (reduce (fn [acc move]
                             (let [h-pos (move-head (:h-curr acc) move)
                                   t-pos (move-tail (:t-curr acc) h-pos)]
                            ;;    (println "==" move "==")
                            ;;    (print-state h-pos t-pos)
                               (-> acc
                                   (assoc :h-curr h-pos)
                                   (assoc :t-curr t-pos)
                                   (update :t-pos conj t-pos))))
                           {:h-curr [0 0]
                            :t-curr [0 0]
                            :t-pos #{[0 0]}}
                           h-moves)))))

(comment
  (part-1 puzzle-input)
;;   => 6563
  )

;; --- Part Two ---

(defn print-state-2 [knots]
  (string/join "\n"
               (for [y (reverse (range -5 16))]
                 (string/join " "
                              (for [x (range -11 15)]
                                (cond
                                  (= [x y] (first knots))       "H"
                                  (contains? (set knots) [x y]) (.indexOf knots [x y])
                                  (= [x y] [0 0])               "s"
                                  :else                         "."))))))

(defn part-2 [input] 
  (count (:t-pos (reduce (fn [acc move]
                           (let [h-moved (update-in acc [:curr 0] move-head move)
                                 t-moved (loop [tails h-moved
                                                i 1]
                                           (if (< i 10)
                                             (recur (update-in tails [:curr i] move-tail ((:curr tails) (dec i)))
                                                    (inc i))
                                             tails))]
            ;;   (println "==" move "==")
            ;;   (println (print-state-2 (:curr t-moved)))
                             (update t-moved :t-pos conj (last (:curr t-moved)))
                             )
                           )
                         {:curr (vec (take 10 (repeat [0 0])))
                          :t-pos #{[0 0]}}
                         (parse-input input)))))

(comment
  (part-2 puzzle-input)
;;   => 2653
  )
