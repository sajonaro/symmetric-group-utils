(ns permutations)

(defn gen-permutations [coll]
  "generate a list of all possible permutations of elements in collection
   for exaple, ( 1 2 3) -> ( (1 2 3)(3 2 1)(1 3 2)(2 1 3) (2 3 1))"
  (lazy-seq
   (if (seq (rest coll))
     (apply concat (for [x coll]
                     (map #(cons x %)
                          (gen-permutations (remove #{x} coll)))))
     [coll])))


;;;some tests
(count (gen-permutations '(1 2 3)))