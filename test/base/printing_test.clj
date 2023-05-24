(ns base.printing-test
  (:require [base.printing :as print]
            [clojure.test :refer [deftest is testing]]))


(deftest permutation-to-mapset-test
      (testing "testing print/permutation-to-mapset function"
        (is (= (print/permutation-to-mapset '(2 3 1 4)) {:1 2, :2 3, :3 1, :4 4})))) 



;;;some tests
(print/permutation-print '(2 4 1 3))

(print/map-group-transform {:1 2 :3 4 :5 2} * 2)


;;some tests
(print/print-multiplication-table  {:1 1 :2 2 :3 3} *)



