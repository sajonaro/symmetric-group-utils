(ns permutations
  (:require [clojure.set :refer :all ]
            [permutations :as p]))

(defn gen-permutations [coll]
  "generate a list of all possible permutations of elements in collection
   for exaple, (1 2 3) -> ((1 2 3)(3 2 1)(1 3 2)(2 1 3) (2 3 1)(3 1 2))"
  (lazy-seq
   (if (seq (rest coll))
     (apply concat (for [x coll]
                     (map #(cons x %)
                          (gen-permutations (remove #{x} coll)))))
     [coll])))

;;;some tests
(count (gen-permutations '(1 2 3))) ;;should be = 6


(defn single-cycle-to-permutation-helper [c]
  "hanldes conversion of cycle to permuatation notation
   in case when cycle is just a single cycle
   e.g. (1 2 3) or (1 2) etc"
  (loop[res {} ccl c]
    (cond
      (and ;;;weed out edge case 1
       (= 1 (count ccl))
       (= 0 (count res)))
        (assoc res (keyword (str (first ccl))) (first ccl))

      (and ;;;weed out edge case 2
       (= 2 (count ccl))
       (= 0 (count res)))
        (assoc (assoc res (keyword (str (last ccl))) (first ccl)) (keyword (str (first ccl))) (last ccl))

      (and
       (= 2 (count ccl))
       (>   (count res) 1))
        (assoc res (keyword (str (first ccl))) (last ccl))

      :else
        (recur (assoc (assoc res (keyword (str (last ccl))) (first c)) (keyword (str (first ccl))) (second ccl)) (rest ccl)))))   


(defn dot
  "Symmetric group operation *, i.e. multiplication 
   of two or more permutations
   defined as p1*p2 = p3,  such as p3 is the permutation equivalent to 
   consecutively applying  p1 after p2 ( right to left).
   p1,p2 are defined in 'permutation' notation
   examples:
   (1 2 3), (3 2 1)           ->  (3 2 1) "
  ([p] p)
  ([p1 p2](seq (map #(nth p1 (dec %)) p2)))
  ([p1 p2 & more](reduce dot (dot p1 p2) (reverse more))))


(dot '(2 3 1) '(2 3 1) '(3 1 2))
(dot '(2 3 1) '(2 3 1))
(dot [1 2 3] [1 2 3] [2 3 1])


(defn ccl-to-pmtn [c]
  "convert cycle to permutation
   i.e. a group Sn element from cycle to 
   permutation notation
   e.g. for G = S3 cycles -> permutations we have:
   '((1 2)(3))='((2 1)(3))='((3)(2 1))..   -> (2 1 3)
   '((1 3)(2))='((3 1)(2))='((2)(1 3))..   -> (3 2 1)
   '((1)(2 3))='((2 3)(1))='((3 2)(1))..   -> (1 3 2)   
   '((1 2 3))='((3 1 2))='((2 3 1))        -> (2 3 1)
   '((3 2 1))='((1 3 2))='((2 1 3))        -> (3 1 2)
   '((1)(2)(3))='((3)(2)(1))..             -> (1 2 3)
   Note, cycle is evaluated from left to right  "
  ( ->> (map single-cycle-to-permutation-helper c)
        (apply merge)
        (into (sorted-map))
        (vals)))

;;;some tests
(ccl-to-pmtn '((1 2 3 4 5 6 7)))
(ccl-to-pmtn '((1)))
(ccl-to-pmtn '((2 1)))
(ccl-to-pmtn '((1 3)(2)))
(ccl-to-pmtn '((1)(3)(2)))
(ccl-to-pmtn '((2 3 1)))


;;;convert a permutation to cycle notation
(defn pmtn-to-ccl[permutation]
  permutation)
;;;TODO


(defn dot-cycles
  "Symmetric group * operation, i.e. multiplication of two permutations
   defined as c1*c2 = p3,  such as p3 is the permutation equivalent to applying 
   consecutively permutaion c1 after c2 ( right to left).
   c1,c2 are defined in cycle notation
   examples:
   c1=(1 2)(3), c2 = (1)(2 3) ->  (2 3 1)
   (1 2)(3), (1 2 3)          ->  (1 3 2)
   (1 2 3),(1 2)(3)           ->  (3 2 1)
   (1 3)(2),(1 3)(2)          ->  (1 2 3)
   (1 2 3)(1 2 3)             ->  (3 1 2)"
  ([c] c)
  ([c1 c2]
   (let [a (ccl-to-pmtn c1)
         b (ccl-to-pmtn c2)]
     (pmtn-to-ccl (dot a b))))
  ([c1 c2 & more]
   (pmtn-to-ccl (reduce dot-cycles (dot-cycles c1 c2) (reverse more)))))


;;;some tests
(dot-cycles '((1 2 3)) '((1 2 3)))
(dot-cycles '((1 2)(3)) '((1)(2 3)))
(dot-cycles '((1 2)(3)) '((1 2 3)))
(dot-cycles '((1 2 3)) '((1 2)(3)))
(dot-cycles '((1 3)(2)) '((1 3)(2)))


;;;for a P calculate R = P^(-1)
;;;(such that p*r = i = r*p)
;;; e.g. (2 1 3) -> (2 1 3)
;;;      (3 2 1) -> (3 2 1)
;;;      (3 1 2) -> (2 3 1)
(defn invert[p]
  (->> (seq p)
       (zipmap (range 1 (inc (count p))))
       (map-invert)
       (into (sorted-map))
       (vals)))
  
(invert '(3 1 2 ))
(invert '(3 2 1))
(invert '(2 3 1))


;;; convert element of Sn 
;;; written in form of a permutation 
;;; to 'defining representaion'
;;; i.e. a matrix N x N  with 
;;; x[i,j] = 1 iff ccl[j] = i, 0 otherwise
;;; e.g  (2 1 3) -> [[0 1 0]
;;;                  [1 0 0]
;;;                  [0 0 1]] 
(defn pmtn-to-matrix [pmtn]
  (let [ n (count pmtn)]
    (loop [elements pmtn res []]
      (if (zero? (count elements))
        res
        (recur
         (rest elements)
         (into res [(assoc (vec (repeat n 0)) (dec (first elements)) 1)]))))))


;;; convert Sn element 
;;; written in form of a cycle - ccl 
;;; to 'defining representaion'
;;; i.e. a matrix N x N  with 
;;; x[i,j] = 1 iff ccl[j] = i, 0 otherwise
;;; e.g (1 2)(3) -> [[0 1 0]
;;;                  [1 0 0]
;;;                  [0 0 1]] 
(defn ccl-to-matrix [ccl]
  (->> (ccl-to-pmtn ccl)
       (pmtn-to-matrix)))


;;;helper functin to pretty-print 
;;;a matrix
(defn print-matrix[mtrx]
  (do
    (print "[")
    (doseq[el (butlast mtrx)]
     (do
       (print el)
       (println))
       (print " ")
     )
    (print (last mtrx))
    (print "]")
))

;;;some tests
(print-matrix (ccl-to-matrix [[1 2] [3] [4]]))
(pmtn-to-matrix [2 1 3])




;;; take Nth element of permutation group elements
;;; sorted in lexicographic (incremental) order
(defn take-nth-element [permutation-collection, N]
  (nth
   (gen-permutations permutation-collection)
   (dec N)))

;;; for example this should return '(1 0 2)
(take-nth-element '(0 1 2) 3)  

