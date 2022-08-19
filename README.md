# symmetric-group-utils
functions helping explore problems related to symmetric group Sn  (https://en.wikipedia.org/wiki/Symmetric_group)


<br/>
<br/>

### how to use this library?

- reference io.github.sajonaro/symmetric-group-utils library via deps.edn
- reference needed namespaces from the library in your client source code
<br/>

example:


- in deps.edn:
```
{:deps
 {io.github.sajonaro/symmetric-group-utils {:git/tag "v0.0.1" :git/sha "cb6762a"}}}
``` 

- in client code (e.g. src/tst.clj file):
```
;;;reference partitions's namespace functionality
(ns tst
  (:require [partitions :as ptns])) 
  
;;; use gen-partitions function from library
(defn run [opts] 
  (print (count (ptns/gen-partitions 10)))) 
```
- to quickly test above code via cli (N.B! tst.clj must be in src folder by default for clj to pick it up):
 ```
  clj -X tst/run
 
 ```
