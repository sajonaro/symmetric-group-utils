(ns printing
  (:require [clojure.pprint :as pp]
            [clojure.java.io :as io]))



(defn permutation-to-mapset [p]
  "turn a ( x1 ....xn) permutation into
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


(defn map-group-transform [els transform value]
  "apply func to every value in the map
   and find corresponding group element for resulting value
  (assumption is - els contains all group members, keys - names
   vals - their corresponding values) 
   func is binary function, first operand is val second - value
   "
  (let [rev-els (clojure.set/map-invert els)]
    (zipmap
     (keys els)
     (map #(rev-els %) (map #(transform value %) (vals els))))))

(clojure.set/map-invert {:1 2 :3 4 :5 2})
(map-group-transform {:1 2 :3 4 :5 2} * 2)

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
  (loop [i 1 res []]
    (if (> i (count els))
      (pp/print-table res)
      (recur (inc i)
             (conj res
                   (merge {nil (nth (keys els) (dec i))}
                          (map-group-transform
                           els
                           func
                           (els (nth (keys els) (dec i))))))))))

;;some tests
(print-multiplication-table  {:1 1 :2 2 :3 3} *)


(defn multiplication-table-print-to-file [els func filename appendOption]
 ^{:doc "prints multiplication  table intto file:
         els - map with groups elements: keys are group member names, values - actual values
         func - binary group operation
         filename - name of the file to write into
         appendOption - can be true/false, if true - file will be appended to, false - rewritten"
   :arglists '(els  func filename appendOption)
   :added "1.0"
   :static true}
  (with-open [w (io/writer filename :append appendOption)]
    (.write w (with-out-str (print-multiplication-table els func)))))


(defn print-stuff-to-file [stuff filename]
  ^{:doc "prints (str stuff) to filename
          stuff - object 
          filename - name of the file to write into"
   :arglists '(stuff filename)
   :added "1.0"
   :static true}
  (with-open [w (io/writer filename :append false)]
    (.write w (str stuff))))
