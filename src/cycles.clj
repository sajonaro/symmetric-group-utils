(ns cycles
  (:require
   [partitions :as ptns]))


(defn enhance-partition [partition]
  "Auxiliary function, turns a partition into
   a sequence of tuples like so: 
   (2 2 1 1) -> ([2,2] [4,2] [5,1] [6,1])
   where the first element of the tuple is the sum of 
   previous numbers in partiion,and the second - current 
   element
  "
  (rest (reduce
         (fn [result number]
           (conj result [(+  number (first (last result))) number]))
         [[0]]
         partition)))




(defn partition-to-cycle [partition]
  "Given a partition i.e. a list of wearkly decreasing integers
   generate its corresponding cyclic notation
   e.g. for a permuation (3,1,1) possible cyclic notationis (1 2 3)(4)(5),
   similarly:
   (1)      ----> (1)
   (1,1)    ----> (1)(2)= (2)(1)
   (2,1)    ----> (1 2)(3) = (3)(2 1)= (3)(1 2)=(2 1)(3)
   (3,2,1)  ----> (1 2 3)(4 5)(6) = ..
   Note, the sum of numbers in partition is the order of Symmetric group,
   i.e. the number N of elements participating in permutation "
  (reduce
   (fn [result element]
     (conj result (range
                   (- (first element) (dec (last element)))
                   (inc (first element)))))
   []
   (enhance-partition partition)))


(defn rotate-cycle-right[cycle]
  "rorates a cycle (sequence of elements) to the right by 1. is applies
   cyclic rotation by one element
   e.g. for input (1 2 3) -> (3 1 2)
   "
  (cons (last cycle) (butlast cycle)))


(defn gen-all-cycles-by-part [partition]
  "given a partition i.e. a list of weakkly decreasing integers
   generate all cyclic permutations of type described by this permutation
   e.g. for a permuation (3,1,1) possible cycles are
   (1 2 3)(4)(5)
   (3 1 2)(4)(5)
   (2 3 1)(4)(5) "
  (let [c (partition-to-cycle partition)]
    (loop [res [(map rotate-cycle-right c)]  i (apply * (map count c))]
     (if (= 0 i)
       (distinct res)
       (recur (conj res (map rotate-cycle-right (last res))) (dec i))))))


;;;some tests

(ptns/gen-partitions 3)



(partition-to-cycle '(1 1 1))
(partition-to-cycle '(2 1))
(partition-to-cycle '(3))

(gen-all-cycles-by-part '(3 2 1))
(gen-all-cycles-by-part '(3 1))
(rotate-cycle-right '(1 ))