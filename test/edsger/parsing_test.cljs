(ns edsger.parsing-test
  (:require [edsger.parsing :as p]
            [clojure.test :as t :refer-macros [deftest is are] :include-macros true]))

;; ==== Tests for lisp-style-cfg

(deftest lisp-style-cfg_constant-values
  (are [input output] (= (p/lisp-style-cfg input) output)
    "(c)" [:S "(" [:A [:E "c"]] ")"]
    "(true)" [:S "(" [:A [:D "true"]] ")"]))

