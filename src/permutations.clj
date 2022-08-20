(ns permutations
  (:require [partitions :as ptns] [cycles :as ccls]))


(defn gen-permutations [collection]
  "generate a list of all possible permutations of elements in collection
   for exaple, ( 1 2 3) -> ( (1 2 3), (3,2,1))"
  ())


(defn gen-all-cycles-by-part [partition]
  "given a partition i.e. a list of wearkly decreasing integers
   generate all cyclic permutations of type described by this permutation
   e.g. for a permuation (3,1,1) possible cycles are
   (1 2 3)(4)(5) "
  ())