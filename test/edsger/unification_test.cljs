(ns edsger.unification-test
  (:require [edsger.unification :as u]
            [cljs.test :as t :refer-macros [deftest is] :include-macros true]))

(deftest wrap-with-symbol
  (is (= '(:a) (u/wrap :a))))

(deftest wrap-with-list
  (is (= '(2) (u/wrap '(2)))))

;;-----------------------------------------------------

(deftest a-test
  (is (true? (u/check-match '(:not (:equiv u (:or w y)))
                            '(:equiv (:not u) (:or w y))
                            '(:not (:equiv ?a ?b))
                            '(:equiv (:not ?a) ?b)))))

(deftest completely-inapplicable-rule
  (is (not (u/check-match '(:equiv (:and p q) p q)
                             '(:or p q)
                             '(:equiv (:and ?p ?q) ?p)
                             '(:equiv ?q (:or ?p ?q))))))


(deftest proving-3_4-step-1
  (is (true? (u/check-match '(true)
                            '(:equiv q q)
                            '(true)
                            '(:equiv ?q ?q)))))

(deftest proving-3_4-step-1-badv1
  (is (not (u/check-match '(true)
                              '(:equiv p q)
                              '(true)
                              '(:equiv ?q ?q)))))

(deftest proving-3_4-step-1-badv2
  (is (not (u/check-match '(false)
                              '(:equiv q q)
                              '(true)
                              '(:equiv ?q ?q)))))

(deftest start-matches-and-end-requires-no-substitution
  (is (true? (u/check-match '(:a-keyword q)
                            '(true)
                            '(:a-keyword ?p)
                            '(true)))))

(deftest constants-can-match
  (is (true? (u/check-match ':a ':b ':a ':b))))

(deftest constants-can-fail
  (is (not (u/check-match ':a ':c ':a ':b))))
