(ns partitions-symbolic
  (:require
   [printing :as prnt]))




;;; purely technical function
;;; for input [(a b c) 0] -> (ab c)
(defn- pack-up-nth [coll n]
  " input -> result:
    ['(a b c) 0] -> '(ab c)
    ['(a b c d) 2] -> '(b ac)
   "
  (concat
   (take (dec n) (rest coll))
   [(#(str %1 %2) (first coll) (nth coll n))]
   (take-last (- (count coll) (inc n)) coll)))


(pack-up-nth (pack-up-nth (pack-up-nth [0 4 1 2 3 4] 2) 2) 1)
(pack-up-nth [0 4 1 2 3 4] 4)



(comment
  " Generate all possible ways to break (partition) provided 
    elements collection (including trivial grouping - all elements
    together) 
    N.B. order of elements is irrelevant i.e.
    AB = BA, (a,b,bb) = ( b, a, bb) etc.
    e.g: 
    '(B B B W) -> (B,B,B,W) (BB,B,W) (B,B,BW) (B ,BBW) (BBB,W) (BB,BW) (BBBW) 
    '(A B)     -> (A,B) (AB)
    '(A B C)   -> (A,B,C) (AB,C)  (AC,B) (A,BC) (ABC) ")
(defn gen-partitions-from-elements
  ([elements-coll]
   (let [n   (dec (count elements-coll))
         col (gen-partitions-from-elements elements-coll n)
         filtered-col (filter #(not (= (count %) 1)) col)]
     (cond
       (== 1 (count elements-coll))
       elements-coll
       :else
       (concat (map gen-partitions-from-elements
                    filtered-col)
               col))))
  ([elements-coll partitions#]
   (loop [res [] i 1]
     (cond (== partitions# (dec i))
           res
           :else
           (recur (conj  res (pack-up-nth elements-coll i))
                  (inc i))))))




(comment "Some tests")
(def stuff (seq (gen-partitions-from-elements '(a  b c d))))
(prnt/print-stuff-to-file stuff "test.txt")

(gen-partitions-from-elements '(a  b c d) 3)
(gen-partitions-from-elements '(a  b c d))
(gen-partitions-from-elements '(a  b c))
(gen-partitions-from-elements '(a  b))
(gen-partitions-from-elements '(a))



;;; example: 
;;; having three black objects B and one white object W 
;;; they can be grouped in 7 ways like this:
;;; (BBBW)	(B,BBW)	(B,B,BW)	(B,B,B,W) 	(B,BB,W)	(BBB,W)	(BB,BW)
(gen-partitions-from-elements '(B B B W))


(defn gen-partitions-any
  ([elements-coll]
   (let [n   (dec (count elements-coll))
         col (gen-partitions-any elements-coll n)
         filtered-col (filter #(not (= (count %) 1)) col)]
     (cond
       (= 1 (count elements-coll))
       elements-coll
       :else
       (concat col (map gen-partitions-any (rest  filtered-col))))))
  ([elements-coll partitions#]
   (loop [res [] i 1]
     (cond (= partitions# (dec i))
           res
           :else
           (recur (cons  (pack-up-nth elements-coll i) res)
                  (inc i))))))


(gen-partitions-any '(a b c))
(gen-partitions-any '(B B B W))
(gen-partitions-from-elements '(B B B W))


;;;combination of conj and concat
;;; i.e. adds flattaned by one level 
;;;second col-b into col-a
(defn conjat [col_a col_b]
  (if (seq col_b)
    (conjat (conj col_a (first col_b)) (rest col_b))
    col_a))





(defn build-result-2
  ([col]
   (let [n (count col) , tmp (build-result-2 col n)]
     (cond
       (= n 1) col
       (= n 2) col
       :else
       (conjat (map build-result-2 tmp) [col]))))
  ([col n]
   (loop [counter 1
          results []]
     (if (< counter n)
       (recur (inc counter)
              (conj results (pack-up-nth col counter)))
       results)))
  ([col _  _]
   (apply str col)))
    ;;;(conj (reduce #(conj %1 (first %2)) [] (rest (build-result-2 col 0 0))) 
    ;;;      [(apply str col)])))




(conjat ['(1)] '('(3) '(4)))
(build-result-2 '(1 2 3 4))
(build-result-2 '(1 2 3 4 5))
(gen-partitions-any '(1 2 3) 2)

(defn gen-parts-any
  ([elements-coll]
   (let [n   (dec (count elements-coll))
         col (gen-parts-any elements-coll n)
         filtered-col (filter #(not (= (count %) 1)) col)
         res []]
     (cond
       (= 1 (count elements-coll))
       elements-coll
       :else
       (concat col (map gen-parts-any (rest filtered-col))))))
  ([elements-coll partitions#]
   (loop [res [] i 1]
     (cond (= partitions# (dec i))
           res
           :else
           (recur (cons  (pack-up-nth elements-coll i) res)
                  (inc i))))))


(gen-parts-any '(B B B W))