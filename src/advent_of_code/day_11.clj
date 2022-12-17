(ns advent-of-code.day-11 
  (:require [clojure.string :as string]
            [clojure.core.match :refer [match]]))

;; --- Day 11: Monkey in the Middle ---

(def puzzle-input (slurp "resources/day_11_input.txt"))

;; --- Part One ---

(def inspections (atom []))

(defn parse-monkey [monkey-txt]
  (let [lines (string/split-lines monkey-txt)]
    (map (fn [l f]
           (f (remove empty? (string/split (string/trim l) #"[ ,:]"))))
         lines
         [(comp parse-long last)
          #(->> %
                (drop 2)
                (map parse-long))
          #(->> %
                (drop 4))
          (comp parse-long last)
          (comp parse-long last)
          (comp parse-long last)])
    ))

(defn create-monkey [acc [n items [op arg] test true-monkey false-monkey]]
  (assoc acc n {:items (vec items)
                :operation (fn [old]
                             (match [op arg]
                               ["+" "old"] (+ old old)
                               ["*" "old"] (* old old)
                               ["+" _]     (+ old (parse-long arg))
                               ["*" _]     (* old (parse-long arg))))
                :to-monkey (fn [x]
                               (if (= 0 (rem x test))
                                 true-monkey
                                 false-monkey))}))

(defn turn [n relief lcm monkeys]
  (loop [monkeys monkeys]
    (let [{:keys [items operation to-monkey]} (monkeys n)]
      (if (empty? items)
        monkeys
        (let [item (mod (first items) lcm)
              worry (-> item
                        operation
                        (quot relief))
              move-to (to-monkey worry)]
          (swap! inspections update n inc)
          (recur (-> monkeys
                     (update-in [move-to :items] conj worry)
                     (update-in [n :items] #(vec (rest %)))))))))
  )

(defn round [relief lcm monkeys]
  (loop [n 0
         monkeys monkeys]
    (if (= n (count monkeys))
      monkeys
      (recur (inc n)
             (turn n relief lcm monkeys)))))

(defn do-rounds [n relief lcm monkeys]
  (loop [i 0
         monkeys monkeys]
    (if (= i n)
      monkeys
      (recur (inc i)
             (round relief lcm monkeys)))))

(defn init-inspections [monkeys]
  (reset! inspections [])
  (doseq [n (range (count monkeys))]
    (swap! inspections assoc n 0))
  monkeys)

(defn solution [rounds relief lcm input]
  (->> (string/split input #"\n\n")
       (map parse-monkey)
       init-inspections
       (reduce create-monkey {})
       (do-rounds rounds relief lcm)
       )
  (->> @inspections
       (sort >)
       (take 2)
       (apply *)
       )
  )

(def part-1 (partial solution 20 3 96577))

(comment
  (part-1 puzzle-input)
;;   => 117640
  )

;; --- Part Two ---

;; (def inspections-2 (atom []))

(def part-2 (partial solution 10000 1 96577))

(comment
  (solution 10000 1 (apply * [13 11 2 5 7 3 19 17]) puzzle-input)
  ;; => 30616425600
  )