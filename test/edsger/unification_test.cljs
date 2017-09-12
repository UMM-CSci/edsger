(ns edsger.unification-test
  (:require [edsger.unification :as u]
            [cljs.test :as t :refer-macros [deftest is] :include-macros true]))

(deftest a-test
  (is (true? (u/check-match '(:not (:equiv u (:or w y)))
                            '(:equiv (:not u) (:or w y))
                            '(:not (:equiv ?a ?b))
                            '(:equiv (:not ?a) ?b)))))

(deftest completely-inapplicable-rule
  (is (false? (u/check-match '(:equiv (:and p q) p q)
                             '(:or p q)
                             '(:equiv (:and ?p ?q) ?p)
                             '(:equiv ?q (:or ?p ?q))))))

