(ns edsger.parsing-test
  (:require [edsger.parsing :as p]
            [clojure.test :as t :refer-macros [deftest is are] :include-macros true]))


;; ==== Tests for `lisp-style-cfg`

(deftest lisp-style-cfg_constant-values
  (are [input output] (= (p/lisp-style-cfg input) output)
    "c" [:S [:E "c"]]
    "true" [:S [:D "true"]]))

(deftest lisp-style-cfg_single-operand
  (are [input output] (= (p/lisp-style-cfg input) output)
    "(not a)" [:S "(" [:B "not"] " " [:S [:E "a"]] ")"]
    "(not false)" [:S "(" [:B "not"] " " [:S [:D "false"]] ")"]))

(deftest lisp-style-cfg_two-operand
  (are [input output] (= (p/lisp-style-cfg input) output)
    "(or false b)" [:S "("
                    [:C "or"] " "
                    [:S [:D "false"]] " "
                    [:S [:E "b"]] ")"]
    "(equiv a b)" [:S "("
                   [:C "equiv"] " "
                   [:S [:E "a"]] " "
                   [:S [:E "b"]] ")"]))

(deftest list-style-cfg_unparsable-input
  (are [input] (= (type (p/lisp-style-cfg input)) instaparse.gll/Failure)
    ")"
    "()"
    "(true false)"
    "and a b"
    "(and (a) (b) (c))"))


;; ==== Tests for `infix-cfg`

(deftest infix-cfg_single-value-expression
  (are [input expected-output] (= (p/infix-cfg input) expected-output)
    "true" [:top-level [:boolean "true"]]
    "false" [:top-level [:boolean "false"]]
    "a" [:top-level [:variable "a"]]
    "m" [:top-level [:variable "m"]]
    "z" [:top-level [:variable "z"]]))

(deftest infix-cfg_one-level-operations
  (are [input expected-output] (= (p/infix-cfg input) expected-output)
    "¬ true" [:top-level [:unary-expr [:unary-op "¬"] [:bottom [:boolean "true"]]]]
    "a ⇒ false" [:top-level [:binary-expr
                             [:bottom [:variable "a"]]
                             [:binary-op "⇒"]
                             [:bottom [:boolean "false"]]]]
    "p ≡ q" [:top-level [:binary-expr
                         [:bottom [:variable "p"]]
                         [:binary-op "≡"]
                         [:bottom [:variable "q"]]]]
    "t ∧ u" [:top-level [:binary-expr
                         [:bottom [:variable "t"]]
                         [:binary-op "∧"]
                         [:bottom [:variable "u"]]]]))

(deftest infix-cfg_complex-nexting-with-parens-works
  (is (=
       (p/infix-cfg "(a ⇒ false) ∨ (¬ (t ∧ u))")
       [:top-level [:binary-expr
                    [:bottom
                     [:top-level
                          [:binary-expr
                           [:bottom [:variable "a"]]
                           [:binary-op "⇒"]
                           [:bottom [:boolean "false"]]]]]
                    [:binary-op "∨"]
                    [:bottom
                     [:top-level
                          [:unary-expr
                           [:unary-op "¬"]
                           [:bottom
                                [:top-level
                                 [:binary-expr
                                  [:bottom [:variable "t"]]
                                  [:binary-op "∧"]
                                  [:bottom [:variable "u"]]]]]]]]]])))


;; ==== Tests for `transform-infix-cfg`

(deftest transform-infix-cfg_works-with-infix-cfg
  (are [input expected-output] (= (p/transform-infix-cfg (p/infix-cfg input)) expected-output)
    "true" true
    "false" false
    "a" 'a
    "m" 'm
    "z" 'z
    "¬ true" [:not true]
    "a ⇒ false" [:implies 'a false]
    "p ≡ q" [:equiv 'p 'q]
    "t ∧ u" [:and 't 'u]
    "(a ⇒ false) ∨ (¬ (t ∧ u))" [:or
                                 [:implies 'a false]
                                 [:not [:and 't 'u]]]))

;; ==== Tests for `mk-list`

(deftest mk-list_strips-trees-properly
  (are [input output] (= (p/mk-list input) output)
    [:S "("
     [:C "equiv"] " "
     [:S [:E "a"]] " "
     [:S [:E "b"]] ")"]
    '(:equiv a b)

    [:S "("
     [:C "or"] " "
     [:S [:D "false"]] " "
     [:S [:E "b"] ")"]]
    '(:or false b)

    [:S [:D "true"]]
    true))


;; ==== Tests for `parse`

(deftest parse_good
  (are [input output] (= (p/parse input) output)
    "(or a (and b c))" '(:or a (:and b c))
    "(not false)" '(:not false)))

(deftest parse_bad
  (is (nil? (p/parse "(:or a)"))))


;; ==== Tests for `rulify`

(deftest rulify_works-correctly
  (is (= '(:and ?b ?c (:or (:not ?a) ?c))
         (p/rulify '(:and b c (:or (:not a) c)))))
  (is (= '(:and true ?c (:or (:not ?a) false))
         (p/rulify '(:and true c (:or (:not a) false))))) )
