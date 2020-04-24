(ns edsger.modelchecking
  "Tools for model checking the validity of entered theorems"
  (:use [clojure.set]))

(defn modelcheck [expr]
    
  ;; evaluate expr if all true then it is correct

    (some false (map #(eval-expr [expr %]) (powerset[(variables[expr])])))
    
)

; (eval-expr 'a #{'a 'b 'c}) => true
; (eval-expr 'x #{'a 'b 'c}) => false
;
; (eval-expr [:and 'a 'b] #{'a 'b 'c}) => true
; (eval-expr [:and 'a 'x] #{'a 'b 'c}) => false
; 
; (eval-var 'a #{'a 'b 'c}) => true
; (eval-var 'x #{'a 'b 'c}) => false


(defn eval-expr [expr true-vars]
    (if (symbol? expr)
        (eval-var expr true-vars)
        (let [args (map #(eval-expr % true-vars) (rest expr))
            (if (= :not (first expr)
                (not (first args)
                (if (= :and (first expr))
                    (and (first args) (second args))
                    (if (= :or (first expr))
                        (or (first args)(second args))
                        (if (= :implies (first expr))
                            (implies (first args)(second args))
                            (if (= :equiv (first expr))
                                (equiv (first args)(second args)))))))))])))

;(defn eval-expr [expr true-vars]
;   (if (symbol? expr)
;       (eval-var expr true-vars)
;       let [args (map #(eval-expr % true-vars) (rest expr))
;            first-arg (first args)
;            second-arg (second args)]
;           (condp = first expr)
;               :not (not first-arg second-arg)
;               :equiv (equiv first-arg second-arg)
;               :implies (implies [first-arg] [second-arg])
;               :and (and first-arg second-arg)
;               :or (or first-arg second-arg)
;               (str "not a valid operator")))))
;               
;   )
;
;
;
;

;)



 ;; Create a powerset of the set s containing all variables
(defn powerset [s]
        ;; this information was on the github https://gist.github.com/dekellum/4171049
        (apply union
         #{s} ;the complete set of all s
         (map (fn [i] (powerset (disj s i))) s)))

  ;;find the variables and create a set
 
(defn variables [expr]
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

(defn implies [arg0] [arg1]
    (if (arg0)true
        (if (arg1)false
        false
        )
    )
    else(
        true)

)

(def op
  {:not
   :or
   :and
   :equiv
   :implies}
)
