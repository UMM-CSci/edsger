(ns edsger.substitution-test
  (:require [edsger.substitution :as subst]
            [clojure.test :as t :refer-macros [deftest is]]))

(deftest i-should-fail
  (is (= 1 0)))

(deftest i-should-succeed
  (is (= 1 1)))

