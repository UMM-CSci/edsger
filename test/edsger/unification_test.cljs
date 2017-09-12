(ns edsger.unification-test
  (:require [edsger.unification :as u]
            [cljs.test :as t :refer-macros [deftest is] :include-macros true]))

(defn falsey? [x] (or (nil? x) (false? x)))

(deftest a-test
  (is (true? (u/check-match '(:not (:equiv u (:or w y)))
                            '(:equiv (:not u) (:or w y))
                            '(:not (:equiv ?a ?b))
                            '(:equiv (:not ?a) ?b)))))

(deftest completely-inapplicable-rule
  (is (falsey? (u/check-match '(:equiv (:and p q) p q)
                             '(:or p q)
                             '(:equiv (:and ?p ?q) ?p)
                             '(:equiv ?q (:or ?p ?q))))))


(deftest proving-3_4-step-1
  (is (true? (u/check-match '(true)
                            '(:equiv q q)
                            '(true)
                            '(:equiv ?q ?q)))))

(deftest proving-3_4-step-1-badv1
  (is (falsey? (u/check-match '(true)
                              '(:equiv p q)
                              '(true)
                              '(:equiv ?q ?q)))))

(deftest proving-3_4-step-1-badv2
  (is (falsey? (u/check-match '(false)
                              '(:equiv q q)
                              '(true)
                              '(:equiv ?q ?q)))))

