(ns edsger.unification
  (:require [cljs.core.logic :as logic :refer [binding-map]]))

(defn check-match [start-exp end-exp lhs rhs]
  "TODO"
  (let [start-matches (binding-map start-exp lhs)
        end-matches (binding-map end-exp rhs)]
    (and
     (not (empty? start-matches))
     (= start-matches end-matches))))

;; This file is currently only for playing around during development
;; but I think that we'll eventually have some useful functions here.

;; When this file is evaluated, the
;; code here is executed and anything that is (print)-ed will
;; be printed in the developer console in Chrome when using figwheel.
;; This means we can just make a change, save, and then switch to
;; Chrome to see what printed.

(print "BBORK!\nPRE") ;; I like a header for the section

(def a '(+ ?x 1))
(def b '(+ 1 1))

(def exp-start '(:not (:equiv u (:or w y))))
(def exp-end '(:equiv (:not u) (:or w y)))
(def lhs '(:not (:equiv ?a ?b)))
(def rhs '(:equiv (:not ?a) ?b))

(print (binding-map exp-start lhs))
(print (binding-map exp-end rhs))
(print (= (binding-map exp-start lhs)
          (binding-map exp-end rhs)))



(print "POST\nBBORK!") ;; I like a footer for the section


