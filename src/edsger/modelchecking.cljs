(ns edsger.modelchecking)
(ns powerset.core-test
  (:use (clojure set test)))

(defn modelcheck [let [expr (hiccup-tree)]]

  ;; evaluate expr if all true then it is correct

    (if (some false (map #(eval-expr expr %) powerset[(variables)]))
        (print "invalid rule")
    )
    (else( print "valid rule"))
)

(defn eval-expr
    (eval-expr expr true-vars
        (if(seq? expr)
            (eval-var expr true-vars)))
    (eval-var [v true-vars]
        (contains? true-vars v))
    (if(=:variable (first expr) 
        (eval-var(second expr) true-vars)))
  )


 ;; Create a powerset of the set s containing all variables
(defn powerset [s]
        ;; this information was on the github https://gist.github.com/dekellum/4171049
        (apply union
         #{s} ;the complete set of all s
         (map (fn [i] (powerset (disj s i))) s)))

  ;;find the variables and create a set
  
(defn variables
    (let [op (first expr)]
            (if ( = op variable)
                (set (second expr))
                (if (= op :not)
                    (variables (second expr))
                    (union (variables (second expr))
                            (variables (third expr))
                     )
                 )
            )
    )
)
