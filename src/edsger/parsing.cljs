(ns edsger.parsing
  "Tools to convert user input into cljs lists"
  (:require [instaparse.core :as insta :refer-macros [defparser]]))

;; A simple CFG for parsing logical expressions containing several logic
;; operators. The parser is aware of precedence, but some operators have
;; equal precedence, e.g. "p ≡ q ≡ r" needs to be parenthesized as
;; "(p ≡ q) ≡ r" or as "p ≡ (q ≡ r)".

(insta/defparser
  infix-cfg
  (str "top-level      = equiv-expr
        <equiv-expr>   = implies-expr | equiv
        equiv          = <w> implies-expr <w '≡' w> implies-expr <w>
        <implies-expr> = and-or-expr | implies
        implies        = <w> and-or-expr <w '⇒' w> and-or-expr <w>
        <and-or-expr>  = not-expr | and | or
        and            = <w> not-expr <w '∧' w> not-expr <w>
        or             = <w> not-expr <w '∨' w> not-expr <w>
        <not-expr>     = statement | not
        not            = <w '¬' w> not-expr <w>
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
  "Takes a stringfied expression and converts it into
   a legitimate expression. Returns nil when the expression
   cannot be parsed."
  [expression]
  (let [hiccup-tree (transform-infix-cfg (infix-cfg expression))]
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
