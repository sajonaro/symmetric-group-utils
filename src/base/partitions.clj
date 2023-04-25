(ns base.partitions
  (:require 
    [base.printing :as prnt]))



;;;straightforward utility functions
(defn drop-zeroes [coll]
  (filter #(not= 0 %) coll))

(defn shave-off-second [a b]
  (drop-zeroes (concat [(inc a)] [(dec b)])))


;;; core of partitioning algorithm:
;;; bite off 1 from last element and
;;; try to add it to previous member i.e. n-1-th
;;; if n-1-th member is less then n-2-th member by at least one - ok, proceed to add one to it
;;; otherwise - let this extra 1 add up to the beginning of the sequence
;;; e.g. applied to
;;; (2,2,1) the result should be (3,2)
;;; (2,1,1) - > (2,2)
;;; (3) -> (3)
;;; note: overall sum of elements remains constant
(defn fold-last
  ([coll]
   (let [cnt (count coll)
         n-th (last coll)
         f-st (first coll)]
     (cond
     ;;;edge cases
       (= 1 cnt) coll
       (= 2 cnt) (concat [(inc f-st)] (take (dec n-th) (repeat 1)))
       :else (fold-last coll cnt))))
  ;;;;;; when count >= 3
  ([coll cnt]
   (let [n-th   (last coll)
         n-1-th (nth coll (- cnt 2))
         n-2-th (nth coll (- cnt 3))]
     (cond
       ;;;if configuration is (x x x ..1) or (x x .. x)
       ;;;then explode to (x+1 1 1 1 1 .. 1)
       (or 
        (and (= 1 n-th)
             (apply = (butlast coll)))
        (apply = coll))
       (concat
        [(inc (first coll))]
        (take (- (apply + coll) (inc (first coll))) (repeat 1)))

       (< n-1-th n-2-th) (concat
                          (take (- cnt 2) coll)
                          (shave-off-second n-1-th (last coll)))

       (>= n-1-th n-2-th) (drop-zeroes
                           (concat
                            (fold-last (concat (take (- cnt 2) coll) [(inc n-1-th)]))
                            [(dec n-th)]))))))


;;; a partition of a number N
;;; is a weakly decreasing sequence (i1>=i2>=..ik )  of numbers adding up to N
;;; e.g. patritions of 
;;; 1:                                                                                                                                  (1) 
;;; 2:(1,1)                                                                                                                             (2)
;;; 3:(1,1,1)         (2,1)                                                                                                             (3)
;;; 4:(1,1,1,1)       (2,1,1)(2,2)                       (3,1)                                                                          (4)
;;; 5:(1,1,1,1,1)     (2,1,1,1)(2,2,1)                   (3,1,1)(3,2)                       (4,1)                                       (5)
;;; 6:(1,1,1,1,1,1)   (2,1,1,1,1)(2,2,1,1)(2,2,2)        (3,1,1,1)(3,2,1)(3,3)              (4,1,1)(4,2)          (5,1)                 (6)
;;; 7:(1,1,1,1,1,1,1) (2,1,1,1,1,1)(2,2,1,1,1)(2,2,2,1)  (3,1,1,1,1)(3,2,1,1)(3,2,2)(3,3,1) (4,1,1,1)(4,2,1)(4,3) (5,1,1)(5,2)   (6,1)  (7) 
(defn gen-partitions
  " a partition of a number N
    is a weakly decreasing sequence (i1>=i2>=..ik )  of numbers adding up to N
    e.g. patritions of 
    1:(1)                                                                                                                                 (1) 
    2:(1,1)                                                                                                                             (2)
    3:(1,1,1)         (2,1)                                                                                                             (3)
    4:(1,1,1,1)       (2,1,1)(2,2)                       (3,1)                                                                          (4)
    5:(1,1,1,1,1)     (2,1,1,1)(2,2,1)                   (3,1,1)(3,2)                       (4,1)                                       (5)
    6:(1,1,1,1,1,1)   (2,1,1,1,1)(2,2,1,1)(2,2,2)        (3,1,1,1)(3,2,1)(3,3)              (4,1,1)(4,2)          (5,1)                 (6)
    7:(1,1,1,1,1,1,1) (2,1,1,1,1,1)(2,2,1,1,1)(2,2,2,1)  (3,1,1,1,1)(3,2,1,1)(3,2,2)(3,3,1) (4,1,1,1)(4,2,1)(4,3) (5,1,1)(5,2)   (6,1)  (7) 
  "
  ([n]
   (gen-partitions n (vector (take n (repeat 1)))))
  ([n coll]
   (cond
     (= 1 (count (last coll))) coll
     :else (gen-partitions n (conj coll (fold-last (last coll)))))))




;;;some tests

(gen-partitions 4)

(fold-last '(4 4))

(fold-last '(3 3 3))

(fold-last '(2 1 1))

(fold-last '(3 2 1 1 1))

(fold-last '(3 3 1 1 1))

(fold-last '(3))

(fold-last '(2 2 2 1))
