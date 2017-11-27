(ns edsger.test-runner
  (:require  [doo.runner :refer-macros [doo-tests]]
             [edsger.unification-test]
             [edsger.parsing-test]))

;; This enables printing of some sort during tests?
(enable-console-print!)

(doo-tests
 'edsger.unification-test
 'edsger.parsing-test)

