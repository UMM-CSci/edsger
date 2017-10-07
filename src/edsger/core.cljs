(ns edsger.core
  "Front-end UI controller"
  (:require [clojure.browser.repl :as repl]
            [clojure.browser.dom  :as dom]
            [clojure.browser.event :as ev]
            [goog.events :as events]
            [goog.dom :as gdom]
            [edsger.unification :as uni]
            [edsger.parsing :as parsing]))

(enable-console-print!)



;; Helpers ===========================

;; shortcut for dom/get-element
(defn- by-id [id] (dom/get-element id))

;; shortcut for dom/element
(defn- elemt [tag params] (dom/element tag params))

(defn- iArrayLike-to-cljs-list
  "Converts goog's iArrayLike type to cljs list"
  [iArr]
  (let [length (aget iArr "length")]
    (for [i (range length)]
      (aget iArr i))))



;; UI and handlers ===================

(defn validate-handler [evt]
  "Perform the validate based on the values typed by users"
  (let [ex-elems (gdom/getElementsByClass "ex") ;; `iArrayLike` type
        rule-elems (gdom/getElementsByClass "rule")
        exps (map #(parsing/parse (.-value %)) (iArrayLike-to-cljs-list ex-elems))
        rules (map #(parsing/rulify (parsing/parse (.-value %))) (iArrayLike-to-cljs-list rule-elems))
        result-str (str (true? (uni/check-match-recursive (nth exps 0)
                                                          (nth exps 1)
                                                          (nth rules 0)
                                                          (nth rules 1))))]
    (.alert js/window result-str)))

(defn validate-click-listener
  [elem]
  (events/listen elem "click" validate-handler))

(validate-click-listener (by-id "validate"))
