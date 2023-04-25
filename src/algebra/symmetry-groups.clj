(ns algebra.symmetry-groups
  (:require [base.permutations :as perm]))

(defn get-conjugacy-classes [order]
  "get all elements of the group S(N = order)
   i.e. different permutations ( N --> N)
   broken down into conjugacy classes
   e.g:
   order = 3 -> (1 2 3)"
  (let[ els (perm/gen-permutations (range 1 (inc order)))]
   (map perm/pmtn-to-ccl els)))

(get-conjugacy-classes 3)