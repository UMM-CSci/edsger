(ns edsger.test-runner
  (:require  [doo.runner :refer-macros [doo-tests]]
             [edsger.substitution-test]
             [edsger.unification-test]
             [edsger.parsing-test]
             [edsger.failure]))

;; This enables printing of some sort during tests?
(enable-console-print!)

(doo-tests
 'edsger.substitution-test
 'edsger.unification-test
 'edsger.parsing-test
 'edsger.failure)

