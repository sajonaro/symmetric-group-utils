(ns partitions-symbolic
  (:require
   [printing :as prnt]
   [permutations :as perm]
   [basic]))


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


;;;relaxed version of gen-parts-symb-unique
;;;in that is for it (a b) not = (b a)
;;;therefore we obtain way more combinations(groupings) than needed
(defn gen-parts-symb
  ([col _ _]
   (let [n (count col) , tmp (gen-parts-symb col 0 0 0 )]
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
      (conj (gen-parts-symb col 0 0) (conj () (apply str col)))))
  ([col _ _ _]
   (let[n (count col) lst (perm/apply-default-cycle col)]
    (mapcat #(gen-parts-symb % n) lst ))))


;;; function providing a (unique) KEY  
;;; identifying a grouping 
(defn grouping-selector [grouping]
  (sort (apply concat
               (map #(sort (str %))
                    (cons (count grouping) grouping)))))
 
;; ((apply concat (map #(sort (str %)) '("ac" "b" "bca")))

(cons (count '(a b "ac"  "bca" a)) '(a b "ac"  "bca" a))
(grouping-selector '(c "ab"))
(map #(sort (str %)) '(a b "ac"  "bca" a))
(zipmap '(a b) '(1 1))


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
(defn gen-parts-symb-unique[col, selector-fn]
  (basic/distinct-by selector-fn (gen-parts-symb col)))

(comment "Some tests")
(def stuff (seq (gen-parts-symb '(a  b c d))))
(prnt/print-stuff-to-file stuff "test.txt")

(gen-parts-symb '(a  b c d e))
(gen-parts-symb '(a  b c d) 0 0 0)

;; (ab,ac)(ab, bc)(ca, cb)
(gen-parts-symb '(a  b c) 0 0 0)
(gen-parts-symb '(a  b c))
(gen-parts-symb-unique '(a b c) grouping-selector)
(gen-parts-symb '(a))
(perm/apply-default-cycle '(1 2 3))


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