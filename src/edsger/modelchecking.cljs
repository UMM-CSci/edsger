(ns edsger.modelchecking)
(ns powerset.core-test
  (:use (clojure set test)))

(defn modelcheck
   
   (parse expression)

    ;;find the variables and create a set

    (let [op (first expr)]
        (if ( = op variable)
            (set (second expr))
            (if (= op :not)
                (variables (second expr))
                (set/union (variables (second expr))
                            (variables (third expr))
                )
            )
        )
    )
   


    ;; Create a powerset of the set s containing all variables

    (defn powerset [s]
        ;; this information was on the github https://gist.github.com/dekellum/4171049
        (apply union
         #{s} ;the complete set of all s
         (map (fn [i] (powerset (disj s i))) s)))


    (eval-expr expr true-vars
        (if(seq? expr)
            (eval-var expr true-vars)))
    (eval-var [v true-vars]
        (contains? true-vars v))
    (if(=:variable (first expr) 
        (eval-var(second expr) true-vars)))


  ;; evaluate expr if all true then it is correct

    (if ( = false (map #(eval-expr expr %) ps))
        (print "invalid rule")
    )
    (else( print "valid rule"))
)

(defn check
    ;; parse the expr
    (parse expression)
    ;; Search for all variables in the rule after parsed

   
    
    ;; Test using every combination of powerset
        ;; create a loop i =0 to 2^(number of elements in a set)
        
        ;;all vars should initialize as false at the beginning of loop

    ;; set vars in true-vars as true
    (def true-vars map(i)
        
    )
    
    ;; Use the functions and return boolean
    (if(=:and)
        (and var var))
    (if(=:or)
        (or var var))
    (if(=:implies)
        (implies var var))
    (if(=:not)
        (not var))
    (if(=:equiv)
        (equiv var var))

   
    ;; only boolean or var if the rule or expression as completely returned all values.
    (if(var)
         ;; If any of the final booleans return false then rule is invalid
        (is false then exit))
        ;;also should print invalid or say invalid and that rule does not evaluate correctly

    ;; If all of the final booleans return true then rule is valid
        ;; should have validate button or something say it is correct.
    

   

        
  

)

