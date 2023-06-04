(ns interface.cli-interface
   (:require 
    [base.permutations :as perm]
    [algebra.symmetry-groups :as sg]
    [base.printing :as prnt]))


(defn extract-arg-values
  "extract value from edn cli parameter"
  [func cli-args]
  (map func (get-in cli-args [:_arguments])))


(defn extract-integers
  [cli-args]
  (extract-arg-values #(Integer/parseInt %) cli-args))

(defn extract-int
  [cli-args] 
 (first (extract-integers cli-args)))


(defn extract-string
  [cli-args]
  (->> cli-args 
       (extract-arg-values #(clojure.edn/read-string %))
       first))


;;;interface to logic
(defn p2c
  "perm/permutation-to-ccl cli interface"
  [cli-args] 
  (->> cli-args 
       extract-integers
       perm/permutation-to-ccl 
       println )) 

(defn c2m
  "perm/ccl-to-matrix cli interface"
  [cli-args]
  (->> cli-args
       extract-string
       perm/ccl-to-matrix
       perm/print-matrix))

(defn get-gr-mems 
  "Print all Sn group members with labels  "
  [cli-args]
  (->> cli-args
       extract-int
       sg/get-sn-map   
       println))

(defn cayley
  "ptint cayley table for group Sn, n extracted from args"
  [cli-args]
  (let [n (extract-int cli-args)]
   (do  (println "Group members: ")
        (println (sg/get-sn-map n))
        (println)
        (println "Cayley table:")
        (prnt/print-multiplication-table
          (sg/get-sn-map n)
          perm/dot))))

(defn get-conj-classes
  "Break Sn down into conjugacy classes"
  [cli-args]
  (->> cli-args
       extract-int
       sg/get-conjugacy-classes 
       prnt/print-datatable))

(defn c2p
  "convert cycle into permutation (sequence)"
  [cli-args]
  (->> cli-args
       extract-string
       perm/ccl-to-pmtn
       prnt/permutation-print))


(defn get-order
  "convert cycle into permutation (sequence)"
  [cli-args]
  (->> cli-args
       extract-integers
       sg/get-order-p
       println))


(defn p2t
   "convert cycle into permutation (sequence)"
  [cli-args]
  (->> cli-args
       extract-integers
       perm/permutation-to-transposition
       println))

(defn matrix-mult
  ""
   [cli-args]
  (->> "matrix multiplication"
       println))


(defn cg
  ""
  [cli-args]
  (->> "cayley graph"
       println))

(defn get-sign
  ""
  [cli-args]
  (->> cli-args
       extract-integers
       sg/get-sign
       println))