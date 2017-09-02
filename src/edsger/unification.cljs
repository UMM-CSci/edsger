(ns edsger.unification
  (:require [edsger.unification-macros :refer [match-rule] :include-macros true]))

;; This file is currently only for playing around during development
;; but I think that we'll eventually have some useful functions here.

;; When this file is evaluated, the
;; code here is executed and anything that is (print)-ed will
;; be printed in the developer console in Chrome when using figwheel.
;; This means we can just make a change, save, and then switch to
;; Chrome to see what printed.

(print "BBORK!\nPRE") ;; I like a header for the section

;; Currently there is just a bunch of random stuff in here.

;; (print (macroexpand-1 '(match-rule '(:+ (:* w (:+ 2 3)) 0) '(:+ x 0))))
;; (print (macroexpand-1 '(match-rule '(:+ (:* w (:+ 2 3)) 0) 'x)))
(print (macroexpand-1 '(match-rule '(:* (:- 10 3) (:+ a b)) '(:* x y))))
;; (print (match-rule '(:* (:- 10 3) (:+ a b)) '(:* x y)))
;; (print
;;  (cljs.core.logic/run* [q]
;;    (cljs.core.logic/fresh [x y expression-sym rule-sym]
;;      (cljs.core.logic/== expression-sym (quote (:* (:- 10 3) (:+ a b))))
;;      (cljs.core.logic/== rule-sym (clojure.core/list :* x y))
;;      (cljs.core.logic/== expression-sym rule-sym)
;;      (cljs.core.logic/== q [x y]))))
;; (print (match-rule '(:+ (:* w (:+ 2 3)) 0) 'x))
;; (print (match-rule '(:+ (:* w (:+ 2 3)) 0) '(:+ x 0)))
;; (print (match-rule '(:+ (:* w (:+ 2 3)) 0) '(:+ x 0)))
;; (print
;;  (cljs.core.logic/run* [q]
;;    (cljs.core.logic/fresh
;;      [x expression-sym rule-sym]
;;      (cljs.core.logic/== expression-sym (quote (:+ (:* w (:+ 2 3)) 0)))
;;      (cljs.core.logic/== rule-sym (clojure.core/list :+ x 0))
;;      (cljs.core.logic/== expression-sym rule-sym) (cljs.core.logic/== q x))))
;; (print (seq (match-rule '(:+ (:* w (:+ 2 3)) 0) '(:+ x 0))))
;; (print (unifier/unify'(:+ (:* w (:+ 2 3)) 0) '(:+ ?x 0) ))
;; (print (run* [q] (fresh ['x rule other] (== rule (list :+ 'x 0)) (== other '(:+ (:* w (:+ 2 3)) 0)) (== rule other) (== q 'x))))

(print "POST\nBBORK!") ;; I like a footer for the section


