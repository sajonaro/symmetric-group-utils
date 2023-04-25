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

;;;example
(conjat ['(1)] '('(3) '(4)))