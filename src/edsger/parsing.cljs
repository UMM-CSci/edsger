(ns edsger.parsing
  "Tools to convert user input into cljs lists"
  (:require [instaparse.core :as insta :refer-macros [defparser]]))

;; A simple CFG for parsing logical expressions containing several logic
;; operators. Currently requires expressions to be fully parenthesized.
(insta/defparser
  infix-cfg
  (str "top-level      = equiv-expr
        <equiv-expr>   = implies-expr | equiv
        equiv          = <w> equiv-expr <w '≡' w> equiv-expr <w>
        <implies-expr> = and-or-expr | implies
        implies        = <w> implies-expr <w '⇒' w> implies-expr <w>
        <and-or-expr>  = not-expr | and | or
        <and-expr>     = not-expr | and
        <or-expr>      = not-expr | or
        and            = <w> and-expr <w '∧' w> and-expr <w>
        or             = <w> or-expr <w '∨' w> or-expr <w>
        <not-expr>     = statement | not
        not            = <w '¬' w> statement <w>
        <statement>    = <w> (variable | boolean) <w> | <w '(' w> equiv-expr <w ')' w>
        boolean        = 'true' | 'false'
        variable       = #'[a-zA-Z]'
        w              = #'\\s*'"))

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
    :and (fn [left right] [:and left right])
    :or (fn [left right] [:or left right])
    :equiv (fn [left right] [:equiv left right])
    :top-level identity}
   parse-tree))

(defn parse
  "Takes a stringfied expression and returns a list of possible
   parse trees for the expression. Returns an empty list when the
   string cannot be parsed. In that case, the parse error is
   attached as metadata."
  [expression]
  (let [hiccup-tree (transform-infix-cfg (insta/parses infix-cfg expression))]
    (if-not (= (type hiccup-tree) instaparse.gll/Failure)
      hiccup-tree)))

(defn rulify
  "Recursively traverse an expression and prepend '?' onto
   all symbols."
  [input]
  (cond
    (seqable? input) (map rulify input)
    (symbol? input) (symbol (str "?" input))
    :else input))
(print "START in parsing")



;; (print (as-and-bs "(and (a) (b))"))
;; (print (count (as-and-bs "(and (a) (b))")))
;; (print (type (as-and-bs "(and (a) )")))

(print "ENND in parsing")
