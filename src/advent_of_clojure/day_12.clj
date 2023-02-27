(ns advent-of-clojure.day-12 
  (:require [clojure.string :as string]
            [clojure.data.priority-map :refer [priority-map]]))

;; --- Day 12: Hill Climbing Algorithm ---

(def puzzle-input (slurp "resources/day_12_input.txt"))
;; --- Part One ---

(defn parse-input [input]
  (->> input
       string/split-lines
       (map vec)
       vec))

(defn find-start-end [acc i v]
  (let [start (.indexOf v \S)
        end   (.indexOf v \E)] 
    (cond-> acc
      (> start -1) (assoc :start [start i])
      (> end   -1) (assoc :end   [end   i]))))

(defn can-step? [[from-x from-y] [to-x to-y] height-map] 
  (let [from-height (nth (nth height-map from-y) from-x)
        to-height   (nth (nth height-map to-y)   to-x)
        one-step?    #(<= (- (int %2) (int %1)) 1)]
    ;; (println from-height to-height (one-step? from-height to-height))
    (one-step? from-height to-height)))

(defn get-neighbors-1 [[x y] height-map]
  (let [poss [[x (dec y)] [(inc x) y] [x (inc y)] [(dec x) y]]]
    (filterv (fn [[X Y]]
                     (and (< -1 X (count (first height-map)))
                          (< -1 Y (count height-map))
                          (can-step? [x y] [X Y] height-map)))
             poss)))

(defn dijkstra [height-map start get-neighbors]
  (loop [q (priority-map start 0)
         dist (into {} (for [y (range (count height-map))
                                    x (range (count (first height-map)))]
                                (if (= [x y] start)
                                  [start 0]
                                  [[x y] ##Inf])))
         prev {}]
    (let [u (peek q)]
      (if u
        (let [adj (get-neighbors (first u) height-map)
            ;;   _ (println (first u) ": " adj)
              {:keys [q dist prev]} (reduce (fn [acc v]
                                              (let [alt (inc (dist (first u)))]
                                                (if (< alt (dist v))
                                                  (-> acc
                                                      (assoc-in [:dist v] alt)
                                                      (assoc-in [:prev v] (first u))
                                                      (update :q conj [v alt]))
                                                  acc)))
                                            {:q (pop q) :dist dist :prev prev}
                                            adj)]
          (recur q dist prev))
        {:dist dist :prev prev}))
    ))

(defn print-grid [dist w h]
  (for [y (range h)]
    (for [x (range w)]
      (dist [x y]))))

(defn part-1 [input]
  (let [parsed (parse-input input)
        {:keys [start end]} (reduce-kv find-start-end
                                       {}
                                       parsed)
        height-map (map (partial replace {\S \a \E \z}) parsed)
        {:keys [dist prev]} (dijkstra height-map start get-neighbors-1)]
    ;; (print-grid dist (count (first height-map)) (count height-map))
    (dec (count (loop [s []
                       u end]
                  (if (or (prev u) (= u start))
                    (recur (cons u s)
                           (prev u))
                    s))))
    ))

(comment
  (part-1 puzzle-input)
;;   => 517
  )

;; --- Part Two ---

(defn find-start-ends [acc i v]
  (let [start (.indexOf v \E)
        end-1 (.indexOf v \S)
        end-2 (.indexOf v \a)]
    (cond-> acc
      (> start -1) (assoc  :start [start i])
      (> end-1 -1) (update :ends conj [end-1 i])
      (> end-2 -1) (update :ends conj [end-2 i]))))

(defn get-neighbors-2 [[x y] height-map]
  (let [poss [[x (dec y)] [(inc x) y] [x (inc y)] [(dec x) y]]]
    (filterv (fn [[X Y]]
               (and (< -1 X (count (first height-map)))
                    (< -1 Y (count height-map))
                    (can-step? [X Y] [x y] height-map)))
             poss)))

(defn part-2 [input]
  (let [parsed (parse-input input)
        {:keys [start ends]} (reduce-kv find-start-ends
                                        {:start nil :ends []}
                                        parsed)
        height-map (map (partial replace {\S \a \E \z}) parsed)
        {:keys [dist]} (dijkstra height-map start get-neighbors-2)]
    (print-grid dist (count (first height-map)) (count height-map))
    (reduce (fn [acc end]
              (let [path-len (dist end)]
                (min path-len acc)))
            ##Inf
            ends)))

(comment
  (part-2 puzzle-input)
;;   => 512
  )