(ns base.sets)


(defn has? 
  "True if `col` contains `el`, false otherwise."
  [col el]
  (loop[tmp col]
   (if (and (seq tmp) (not= (first tmp) el))
     (recur (rest tmp))
     (if (seq tmp)
       true
       false))))



(defn get-complement[set subset]
  "Get a complement for a `subset` in a given `set`
   e.g:
   [a b c],[b] -> [a c]"
  (filter #(not (has? subset %)) set))


;;some tests
(get-complement [1 2 3 4]  [3 2 1])
(has? [1 2 3] 5)

 