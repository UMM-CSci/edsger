(ns edsger.core
  "Front-end UI controller"
  (:require [clojure.browser.repl :as repl]
            [clojure.browser.dom  :as dom]
            [goog.events :as events]
            [goog.dom :as gdom]
            [edsger.unification :as uni]
            [edsger.parsing :as parsing]))

(enable-console-print!)



;; Helpers ===========================

;; shortcut for dom/get-element
(defn- by-id [id] (dom/get-element id))

;; shortcut for dom/element
(defn- elemt
  ([tag-or-text]
   (dom/element tag-or-text))
  ([tag params]
   (dom/element tag params)))

(defn- iArrayLike-to-cljs-list
  "Converts goog's iArrayLike type to cljs list"
  [iArr]
  (let [length (aget iArr "length")]
    (for [i (range length)]
      (aget iArr i))))



;; UI and handlers ===================

(defn copy-handler-gen
  "Returns a function/handler that copies the given element's content
   to the clipboard"
  [id]
  (fn []
    (let [temp (elemt "input" {"value" (aget (by-id id) "innerHTML")})]
      (do
        (gdom/appendChild (aget js/document "body") temp)
        (.select temp)
        (.execCommand js/document "copy")
        (gdom/removeNode temp)))))

(defn copy-click-listener
  [elem id]
  (events/listen elem "click" (copy-handler-gen id)))

(defn validate-handler
  "Performs the validation based on the values typed by users"
  [evt]
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

;; TODO: simplify this
(defn window-load-handler
  "Top-level load handler"
  []
  (validate-click-listener (by-id "validate"))
  (copy-click-listener (by-id "not") "not")
  (copy-click-listener (by-id "and") "and")
  (copy-click-listener (by-id "or") "or")
  (copy-click-listener (by-id "impli") "impli")
  (copy-click-listener (by-id "equiv") "equiv"))

(events/listen js/window "load" window-load-handler)
