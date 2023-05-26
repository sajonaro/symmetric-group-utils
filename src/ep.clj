(ns ep
  (:gen-class)
  (:require 
   [interface.cli-interface :as cli]
   [cli-matic.core :refer [run-cmd]]))


;;;definition of what CLI tool can do
(def CONF
  {:command     "gu"
   :description "A command-line symmetric group utility"
   :version     "0.0.1"
   :subcommands [          
                 {:command     "p2c"
                  :description "Convert a permutation into a cycle form "
                  :examples    ["./gu p2c 2 3 1      -->    (1 2 3)   "
                                "./gu p2c 2 1 4 3    -->    (1 2)(3 4)"]
                  :runs        cli/p2c}
                 
                 {:command     "c2m"
                  :description "Convert a cycle into matrix form i.e get a matrix representation for a cycle"
                  :examples [ "./gu c2m '((1 2)(3))' -->
                               [[0 1 0]
                                [1 0 0]
                                [0 0 1]]"]
                  :runs cli/c2m }
                 
                 {:command "get-gr-mems"
                  :description "Get all Sn group members up to N, sorted alphabetically"
                  :examples ["./gu get-gr-mems 3  -->
                               (((3) (2) (1)) ((3 2) (1)) ((3) (2 1)) ((2 3 1)) ((3 2 1)) ((2) (3 1)))"]
                  :runs cli/get-gr-mems}
                 
                 {:command "get-conj-cls"
                  :description "Get Sn group broken down into conjugacy classes"
                  :examples ["./gu get-conj-cls 3  -->
                               
                              {(1 1 1) [[e ((3) (2) (1))]],
                               (1 2)   [[g1 ((3 2) (1))] [g4 ((3) (2 1))] [g5 ((2) (3 1))]],
                               (3)     [[g3 ((2 3 1))] [g4 ((3 2 1))]]}"]
                  :runs cli/get-conj-classes}
                 
                  {:command     "cayley"
                   :description "Print the Cayley-table for Sn group, N is command's argument"
                   :examples ["cayley 2 -->
                               
                               Group members:  
                               {e (1 2), g1 (2 1)}

                               Cayley table: 
                               |    | :e | g1 |
                               |----+----+----|
                               | :e | :e | g1 |
                               | g1 | g1 | :e |"
                              "                   
                              "
                              "For N larger 3 you can use redirection
                               e.g. N = 4:
                               ./gu cayley 4 >> out.txt"]
                   :runs cli/cayley}
                 ]} )

(defn -main 
  [& args]
  (run-cmd args CONF))