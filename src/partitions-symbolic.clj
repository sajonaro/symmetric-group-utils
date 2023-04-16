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

;;;tests
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
(defn gen-parts-symb
  ([col _ _]
   (let [n (count col) , tmp (gen-parts-symb col n)]
     (cond
       (= n 1) col
       (= n 2) [col]
       :else
       (conj (mapcat #(gen-parts-symb % 0 0) tmp) col))))
  ([col n]
   (loop [counter 1
          results []]
     (if (< counter n)
       (recur (inc counter)
              (conj results (pack-up-nth col counter)))
       results)))
  ([col]
   (if (= (count col) 1)
     col 
    (conj (gen-parts-symb col 0 0) (conj () (apply str col))))))



(comment "Some tests")
(def stuff (seq (gen-parts-symb '(a  b c d))))
(prnt/print-stuff-to-file stuff "test.txt")

(gen-parts-symb '(a  b c d e))
(gen-parts-symb '(a  b c d))
(gen-parts-symb '(a  b c))
(gen-parts-symb '(a  b))
(gen-parts-symb '(a))



;;; example: 
;;; having three black objects B and one white object W 
;;; they can be grouped in 7 ways like this:
;;; (BBBW)	(B,BBW)	(B,B,BW)	(B,B,B,W) 	(B,BB,W)	(BBB,W)	(BB,BW)
(gen-parts-symb '(B B B W))


;;;combination of conj and concat
;;; i.e. adds flattaned by one level 
;;;second col-b into col-a
(defn conjat [col_a col_b]
  (if (seq col_b)
    (conjat (conj col_a (first col_b)) (rest col_b))
    col_a))

;;;example
(conjat ['(1)] '('(3) '(4)))