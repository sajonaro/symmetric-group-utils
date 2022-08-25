(ns permutations)

(defn gen-permutations [coll]
  "generate a list of all possible permutations of elements in collection
   for exaple, (1 2 3) -> ((1 2 3)(3 2 1)(1 3 2)(2 1 3) (2 3 1)(3 1 2))"
  (lazy-seq
   (if (seq (rest coll))
     (apply concat (for [x coll]
                     (map #(cons x %)
                          (gen-permutations (remove #{x} coll)))))
     [coll])))

;;;some tests
(count (gen-permutations '(1 2 3))) ;;should be = 6


(defn single-cycle-to-permutation-helper [c]
  "hanldes conversion of cycle to permuatation notation
   in case when cycle is a composed of a single cycle
   e.g. (1 2 3) or (1 2) etc"
  (loop[res {} ccl c]
    (cond
      (and ;;;weed out edge case 1
       (= 1 (count ccl))
       (= 0 (count res)))
        (assoc res (keyword (str (first ccl))) (first ccl))

      (and ;;;weed out edge case 2
       (= 2 (count ccl))
       (= 0 (count res)))
        (assoc (assoc res (keyword (str (last ccl))) (first ccl)) (keyword (str (first ccl))) (last ccl))

      (and
       (= 2 (count ccl))
       (>   (count res) 1))
        (assoc res (keyword (str (first ccl))) (last ccl))

      :else
        (recur (assoc (assoc res (keyword (str (last ccl))) (first c)) (keyword (str (first ccl))) (second ccl)) (rest ccl)))))   


(defn cycle-to-permutation [c]
  "converts a group Sn element from cycle to 
   permutation notation
   e.g. for G = S3 cycles -> permutations we have:
   '((1 2)(3))='((2 1)(3))='((3)(2 1))..   -> (2 1 3)
   '((1 3)(2))='((3 1)(2))='((2)(1 3))..   -> (3 2 1)
   '((1)(2 3))='((2 3)(1))='((3 2)(1))..   -> (1 3 2)   
   '((1 2 3))='((3 1 2))='((2 3 1))        -> (2 3 1)
   '((3 2 1))='((1 3 2))='((2 1 3))        -> (3 1 2)
   '((1)(2)(3))='((3)(2)(1))..             -> (1 2 3)
   Note, cycle is evaluated from left to right  "
  (vals 
   (into 
    (sorted-map)
     (apply 
       merge
        (map single-cycle-to-permutation-helper c)))))
  

;;;some tests
(cycle-to-permutation '((1 2 3 4 5 6 7)))
(cycle-to-permutation '((1)))
(cycle-to-permutation '((2 3)))
(cycle-to-permutation '((1 3)(2)))
(cycle-to-permutation '((1)(3)(2)))
(cycle-to-permutation '((3 2 1)))


(defn muliply-cycles-to-permutation[c1 c2]
  "Symmetric group * operation, i.e. multiplication of two permutations
   defined as c1*c2 = p3,  such as p3 is the permutation equivalent to applying 
   consecutively permutaion c1 after c2 ( right to left).
   c1,c2 are defined in cycle notation
   examples:
   c1=(1 2)(3), c2 = (1)(2 3) ->  (2 3 1)
   (1 2)(3), (1 2 3)          ->  (1 3 2)
   (1 2 3),(1 2)(3)           ->  (3 2 1)
   (1 3)(2),(1 3)(2)          ->  (1 2 3)
   (1 2 3)(1 2 3)             ->  (3 1 2)  
   "
  (let[a (cycle-to-permutation c1)
       b (cycle-to-permutation c2)]
    (map #(nth a (dec %)) b)))

;;;some tests
(muliply-cycles-to-permutation '((1 2 3)) '((1 2 3)))
(muliply-cycles-to-permutation '((1 2)(3)) '((1)(2 3)))
(muliply-cycles-to-permutation '((1 2)(3)) '((1 2 3)))
(muliply-cycles-to-permutation '((1 2 3)) '((1 2)(3)))
(muliply-cycles-to-permutation '((1 3)(2)) '((1 3)(2)))


(defn multiply-permutations [p1 p2]
  "Symmetric group operation *, i.e. multiplication of two permutations
   defined as p1*p2 = p3,  such as p3 is the permutation equivalent to 
   consecutively applying  p1 after p2 ( right to left).
   p1,p2 are defined in 'permutation' notation
   examples:
   (1 2 3), (3 2 1)           ->  (3 2 1)
   "
    (map #(nth p1 (dec %)) p2))


(multiply-permutations '( 1 2 3) '(3 2 1))