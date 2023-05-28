(ns algebra.symmetry-groups-test
  (:require  [clojure.test :refer [deftest is testing]]
             [algebra.symmetry-groups :as sg]))


(deftest get-order-p-test
  (testing "testing  order of a permutation"
    (is (= (sg/get-order-p '(2 3 1))  3))))
