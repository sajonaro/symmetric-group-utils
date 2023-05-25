(ns algebra.symmetry-groups
  (:require 
   [base.permutations :as perm]
   [clojure.pprint :as pp]
   [base.printing :as prnt]
   [clojure.java.io :as io]))

(defn are-equal-cycles
  "True iff arguments are equivalent Sn group elements,
  False - otherwise
   e.g.: ['((1 2)(3)) '((3)(1)(2))] - false
         ['((1 2)(3)) '((3)(1 2))]  - true
   "
  [g1 & gn]
  (apply = (map perm/ccl-to-pmtn (cons g1 gn))))

;;;(are-equal-cycles '((1 2)(3)) '((3)(1)(2)))

(defn are-equal-permutations
  "True iff arguments are equivalent Sn group elements,
  False - otherwise
     e.g.: ['(1 2) '(1 2) '(1 2)] - true
           ['(1 2) '(2 1)]  - false"
  [g1 & gn]
  (apply =  (cons g1 gn)))

;;;(are-equal-permutations '(1 2 3) '(1 2 3) '(1 3 2))

(defn get-all-group-members
  "get the list of all group members in alphabetic order of Sn, `n` is provided"
  [n]
  (map 
     perm/permutation-to-ccl
       (perm/gen-permutations (range 1 (inc n)))))


(defn get-group-dimension
  "Calculate group's N from group's element `g`
  `g` can be provided in a permutation form: ( 1 2 4 3) "
  [g]
  (apply max g))


(defn pick-next-group-member
  "Return next group element to `g`, assuming group elements
   are sorted by alphabetic order"
  [g]
  (loop[c (get-all-group-members (get-group-dimension g)) fst (first c) ]
   (if (are-equal-permutations (first c) g)
         (if (seq (rest c))
               (first (rest c))
             fst)
       (recur (rest c) fst))))

;;;TODO hangs!!
;;;(pick-next-group-member '(1 2 3))

(defn get-conjugacy-classes 
  "Break down  all elements of the group S(N = order)into conjugacy classes
   
   def: permutations a and b are in one conjugacy class if there exists a group
   member c (permutation) such that a = cbc^-1  

   s       :  m1 ->                       m2
   x s x-1 : xm1 -> [(xsx-1)(xm1)=xsm1=] xm2 
    
   e.g:

   order = 3 -> (1 2 3)"
  [order]
  (  for[ a (perm/gen-permutations (range 1 (inc order)))
          b (perm/gen-permutations (range 1 (inc order))) ]
      (perm/dot a b)))

(get-conjugacy-classes 3)


 (def s3members
   (merge
     {:e '(1 2 3)} ;;; e*g=g*e, for any g in S3
     (zipmap
      '(g1 g2 g3 g4 g5)
      (next (perm/gen-permutations '(1 2 3))))))

;;;(def path "../../resources/output/s3.txt")

  ;;;print group details into file
;;;(pp/pprint s3members (io/writer path))

;;; print group multiplication table
;;; (prnt/multiplication-table-print-to-file
;;; s3members;;
;;; path
;;; true)
;;;