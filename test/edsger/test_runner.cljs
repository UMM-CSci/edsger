(ns edsger.test-runner
  (:require  [clojure.test :refer-macros [run-tests]]
             [edsger.substitution-test]))

;; This enables printing of some sort during tests?
(enable-console-print!)

(defn run-all-tests
  []
  (run-tests 'edsger.substitution-test))

