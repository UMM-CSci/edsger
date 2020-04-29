(ns edgser.validation
    (:require [edsger.modelchecking :as mc]
    [edsger.unification :as uni])
)

(defn validation [exps rules step-types]
    (if (mc/modelcheck rules)
        ;;rule is valid
        (if (uni/recursive-validate exps rules step-types)
            ;;rule matches expression
            true
            ;; valid rule, but it doesnt match.
            :message "Rule is valid, but does not work with this expression."))
    ;;rule is invalid fail and don't check matching
    :message "Invalid rule.")