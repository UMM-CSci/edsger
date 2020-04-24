(ns edgser.validation
    (:require [edsger.modelchecking :as mc]
    [edsger.unification :as uni])
)

(defn validation (exps rules step-types)
    ;;rule invalid start

    (if(mc/modelcheck rules)true
        ;;rule is valid
    )
    (else
        ;;rule is invalid fail and don't check matching
        (try
            (throw :type :custom-error
            :message "Invalid rule.")
        )
    )

    (if((uni/recursive-validate exps rules step-types)) true
        ;;rule matches expression
    )

    (else
        ;; valid rule, but it doesnt match.
        (try
            (throw :type :custom-error
            :message "Rule is valid, but does not work with this expression.")
        )
    )



)