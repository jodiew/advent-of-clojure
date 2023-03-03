(ns advent-of-clojure.day-17
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

;; --- Day 17: Pyroclastic Flow ---
(def puzzle-input (slurp (io/resource "day_17_input.txt")))

;; --- Part One ---

(defn parse-input [input]
  (->> (string/split (string/trim input) #"")
       (map #(condp = %
               ">" 1
               "<" -1
               nil))
       vec))

(defn init-chamber [input]
  {:rocks #{}
   :falling #{}
   :rock-i 0
   :jets (parse-input input)
   :jet-i 0
   :landed-rocks 0})

(defn move-rock [rock x y]
  (->> rock
       (map (fn [[rock-x rock-y]]
              [(+ rock-x x) (+ rock-y y)]))
       set))

(def rock-types [#{[3 1] [4 1] [5 1] [6 1]}

                 #{[4 3]
                   [3 2] [4 2] [5 2]
                   [4 1]}

                 #{[5 3]
                   [5 2]
                   [3 1] [4 1] [5 1]}

                 #{[3 4]
                   [3 3]
                   [3 2]
                   [3 1]}

                 #{[3 2] [4 2]
                   [3 1] [4 1]}])

(defn highest-rock [rocks]
  (if (empty? rocks)
    0
    (->> rocks
         (map second)
         (apply max))))

(defn rock-appear [chamber]
  (assoc chamber :falling (move-rock (rock-types (:rock-i chamber)) 0 (+ 3 (highest-rock (:rocks chamber))))))

(defn rock-push [chamber]
  (let [src (:falling chamber)
        dest (move-rock src (nth (:jets chamber) (:jet-i chamber)) 0)
        max-x (->> dest
                   (map first)
                   (apply max))
        min-x (->> dest
                   (map first)
                   (apply min))]
    (if (or (some dest (:rocks chamber))
            (= min-x 0)
            (= max-x 8))
      (-> chamber
          (update :jet-i #(mod (inc %) (count (:jets chamber)))))
      (-> chamber
          (assoc :falling dest)
          (update :jet-i #(mod (inc %) (count (:jets chamber))))))))

(defn rock-fall [chamber]
  (let [src (:falling chamber)
        dest (move-rock src 0 -1)
        min-y (->> dest
                   (map second)
                   (apply min))]
    (if (or (some dest (:rocks chamber))
            (= min-y 0))
      (-> chamber
          (assoc :falling #{})
          (update :rocks into src)
          (update :rock-i #(mod (inc %) 5))
          (update :landed-rocks inc))
      (assoc chamber :falling dest))))

(defn next-moment [{:keys [falling] :as chamber}]
  (if (empty? falling)
    (rock-appear chamber)
    (-> chamber
        rock-push
        rock-fall)))

(defn part-1 [input]
  (->> (iterate next-moment (init-chamber input))
       (drop-while #(< (:landed-rocks %) 2022))
       first
       :rocks
       highest-rock))

(comment
  (part-1 puzzle-input)
  ;; => 3168
  )

;; (defn part-2 [input]
;;   (->> (iterate next-moment (init-chamber input))
;;        (drop-while #(< (:landed-rocks %) 1000000000000))
;;        first
;;        :rocks
;;        highest-rock))


