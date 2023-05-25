(ns build
  (:refer-clojure :exclude [test])
  (:require [org.corfield.build :as bb]))

(def lib 'net.clojars.sajonaro/grutils)
(def version "0.1.0-SNAPSHOT")
(def main 'ep)

(defn test "Run the tests." [opts]
  (bb/run-tests opts))

(defn clean [opts]
     (bb/clean opts))

(defn ci 
  "Run the CI pipeline of tests (and build the uberjar)."
  [opts]
  (-> opts
      (assoc :lib lib :version version :main main)
      ;;;(bb/run-tests)
      (bb/clean)
      (bb/uber)))
