(ns edsger.parsing-test
  (:require [edsger.parsing :as p]
            [clojure.test :as t :refer-macros [deftest is are] :include-macros true]))


;; ==== Tests for `lisp-style-cfg`

(deftest lisp-style-cfg_constant-values
  (are [input output] (= (p/lisp-style-cfg input) output)
    "(c)" [:S "(" [:A [:E "c"]] ")"]
    "(true)" [:S "(" [:A [:D "true"]] ")"]))

(deftest lisp-style-cfg_single-operand
  (are [input output] (= (p/lisp-style-cfg input) output)
    "(not (a))" [:S "(" [:B "not"] " " [:S "(" [:A [:E "a"]] ")"] ")"]
    "(not (false))" [:S "(" [:B "not"] " " [:S "(" [:A [:D "false"]] ")"] ")"]))

(deftest lisp-style-cfg_two-operand
  (are [input output] (= (p/lisp-style-cfg input) output)
    "(or (false) (b))" [:S "("
                        [:C "or"] " "
                        [:S "(" [:A [:D "false"]] ")"] " "
                        [:S "(" [:A [:E "b"]] ")"] ")"]
    "(equiv (a) (b))" [:S "("
                       [:C "equiv"] " "
                       [:S "(" [:A [:E "a"]] ")"] " "
                       [:S "(" [:A [:E "b"]] ")"] ")"]))

(deftest list-style-cfg_unparsable-input
  (are [input] (= (type (p/lisp-style-cfg input)) instaparse.gll/Failure)
    ")"
    "()"
    "(true false)"
    "and a b"
    "(and (a) (b) (c))"))


;; ==== Tests for `mk-list`

(deftest mk-list_strips-trees-properly
  (are [input output] (= (p/mk-list input) output)
    [:S "("
     [:C "equiv"] " "
     [:S "(" [:A [:E "a"]] ")"] " "
     [:S "(" [:A [:E "b"]] ")"] ")"]
    '(:equiv (a) (b))

    [:S "("
     [:C "or"] " "
     [:S "(" [:A [:D "false"]] ")"] " "
     [:S "(" [:A [:E "b"]] ")"] ")"]
    '(:or (false) (b))

    [:S "(" [:A [:D "true"]] ")"]
    '(true)
    ))


;; ==== Tests for `parse`

(deftest parse_good
  (are [input output] (= (p/parse input) output)
    "(or (a) (and (b) (c)))" '(:or (a) (:and (b) (c)))
    "(not (false))" '(:not (false))))

(deftest parse_bad
  (is (nil? (p/parse "(:or (a))"))))
