(ns base.partitions-symbolic
  (:require
   [base.printing :as prnt]
   [base.permutations :as perm]
   [base.common :as cm] ))

;;; purely technical function
;;; for input [(a b c) 0] -> (ab c)
(defn- pack-up-nth
    " input -> result:
    ['(a b c) 0] -> '(ab c)
    ['(a b c d) 2] -> '(b ac)
   "
  [coll n]
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


(defn transform-element [element]
  (cond (string? element )
        (apply str (sort (str element (count element))))
   :else
        (str element)))

;;; function providing a (unique) KEY  
;;; identifying a grouping 
(defn unique-selector [grouping]
  (apply str (sort (map transform-element
                        grouping))))
 
;;;some tests
(unique-selector '(c "ab"))
(unique-selector '(a "bc"))
(unique-selector '("cb" a))
(unique-selector '( "bc" a a d))

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
  (cm/distinct-by selector-fn (gen-parts-symb col)))

(comment "Some tests"
         (def stuff (seq (gen-parts-symb '(a  b c d))))
(prnt/print-stuff-to-file stuff "test.txt"))


(gen-parts-symb '(a  b c d e))
(gen-parts-symb '(a  b c d) 0 0 0)
 
(gen-parts-symb '(a  b c) 0 0 0)
(gen-parts-symb '(a  b c))
(gen-parts-symb-unique '(a b c) unique-selector)

(gen-parts-symb '(a))
(perm/apply-default-cycle '(1 2 3))

;;; example: 
;;; having three black objects B and one white object W 
;;; they can be grouped in 7 ways like this:
;;; (BBBW)	(B,BBW)	(B,B,BW)	(B,B,B,W) 	(B,BB,W)	(BBB,W)	(BB,BW)
(gen-parts-symb-unique '(B B B W) unique-selector)
(gen-parts-symb-unique '(0 0 0 1) unique-selector)
