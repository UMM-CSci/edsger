(ns edsger.unification-macros-test
  (:require [clojure.test :as t :refer-macros [deftest is]]
            [edsger.unification-macros :refer-macros [match-rule]]))

;; This test demonstrates unification of the two expressions
;; that Nic showed us in our last meeting.
(deftest match-rule-demo-0
  (is (= '([(:* w (:+ 2 3))])
         (match-rule '(:+ (:* w (:+ 2 3)) 0) '(:+ x 0)))))

;; This test demonstrates unification where the rule is
;; just a single free variable
(deftest match-rule-demo-1
  (is (= '([(:+ (:* w (:+ 2 3)) 0)])
         (match-rule '(:+ (:* w (:+ 2 3)) 0) 'x))))

;; This test demonstrates how the output is not useful
;; when the rule has two free variables. What binds to what
;; in the output? We'll solve this in the future by returning
;; a map.
(deftest match-rule-demo-2
  (is (= '([(:- 10 3) (:+ a b)])
         (match-rule '(:* (:- 10 3) (:+ a b)) '(:* x y)))))


