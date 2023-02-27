(ns advent-of-clojure.day-16 
  (:require [clojure.string :as string]
            [clojure.set :as set]))

;; --- Day 16: Proboscidea Volcanium ---
;; Inspired by this solution: https://github.com/pbruyninckx/aoc2022/blob/main/src/aoc/day16.clj
(def puzzle-input (slurp "resources/day_16_input.txt"))

;; --- Part One ---

(defn parse-valve [line]
  (let [[_ valve flow-string tunnels-string] (first (re-seq #"Valve ([A-Z]{2}) has flow rate=([0-9]+); .* valves? (.*)" line))]
    {valve {:flow (parse-long flow-string)
            :tunnels (string/split tunnels-string #", ")}}))

(defn parse-valves [input]
  (->> input
       string/split-lines
       (map parse-valve)
       (apply merge)))

(defn next-minute [valves states]
  (reduce-kv (fn [acc [valve opened] pressure]
               (let [new-pressure (apply + pressure (map #(get-in valves [% :flow]) opened))]
                 (apply (partial merge-with max)
                        acc
                        (when (and (> (get-in valves [valve :flow]) 0)
                                   (not (contains? opened valve)))
                          {[valve (conj opened valve)] new-pressure})
                        (map (fn [next-valve]
                               {[next-valve opened] new-pressure})
                             (get-in valves [valve :tunnels])))))
             {}
             states))

(defn find-max-pressure [valves]
  (->> (iterate (partial next-minute valves) {["AA" #{}] 0})
       (drop 30)
       first
       (map val)
       (apply max)))

(defn part-1 [input]
  (-> input
      parse-valves
      find-max-pressure
      ))

(comment
  (part-1 puzzle-input)
  ;; => 1488
  )

;; --- Part Two ---

(defn find-max-with-elephant [valves]
  (->> (iterate (partial next-minute valves) {["AA" #{}] 0})
       (drop 26)
       first
       (reduce-kv (fn [acc [_ opened] pressure]
                    (merge-with max acc {opened pressure}))
                  {})
       (#(for [[you-open you-pressure] (seq %)
               [ele-open ele-pressure] (seq %)
               :when (empty? (set/intersection you-open ele-open))]
           (+ you-pressure ele-pressure)))
       (apply max)))

(defn part-2 [input]
  (-> input
      parse-valves
      find-max-with-elephant))

(comment
  (part-2 puzzle-input)
  ;; => 2111
  )
