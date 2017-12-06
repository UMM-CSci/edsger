(ns edsger.parsing-test
  (:require [edsger.parsing :as p]
            [clojure.test :as t :refer-macros [deftest is are] :include-macros true]))


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
    "¬ true" [:top-level [:not [:boolean "true"]]]
    "a ⇒ false" [:top-level [:implies [:variable "a"] [:boolean "false"]]]
    "p ≡ q" [:top-level [:equiv
                         [:variable "p"]
                         [:variable "q"]]]
    "t ∧ u" [:top-level [:and
                         [:variable "t"]
                         [:variable "u"]]]))

(deftest infix-cfg_complex-nexting-with-parens-works
  (is (=
       (p/infix-cfg "(a ⇒ false) ∨ (¬ (t ∧ u))")
       [:top-level
        [:or
         [:implies
          [:variable "a"]
          [:boolean "false"]]
         [:not
          [:and
           [:variable "t"]
           [:variable "u"]]]]])))

(deftest infix-cfg_spaces-with-not
  (are [input] (= (p/infix-cfg input) [:top-level
                                       [:not
                                        [:boolean "true"]]])
    "¬ true"
    "¬true"))

(deftest infix-cfg_sequential-ands
  (is (= (type (p/infix-cfg "p ∧ q ∧ r")) instaparse.gll/Failure))
  (is (= (p/infix-cfg "p ∧ (q ∧ r)") [:top-level
                                       [:and [:variable "p"]
                                        [:and [:variable "q"] [:variable "r"]]]]))
  (is (= (p/infix-cfg "(p ∧ q) ∧ r") [:top-level
                                        [:and
                                         [:and [:variable "p"] [:variable "q"]]
                                         [:variable "r"]]])))

(deftest infix-cfg_sequential-ors
  (is (= (type (p/infix-cfg "p ∨ q ∨ r")) instaparse.gll/Failure))
  (is (= (p/infix-cfg "p ∨ (q ∨ r)") [:top-level
                                       [:or [:variable "p"]
                                        [:or [:variable "q"] [:variable "r"]]]]))
  (is (= (p/infix-cfg "(p ∨ q) ∨ r") [:top-level
                                       [:or
                                        [:or [:variable "p"] [:variable "q"]]
                                        [:variable "r"]]])))

(deftest infix-cfg_mixed-and-with-or
  (is (= (type (p/infix-cfg "p ∧ q ∨ r")) instaparse.gll/Failure))
  (is (= (p/infix-cfg "p ∧ (q ∨ r)") [:top-level
                                      [:and [:variable "p"]
                                       [:or [:variable "q"] [:variable "r"]]]]))
  (is (= (p/infix-cfg "(p ∧ q) ∨ r") [:top-level
                                      [:or
                                       [:and [:variable "p"] [:variable "q"]]
                                       [:variable "r"]]])))

(deftest infix-cfg_sequential-equivs
  (is (= (type (p/infix-cfg "p ≡ q ≡ r")) instaparse.gll/Failure))
  (is (= (p/infix-cfg "p ≡ (q ≡ r)") [:top-level
                                      [:equiv [:variable "p"]
                                       [:equiv [:variable "q"] [:variable "r"]]]]))
  (is (= (p/infix-cfg "(p ≡ q) ≡ r") [:top-level
                                      [:equiv
                                       [:equiv [:variable "p"] [:variable "q"]]
                                       [:variable "r"]]])))

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


;; ==== Tests for `parse`

(deftest parse_good
  (are [input output] (= (p/parse input) output)
    "a ∨ (b ∧ c)" '(:or a (:and b c))
    "¬ false" '(:not false)))

(deftest parse_bad
  (is (nil? (p/parse "(:or a)"))))

(deftest parse_binary-op_whitespace-agnostic
  (are [expr] (= (p/parse expr) [:and 'p 'r])
    "p∧r"
    "p ∧ r"
    " p ∧ r "
    " p∧ r"
    "  p∧ r"
    "p∧ r    "))

(deftest parse_simple_whitespace-agnostic
  (are [form1 form2] (and (not (nil? form1))
                          (not (nil? form2))
                          (= (p/parse form1) (p/parse form2)))
    "true" " true\t"
    "a" "a\r\n"
    "false" "   false  \t  "))

(deftest parse_unary-op_whitespace-agnostic
  (are [expr] (= (p/parse expr) [:not 'p])
    "¬p"
    "¬ p"
    " ¬p"
    "¬p "
    " ¬\tp\n"))

(deftest parse_nested_whitespace-agnostic
  (are [expr] (= (p/parse expr) [:or
                                 [:implies 'a false]
                                 [:not [:and 't 'u]]])
    "(a ⇒ false) ∨ (¬ (t ∧ u))"
    "(a⇒false)∨(¬(t∧u))"
    " ( a ⇒ false ) ∨ ( ¬ ( t ∧ u ) ) "))

;; ==== Tests for `rulify`

(deftest rulify_works-correctly
  (is (= '(:and ?b ?c (:or (:not ?a) ?c))
         (p/rulify '(:and b c (:or (:not a) c)))))
  (is (= '(:and true ?c (:or (:not ?a) false))
         (p/rulify '(:and true c (:or (:not a) false)))))
  (is (= '?q
         (p/rulify 'q)))
  (is (= 10
         (p/rulify 10))))
