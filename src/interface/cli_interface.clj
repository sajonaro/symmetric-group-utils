(ns interface.cli-interface
   (:require 
   [base.permutations :as perm]))

(defn extract-arg-value
  "extract value from edn cli parameter"
  [func edn-data]
  (map func (get-in edn-data [:_arguments])))


;;;interface to logic
(defn p2c
  "perm/permutation-to-ccl cli interface"
  [args] 
  (->> args 
       (extract-arg-value #(Integer/parseInt %)) 
       perm/permutation-to-ccl 
       println )) 


(defn c2m
  "perm/ccl-to-matrix cli interface"
  [args]
  (->> args
       (extract-arg-value #(clojure.edn/read-string %))
       first
       perm/ccl-to-matrix
       perm/print-matrix))