(ns base.common)

;;contains general purpose utility functions
(defn distinct-by
  "Returns a stateful transducer that removes elements by calling f on each step as a uniqueness key.
   Returns a lazy sequence when provided with a collection.
   
   Borrowed from : @thenonameguy
   thenonameguy/distinct-by.clj

   "
  ([f]
   (fn [rf]
     (let [seen (volatile! #{})]
       (fn
         ([] (rf))
         ([result] (rf result))
         ([result input]
          (let [v (f input)]
            (if (contains? @seen v)
              result
              (do (vswap! seen conj v)
                  (rf result input)))))))))
  ([f xs]
   (sequence (distinct-by f) xs)))


;;;combination of conj and concat
;;; i.e. adds flattaned by one level 
;;;second col-b into col-a
(defn conjat [col_a col_b]
  (if (seq col_b)
    (conjat (conj col_a (first col_b)) (rest col_b))
    col_a))


(defn factorial
  [n]
  (loop[r n res 1]
   (if (= r 1)
     res
     (recur (dec r)  (* res r)))))


(defn map-val-transform 
  "transform a map `map-arg` with `transform-func` function"
  [transform-func map-arg]
  (into {} (for [[k v] map-arg]
             [k (transform-func v)])))

(defn exp-int
 "return x^n"
 [x n]
 (reduce * (repeat n x)))

(defn lazo
  "Boilerplate for (otherwise) boring loop
   repeating `n-times` times, starting point of res = `init-val`, `fn-trans` - function transforming
   res in each iteration of the loop, `fn-trans` takes res as argument and returns res
   
   e.g. to sum numbers from 1 to 10
   use (lazo inc 0 10) "
  [fn-trans init-val n-times]
  (loop[i 1 res init-val]
    (if (> i n-times)
      res 
    (recur (inc i) (fn-trans res)))))
