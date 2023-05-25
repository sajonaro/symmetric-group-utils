(ns ep
  (:gen-class)
  (:require 
   [interface.cli-interface :as cli]
   [cli-matic.core :refer [run-cmd]]))


(defn x 
  [a]
  (println (str a)))

;;;definition of what CLI tool can do
(def CONF
  {:command     "grutils-cli"
   :description "A command-line symmetric group utility"
   :version     "0.0.1"
   :subcommands [          
                 {:command     "p2c"
                  :description "Convert a permutation into a cycle form "
                  :examples    ["./grutils-cli p2c 2 3 1      -->    (1 2 3)   "
                                "./grutils-cli p2c 2 1 4 3    -->    (1 2)(3 4)"]
                  :runs        cli/p2c}
                 
                 {:command     "c2m"
                  :description "Convert a cycle into matrix form 
                                i.e get a matrix representation for a cycle"
                  :examples [ "c2m '((1 2)(3))' -->
                               [[0 1 0]
                                [1 0 0]
                                [0 0 1]]"]
                  :runs cli/c2m }]} )

(defn -main 
  [& args]
  (run-cmd args CONF))