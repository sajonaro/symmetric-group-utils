(ns algebra.cli-interface
   (:require 
   [base.permutations :as perm]))

(defn extract-seq-value
  "extract value from edn cli parameter"
  [edn-data]
  (map #(Integer/parseInt %) (get-in edn-data [:_arguments])))


;;;interface to logic
(defn p2c
  "perm/permutation-to-ccl cli interface"
  [args] 
  (->> args 
       extract-seq-value 
       perm/permutation-to-ccl 
       println )) 


(defn c2m
  "perm/ccl-to-matrix cli interface"
  [args]
  (->> args
       extract-seq-value
       perm/ccl-to-matrix
       perm/print-matrix))