(ns edsger.test-runner
  (:require  [clojure.test :refer-macros [run-tests]]
             [edsger.substitution-test]
             [edsger.unification-test]
             [edsger.parsing-test]))

;; This enables printing of some sort during tests?
(enable-console-print!)

(defn run-all-tests
  []
  (run-tests 'edsger.substitution-test)
  (run-tests 'edsger.unification-test)
  (run-tests 'edsger.parsing-test))

