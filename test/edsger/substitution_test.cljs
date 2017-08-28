(ns edsger.substitution-test
  (:require [edsger.substitution :as subst]
            [clojure.test :as t :refer-macros [deftest is]]))

(deftest highlight-elements-empty-list
  (is (= '() (subst/highlight-elements '()))))

(deftest highlight-elements-one-element
  (is (= '((1)) (subst/highlight-elements '(1)))))

(deftest highlight-elements-two-elements
  (is (= '((1 2) (2 1)) (subst/highlight-elements '(1 2)))))

(deftest highlight-elements-three-elements
  (is (= '((1 2 3) (2 3 1) (3 1 2))
         (subst/highlight-elements '(1 2 3)))))

(deftest verify-substitution-simple-success
  (is (= true
         (subst/verify-substitution [:a] [:eqv :a :b] [:b]))))

(deftest verify-substitution-simple-failure
  (is (= nil
         (subst/verify-substitution [:a] [:eqv :a :b] [:c]))))

