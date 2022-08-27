(ns printing
  (:require [clojure.pprint :as pp]))



  (defn permutation-to-mapset [p]
  "turn a ( x1 ....xn) pemutation into
   a map-set with keys representing
   position and value - corresponding xi"
    (loop [res {} i 0]
      (if (= (count p) (inc i))
        (assoc res (keyword (str (inc i))) (nth p i))
        (recur (assoc res (keyword (str (inc i))) (nth p i)) (inc i)))))


;;;some tests
(permutation-to-mapset '(2 3 1 4))



(defn permutation-print [p]
  (pp/print-table  [(permutation-to-mapset p)]))

;;;some tests
(permutation-print '(2 4 1 3))


(defn map-transform-all-by-value[els func value]
  "apply func to every value in the map
   func is binary function, first operand is val second - value
   "
  (zipmap (keys els) (map #(func value %) (vals els))))

(map-transform-all-by-value {:1 2 :3 4} * 4)

(defn print-multiplication-table [els func] 
  " Print a multiplication table of values
  provided in a map in following form:
        | k1   |  k2  |  k3  |..   |kn
     -------------------------------
     k1 | 1x1  | 1x2  | 1x3  |     |1xn
     -------------------------------
     k2 | 2x1  | 2x2  | 2x3  |     |2xn
     -------------------------------
     k3 | 3x1  | 3x2  | 1x3  |     |3xn  
     . . . . .  . . . . . . . . . . . .  .
     -------------------------------
     kn | nx1  | nx2  | nx3  |  .. |nxn
   
   where 1..n are values of 'els' map
   headers of the table are keys of 'els' map
   and effectively, a x b means (func a b)
   "
  (loop[i 1 res []]
   (if (> i (count els))
     (pp/print-table res)
     (recur (inc i)
            (conj res 
                  (merge {nil (nth (keys els) (dec i))}
                           (map-transform-all-by-value 
                             els
                             func
                             (els (nth (keys els) (dec i))))))))))

;;some tests
(print-multiplication-table  {:1 1 :2 2 :3 3} * )
