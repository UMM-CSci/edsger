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
