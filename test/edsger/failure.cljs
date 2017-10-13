(ns edsger.failure
  (:require  [clojure.test :as t :refer-macros [deftest is] :include-macros]))

(deftest this-is-bad
  (is (true? false)))

