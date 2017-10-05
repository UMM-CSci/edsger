(ns edsger.parsing
  "Tools to convert user input into cljs lists"
  (:require [instaparse.core :as insta :refer-macros [defparser]]))

;;"A CFG for a simple logic supporting several logic operators.
;; Currently requires expressions to be fully parenthesized"
(insta/defparser
  infix-cfg
  (str "top-level   = boolean | variable | unary-expr | binary-expr;"
       "boolean     = 'true' | 'false';"
       "variable    = #'[a-zA-Z]';" ;; we only support single-character variables
       "unary-op    = '¬';"
       "unary-expr  = unary-op (<' '> | epsilon) bottom;"
       "bottom      = boolean | variable | <'('> top-level <')'>;"
       "binary-expr = bottom <' '> binary-op <' '> bottom;"
       "binary-op   = '∨' | '∧' | '≡'| '⇒';"))

(def ^:private operator-map
  {"¬" :not
   "∨" :or
   "∧" :and
   "≡" :equiv
   "⇒" :implies})

(defn- transform-infix-cfg
  "When given a hiccup tree produced produced by infix-cfg,
   returns the tree structure in the prefix list style that
   we prefer to work with."
  [parse-tree]
  (insta/transform
   {:boolean #(= "true" %)
    :variable symbol
    :unary-op #(get operator-map %)
    :binary-op #(get operator-map %)
    :unary-expr (fn [op exp] [op exp])
    :binary-expr (fn [left op right] [op left right])
    :bottom identity
    :top-level identity}
   parse-tree))

(defn parse
  "Takes a stringfied expression and converts it into
   a legitimate expression. Returns nil when the expression
   cannot be parsed."
  [expression]
  (let [hiccup-tree (transform-infix-cfg (infix-cfg expression))]
    (if-not (= (type hiccup-tree) instaparse.gll/Failure)
      hiccup-tree)))

(defn rulify
  "Recursively traverse a list and prepend '?' onto
   all symbols."
  [input]
  (map
   (fn [element]
     (cond
       (list? element) (rulify element)
       (symbol? element) (symbol (str "?" element))
       :else element))
   input))

(print "START in parsing")



;; (print (as-and-bs "(and (a) (b))"))
;; (print (count (as-and-bs "(and (a) (b))")))
;; (print (type (as-and-bs "(and (a) )")))

(print "ENND in parsing")
