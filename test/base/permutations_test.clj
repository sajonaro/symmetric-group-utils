(ns base.permutations-test
  (:require  [clojure.test :refer [deftest is testing]]
             [base.permutations :refer :all]))

;;;some tests
(deftest permutation-test-1
  (testing "gen-permutations"
    (is (= 6 (count (gen-permutations '(1 2 3))))))) ;;should be = 6

(deftest dot-test-1
  (testing "dot-test-1"
    (is 
     (and 
      (= '(2 3 1) (dot '(2 3 1) '(2 3 1) '(3 1 2)))
      (= '(3 1 2) (dot '(2 3 1) '(2 3 1)))
      (= '(2 3 1) (dot [1 2 3] [1 2 3] [2 3 1]))))))

(deftest ccl-to-pmtn-tests
  (testing "ccl-to-pmtn"
    (is
     (and
      (= '(2 3 4 5 6 7 1) (ccl-to-pmtn '((1 2 3 4 5 6 7))))
      (= '(1) (ccl-to-pmtn '((1))))
      (= '(2 1) (ccl-to-pmtn '((2 1))))
      (= '(3 2 1) (ccl-to-pmtn '((1 3) (2))))
      (= '(1 2 3) (ccl-to-pmtn '((1) (3) (2))))
      (= '(2 3 1) (ccl-to-pmtn '((2 3 1))))))))

(deftest dot-permutation-per-number-tests
  (testing "dot-permutation-per-number"
    (is
     (and
      (= 1 (dot-permutation-per-number '(2 3 1) 3))))))


(deftest dot-cycle-per-number-tests
  (testing "dot-cycle-per-number"
    (is
     (and
      (= 1 (dot-cycle-per-number '((2 3 1)) 3))))))

(deftest permutation-to-ccl-tests
  (testing "permutation-to-ccl"
    (is
     (and
      (= '((2) (3 1)) (permutation-to-ccl '(3 2 1)))
      (= '((3 2 1)) (permutation-to-ccl '(3 1 2)) ))
      (= '((4) (2) (3 1)) (permutation-to-ccl '(3 2 1 4))))))


((deftest conversion-test-tests
   (testing "Context of the test assertions"
     (is (= '(2 3 4 1 5 7 6) (ccl-to-pmtn (permutation-to-ccl '(2 3 4 1 5 7 6))))))))


(deftest dot-cycles-tests
  (testing "dot-cycles"
    (is
     (and
      (= '((3 2 1)) (dot-cycles '((1 2 3)) '((1 2 3))))
      (= '((2 3 1)) (dot-cycles '((1 2) (3)) '((1) (2 3))))
      (= '((3 2) (1)) (dot-cycles '((1 2) (3)) '((1 2 3))))
      (= '((2) (3 1)) (dot-cycles '((1 2 3)) '((1 2) (3))))
      (= '((3) (2) (1)) (dot-cycles '((1 3) (2)) '((1 3) (2))))))))

(deftest invert-tests
  (testing "invert"
    (is
     (and
      (= '(2 3 1) (invert '(3 1 2)))
      (= '(3 2 1) (invert '(3 2 1)))
      (= '(3 1 2) (invert '(2 3 1)))))))

(deftest ccl-to-matrix-tests
  (testing "ccl-to-matrix"
    (is (= [[0 1 0 0]
            [1 0 0 0]
            [0 0 1 0]
            [0 0 0 1]] (ccl-to-matrix [[1 2] [3] [4]])))))

(deftest pmtn-to-matrix-tests
      (testing "pmtn-to-matrix"
        (is (= [[0 1 0] [1 0 0] [0 0 1]] (pmtn-to-matrix [2 1 3]))))) 

(deftest take-nth-element-tests
      (testing "take-nth-element"
        (is (= (take-nth-element '(0 1 2) 3) '(1 0 2))))) 

(deftest apply-default-cycle-tests
      (testing "apply-default-cycle"
        (is (= ['(a b c d) '(d a b c) '(c d a b) '(b c d a)]
               (apply-default-cycle '(a b c d)))))) 

(deftest apply-default-cycle-tests
      (testing "apply-default-cycle"
        (is (= (apply-default-cycle '(1 2 3))
               ['(1 2 3) '(3 1 2) '(2 3 1)])))) 
