(ns s3
  (:require [permutations :as p]
            [printing :as prnt]
            [clojure.pprint :as pp]
            [clojure.java.io :as io]))



  (def s3members
    (merge
     {:e '(1 2 3)} ;;; e*g=g*e, for any g in S3
     (zipmap
      '(g1 g2 g3 g4 g5)
      (next (p/gen-permutations '(1 2 3))))))

  ;;;print group details into file
  (pp/pprint s3members (io/writer "output/s3.txt"))
  

   ;;; print group multiplication table
  (prnt/multiplication-table-print-to-file 
   s3members
   p/dot
   "output/s3.txt"
   true)
   