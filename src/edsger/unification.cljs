(ns edsger.unification
  (:require [cljs.core.logic :as logic :refer [binding-map]]))

;; This file is currently only for playing around during development
;; but I think that we'll eventually have some useful functions here.

;; When this file is evaluated, the
;; code here is executed and anything that is (print)-ed will
;; be printed in the developer console in Chrome when using figwheel.
;; This means we can just make a change, save, and then switch to
;; Chrome to see what printed.

(print "BBORK!\nPRE") ;; I like a header for the section

(print (binding-map '(+ ?x 1) '(+ 1 1)))


(print "POST\nBBORK!") ;; I like a footer for the section


