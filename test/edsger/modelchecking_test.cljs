(ns edsger.modelchecking-test
:require [edsger.modelchecking :as m]
[clojure.test :as t :refer-macros [def-test is are]:include-macros true])


;; ===== find variables using :variable
     
;; ===== find operators
       ;; use the operator info to call correct operator on two variables


;; ===== create-sets

(deftest test-set
    (is (= #{a b}
            (union #{a} #{b})))

)

;; ==== Tests for powerset
(ns powerset.core-test
  (:use (clojure set test)))

(defn powerset [s]
;; this information was on the github https://gist.github.com/dekellum/4171049
  (apply union
         #{s} ;the complete set of all s
         (map (fn [i] (powerset (disj s i))) s)))

(deftest test-powerset
  (is (= #{#{}}
         (powerset #{})))
  (is (= #{ #{} #{1}}
         (powerset #{1})))
  (is (= #{ #{} #{1} #{2} #{1 2}}
         (powerset #{1 2})))
  (is (= #{#{} #{1} #{2} #{1 2} #{3} #{1 3} #{2 3} #{1 2 3}}
         (powerset #{1 2 3}))))

;; ===== setting true-vars values
(deftest true-vars
       vars true-vars 
)

;; ===== checking operators

(deftest and
       (and true true)
       ;;==== true
       (else return false)
)

(deftest or 
       (or false false)
       ;; === false
       (else return true)
)

(deftest implies
       (if :implies true true
       return true)
       (if :implies false
       return true)
       (else return false)

 ;; false followed by anything is true
 ;; true must be followed by false
)    

(deftest not
       (not true)   
       ;; ===false
       (not false)
       ;; ===true
)

(deftest equiv
       (equiv true true)
       (equiv false false)
       (else return false)

)