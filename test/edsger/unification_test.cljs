(ns edsger.unification-test
  (:require [edsger.unification :as u]
            [cljs.test :as t :refer-macros [deftest is] :include-macros true]))

;; ==== Unit tests for `wrap`
(deftest wrap-with-symbol
  (is (= '(:a) (u/wrap :a))))

(deftest wrap-with-list
  (is (= '(2) (u/wrap '(2)))))


;; ==== Unit tests for `check-match`
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
                            '(:equiv ?q ?q)))
      "Empty `start-matches` map case"))

(deftest proving-3_4-step-1-naming
  (is (true? (u/check-match '(true)
                            '(:equiv p p)
                            '(true)
                            '(:equiv ?q ?q)))
      "The exact symbols don't need to match"))

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

(deftest proving-3_39-step-1
  (is (true? (u/check-match '(:equiv (:and p true) p)
                            '(:equiv (:or p true) true)
                            '(:equiv (:and ?p ?q) ?p)
                            '(:equiv (:or ?p ?q) ?q)))))

(deftest proving-3_39-step-1-ordering
  (is (not (u/check-match '(:equiv (:and p true) p)
                          '(:equiv (:or p true) true)
                          '(:equiv (:and ?p ?q) ?p)
                          '(:equiv ?q (:or ?p ?q))))
      "Ordering is crucial for matching with `binding-map`"))

(deftest start-matches-and-end-requires-no-substitution
  (is (true? (u/check-match '(:a-keyword q)
                            '(true)
                            '(:a-keyword ?p)
                            '(true)))
      "Empty `end-matches` case"))

(deftest constants-can-match
  (is (true? (u/check-match ':a ':b ':a ':b))))

(deftest constants-can-fail
  (is (not (u/check-match ':a ':c ':a ':b))))


;; ==== Unit tests for `check-match-recursive`
(deftest non-recursive-case
  (is (true? (u/check-match-recursive
              '(:not (:equiv u (:or w y)))
              '(:equiv (:not u) (:or w y))
              '(:not (:equiv ?a ?b))
              '(:equiv (:not ?a) ?b)))))

(deftest simple-recursive-case
  (is (true? (u/check-match-recursive
              '(:and p (:equiv true true))
              '(:and p true)
              '(:equiv ?q ?q)
              '(true)))))

(deftest rule-applies-but-not-actually-equal
  (is (not (u/check-match-recursive
            '(:and p (:equiv true true))
            '(:or p true)
            '(:equiv ?q ?q)
            '(true)))))

(deftest rule-applies-but-not-actually-equal-v2
  (is (not (u/check-match-recursive
            '(:and p (:equiv true true) false)
            '(:and p true true)
            '(:equiv ?q ?q)
            '(true)))))

(deftest diff-length-exps
  (is (not (u/check-match-recursive
            '(:and p (:equiv true true) false)
            '(:and p true)
            '(:equiv ?q ?q)
            '(true)))
      "Different lengths of expressions are handled correctly"))

(deftest non-seqable-start-or-end-expr
  (is (true? (u/check-match-recursive
              '(:and a a)
              'a
              '(:and ?p ?p)
              '?p)))
  (is (not (u/check-match-recursive
            '(:and a a)
            'b
            '(:and ?p ?p)
            '?p))))


;; The following tests are to test recursive-validate
;; Test on empty lists, should return true if both are empty, false if only one
(deftest empty-list-recurse-val
  (is (not (u/recursive-validate '() '())))
  (is (u/recursive-validate '("anything, just one thing") '()))
  (is (not (u/recursive-validate
          (list '(:and a a) 'a)
          '())))
  (is (not (u/recursive-validate '()
                  (list '(:and ?p ?p) '?p)))))

;; Test on recursive-validate on non-empty lists
(def rules-list (list '(:and ?a ?b) '(:and ?b a)
                       '(:or ?a ?b) '(:or ?b ?a)
                       '(:implies ?z ?q) '(:implies '(:not ?q) '(:not ?z))))
(def exps-list (list '(:implies '(:and a b) '(:or p q))
                     '(:implies '(:and b a) '(:or p q))
                     '(:implies '(:and b a) '(:or q p))
                     '(:implies '(:not '(:or q p)) '(:not '(:and b a)))))
(deftest recursive-validate-non-empty
  (is (u/recursive-validate exps-list rules-list))
  (is (not (u/recursive-validate exps-list (conj '(:or ?a ?b) (rest rules-list))))))
