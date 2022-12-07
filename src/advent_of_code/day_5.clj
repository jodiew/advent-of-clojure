(ns advent-of-code.day-5
  (:require [clojure.string :as string]))

;; --- Day 5: Supply Stacks ---

;; Part 1

(defn parse-input [input]
  (let [[stacks moves] (->> (string/split input #"\n\n")
                            (map (comp
                                  string/split-lines)))]
    {:stacks (->> stacks
                  butlast
                  (map (comp
                        (partial map (partial filter #(<= (int \A) (int %) (int \Z))))
                        (partial partition-all 4)))
                  (apply (partial mapv vector))
                  (map (comp
                        vec
                        (partial apply concat)
                        (partial filter not-empty)))
                  vec)
     :moves (map (comp
                  (fn [[_ move _ from _ to]]
                    {:move (parse-long move)
                     :from (parse-long from)
                     :to   (parse-long to)})
                  #(string/split % #" "))
             moves)}))

(defn move-a-crate [from to stacks]
  (let [to-move (first (stacks (dec from)))]
    (-> stacks
        (update (dec from) #(vec (rest %)))
        (update (dec to) #(vec (cons to-move %)))))
  )

(defn do-move [stacks {:keys [move from to]}]
  (reduce (fn [acc _]
            (move-a-crate from to acc))
          stacks
          (range move))
  )

(defn solver [move-fn input]
  (let [{:keys [stacks moves]} (parse-input input)]
    (->> (reduce move-fn
                 stacks
                 moves)
         (map first)
         string/join)))

(def part-1 (partial solver do-move))

(part-1 (slurp "resources/day_5_input.txt"))
;; => VJSFHWGFT

;; Part 2

(defn do-move-2 [stacks {:keys [move from to]}]
  (let [to-move (subvec (stacks (dec from)) 0 move)]
    (-> stacks
        (update (dec from) #(subvec % move))
        (update (dec to)   #(vec (concat to-move %))))))

(def part-2 (partial solver do-move-2))

(part-2 (slurp "resources/day_5_input.txt"))
;; => LCTQFBVZV
