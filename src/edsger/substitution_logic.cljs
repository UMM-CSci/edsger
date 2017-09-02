(ns edsger.substitution-logic
  (:require [cljs.core.logic :as logic]
            [cljs.core.logic.pldb :as pldb]))

;; (logic/run* [q]
;;   (logic/fresh
;;     [a b c d start rule end]
;;     (logic/== a 'a)
;;     (logic/== b 'b)
;;     (logic/== c 'c)
;;     (logic/== d 'd)
;;     (logic/== start [:eqv a c])
;;     (logic/== rule [:eqv b c])
;;     (logic/== end [:eqv c a])
;;     (logic/== q [start rule end])))

;; (logic/run* [q]
;;   (logic/fresh
;;     [a b c start rule end]
;;     (logic/== a 'a)
;;     (logic/== b 'b)
;;     (logic/== c 'c)
;;     (logic/== start a)
;;     (logic/== rule [:eqv a b])
;;     (logic/== end b)
;;     (logic/== q [start rule end])))

;; (defn get-symbols
;;   "Recursively traverse the passed in strutures and
;;    return a set of all the unique symbols therein."
;;   [& structures]
;;   (set (filter symbol?
;;                (flatten structures))))

;; (defn better-thing [start rule end]
;;   (let [symbols (get-symbols start rule end)]
;;     (logic/run* [q]
;;       (apply logic/fresh
;;              (vec symbols)
;;              ;; [a b c start rule end]
;;              (concat
;;               (map (fn [symb]
;;                      '(logic/== symb 'symb))
;;                    symbols)
;;               (list
;;                `(logic/== start ~start)
;;                `(logic/== rule ~rule)
;;                `(logic/== end ~end)
;;                `(logic/== q [~start ~rule ~end])))))))
;; (better-thing 'a [:eqv 'a 'b] 'b)

;; (defmacro do-the-thing
;;   [start rule end]
;;   (let [symbols (get-symbols start rule end)]
;;     (cons `logic/fresh
;;           (cons (vec symbols)
;;                 (concat
;;                  (map (fn [symbol]
;;                         '(`logic/== symbol symbol))
;;                       symbols)
;;                  "equality statements"
;;                  )))
;;     ))

;; (defmacro dump-mac [a]
;;   `(concat "str" "str"))

;; (eval (dump-mac "a"))

;; (require '[cljs.compiler :as comp])

;; (require '[cljs.core :as core])

;; (require '[cljs.analyser :as ana] )

;; (macroexpand-1 '(dump-mac "a"))

;; (macroexpand-1 '(when bool expr1 expr2))

;; '(1 2)

;; steps:
;; 1. take in something that we've parsed from the user like ['a] [:eqv 'a 'b] ['b]
;; 2. DONE get a list of all the symbols: '(a b)
;; 3. use all the symbols to build up a fresh-expression.


