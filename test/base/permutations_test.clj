(ns base.permutations-test
  (:require  [clojure.test :refer [deftest is testing]]
             [base.permutations :as perm :refer :all]))

;;;some tests
(deftest permutation-test-1
  (testing "gen-permutations"
    (is (= 6 (count (perm/gen-permutations '(1 2 3))))))) ;;should be = 6

(deftest dot-test-1
  (testing "dot-test-1"
    (is 
     (and 
      (= '(2 3 1) (perm/dot '(2 3 1) '(2 3 1) '(3 1 2)))
      (= '(3 1 2) (perm/dot '(2 3 1) '(2 3 1)))
      (= '(2 3 1) (perm/dot [1 2 3] [1 2 3] [2 3 1]))))))


;;TODO fix thid case (perm/ccl-to-pmtn '((2 1) (2 3))!!
(deftest ccl-to-pmtn-tests
  (testing "ccl-to-pmtn"
    (is
     (and
      (= '(2 3 4 5 6 7 1) (perm/ccl-to-pmtn '((1 2 3 4 5 6 7))))
      (= '(1) (perm/ccl-to-pmtn '((1))))
      (= '(2 1) (perm/ccl-to-pmtn '((2 1))))
      (= '(3 2 1) (perm/ccl-to-pmtn '((1 3) (2))))
      (= '(1 2 3) (perm/ccl-to-pmtn '((1) (3) (2))))
      (= '(2 3 1) (perm/ccl-to-pmtn '((2 3 1))))
      (= '(2 3 1) (perm/ccl-to-pmtn '((2 1) (2 3))))))))


(deftest dot-permutation-per-number-tests
  (testing "dot-permutation-per-number"
    (is
     (and
      (= 1 (perm/dot-permutation-per-number '(2 3 1) 3))))))


(deftest dot-cycle-per-number-tests
  (testing "dot-cycle-per-number"
    (is
     (and
      (= 1 (perm/dot-cycle-per-number '((2 3 1)) 3))))))

(deftest permutation-to-ccl-tests
  (testing "permutation-to-ccl"
    (is
     (and
      (= '((2) (3 1)) (perm/permutation-to-ccl '(3 2 1)))
      (= '((3 2 1)) (perm/permutation-to-ccl '(3 1 2)) ))
      (= '((4) (2) (3 1)) (perm/permutation-to-ccl '(3 2 1 4))))))


((deftest conversion-test-tests
   (testing "Context of the test assertions"
     (is (= '(2 3 4 1 5 7 6) (perm/ccl-to-pmtn (permutation-to-ccl '(2 3 4 1 5 7 6))))))))


(deftest dot-cycles-tests
  (testing "dot-cycles"
    (is
     (and
      (= '((3 2 1)) (perm/dot-cycles '((1 2 3)) '((1 2 3))))
      (= '((2 3 1)) (perm/dot-cycles '((1 2) (3)) '((1) (2 3))))
      (= '((3 2) (1)) (perm/dot-cycles '((1 2) (3)) '((1 2 3))))
      (= '((2) (3 1)) (perm/dot-cycles '((1 2 3)) '((1 2) (3))))
      (= '((3) (2) (1)) (perm/dot-cycles '((1 3) (2)) '((1 3) (2))))))))

(deftest invert-tests
  (testing "invert"
    (is
     (and
      (= '(2 3 1) (perm/invert '(3 1 2)))
      (= '(3 2 1) (perm/invert '(3 2 1)))
      (= '(3 1 2) (perm/invert '(2 3 1)))))))

(deftest ccl-to-matrix-tests
  (testing "ccl-to-matrix"
    (is (= [[0 1 0 0]
            [1 0 0 0]
            [0 0 1 0]
            [0 0 0 1]] (perm/ccl-to-matrix [[1 2] [3] [4]])))))

(deftest pmtn-to-matrix-tests
      (testing "pmtn-to-matrix"
        (is (= [[0 1 0] [1 0 0] [0 0 1]] (perm/pmtn-to-matrix [2 1 3]))))) 

(deftest take-nth-element-tests
      (testing "take-nth-element"
        (is (= (perm/take-nth-element '(0 1 2) 3) '(1 0 2))))) 

(deftest apply-default-cycle-tests
      (testing "apply-default-cycle"
        (is (= ['(a b c d) '(d a b c) '(c d a b) '(b c d a)]
               (perm/apply-default-cycle '(a b c d)))))) 

(deftest apply-default-cycle-tests
      (testing "apply-default-cycle"
        (is (= (perm/apply-default-cycle '(1 2 3))
               ['(1 2 3) '(3 1 2) '(2 3 1)])))) 


(deftest perm-to-transp-test
  (testing "perm-to-transp-test"
          (is (= '((1) (6 5) (3 2) (3 4))
                 (perm/permutation-to-transposition '(1 3 4 2 6 5))))))


