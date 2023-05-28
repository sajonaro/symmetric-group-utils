(ns algebra.symmetry-groups
  (:require 
   [base.permutations :as perm]
   [base.common :as com]))

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



(defn- parse-int [s]
  (if (= "" (subs s 1))
    0
    (Integer/parseInt (subs s 1))))


(parse-int "e")

(defn get-sn-map
  "generate map of group members oof Sn"
  ([n _ _]
  (merge
    {"e" (range 1 (inc n))} ;;; e*g=g*e, for any g in S3
    (zipmap
      (map #(str \g %) (range 1 (com/factorial n)))
      (next (perm/gen-permutations (range 1 (inc n)))))))
  ([n _]
   "Arity 2 only used to sort collection by integer postfix"
    (sort-by key
             #(compare (parse-int %1)
                       (parse-int %2))
             (get-sn-map n nil nil)))
  ([n]
     "Arity 1 used to convert array into map"
      (zipmap
       (map first (get-sn-map n nil))
       (map last (get-sn-map n nil)))))

(defn get-sn-map-cycles
  "get group memebrs in cycle notation"
  [n]
  (com/map-val-transform perm/permutation-to-ccl (get-sn-map n)))



(defn get-conjugacy-classes
  "Break down  all elements of the group S(N = order)into conjugacy classes
   
   def: permutations a and b are in one conjugacy class if there exists a group
   member c (permutation) such that a = cbc^-1  
  
   Also it means a and b have same cycle type "
  [order]
  (->> order
       get-sn-map-cycles
       (group-by #(sort (map count (val %))))
       ;;into {} (cat (map identity)) untangles nested vector
       (com/map-val-transform #(into {} (cat (map identity)) %))))

(defn get-unity
  "Get a unity permutation corresponding to group element `g` (permutation) "
  [g]
  (range 1 (inc (get-group-dimension g))))


(defn get-order-p
  "determine order of a permucation `permutation` (list)"
  [permutation]
  (loop[res 1  k permutation unity (get-unity permutation)]
   (if (are-equal-permutations k unity)
     res
     (recur (inc res) (perm/dot k permutation) unity))))
