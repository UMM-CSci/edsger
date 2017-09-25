(ns edsger.parsing
  (:require [instaparse.core :as insta]))

(def lisp-style-cfg
  (insta/parser
   "S = '('A')' | '('B' 'S')' | '('C' 'S' 'S')'
    A = E | D
    E = 'a' | 'b' | 'c'
    D = 'true' | 'false'
    B = 'not'
    C = 'and' | 'or' | 'equiv'"
   ))

(defn parse
  "Takes an stringfied expression and converts it into
   a legitimate expression"
  [hiccup-tree]
  (case (first hiccup-tree)
    :S (map parse (filter vector? hiccup-tree))
    :A (parse (second hiccup-tree))
    :E (symbol (second hiccup-tree))
    :D (#{"true"} (second hiccup-tree))
    :B (keyword (second hiccup-tree))
    :C (keyword (second hiccup-tree))
    ))

(print "START in parsing")



;; (print (as-and-bs "(and (a) (b))"))
;; (print (count (as-and-bs "(and (a) (b))")))
;; (print (type (as-and-bs "(and (a) )")))

(print "ENND in parsing")
