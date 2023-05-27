(ns interface.cli-interface
   (:require 
    [base.permutations :as perm]
    [algebra.symmetry-groups :as sg]
    [base.printing :as prnt]
    [clojure.pprint :as pp]
    [base.common :as com]))


(defn extract-arg-values
  "extract value from edn cli parameter"
  [func edn-data]
  (map func (get-in edn-data [:_arguments])))

(defn extract-int
  [args] 
  (->> args 
       (extract-arg-values #(Integer/parseInt %))
       first))



;;;interface to logic
(defn p2c
  "perm/permutation-to-ccl cli interface"
  [args] 
  (->> args 
       (extract-arg-values #(Integer/parseInt %)) 
       perm/permutation-to-ccl 
       println )) 

(defn c2m
  "perm/ccl-to-matrix cli interface"
  [args]
  (->> args
       (extract-arg-values #(clojure.edn/read-string %))
       first
       perm/ccl-to-matrix
       perm/print-matrix))

(defn get-gr-mems 
  "Print all Sn group members with labels  "
  [args]
  (->> args
       extract-int
       sg/get-sn-map   
       println))

(defn cayley
  "ptint cayley table for group Sn, n extracted from args"
  [args]
  (let [n (extract-int args)]
   (do  (println "Group members: ")
        (println (sg/get-sn-map n))
        (println)
        (println "Cayley table:")
        (prnt/print-multiplication-table
          (sg/get-sn-map n)
          perm/dot))))

(defn get-conj-classes
  "Break Sn down into conjugacy classes"
  [args]
  (->> args
       extract-int
       sg/get-conjugacy-classes 
       prnt/print-datatable))

