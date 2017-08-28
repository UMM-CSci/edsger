(ns edsger.substitution)

(defn new-value [pairs old-value]
  (some (fn [[old new]]
          (if (= old old-value) new))
        pairs))

(defn textual-substitution
  "Performs textual substitution. `pairs` is expected to be a list of old values
  and new values as pairs. e.g. `[[old0 new0] [old1 new1] ...]`. If `list`
  contains elements that are `sequential?`, they will be recursively traversed. So any
  substitution pair where `old*` is a `sequential?` will not have any effect."
  [pairs list]
  (map
   (fn [old-value]
     (if (sequential? old-value)
       (textual-substitution pairs old-value)
       (if-let [new-value (new-value pairs old-value)]
         new-value
         old-value)))
   list))


(defn highlight-elements [list]
  "When passed a finite list, the output will conform to the following
   constraints: The output will be a list of equal length to the input. Each
   element will be a list containing exactly the elements of the input. Each
   element will have a different element in the first position."
  (let [size (count list)]
    (take
     size
     (partition
      size
      1
      (cycle list)))))

(defn permutations-helper [left right]
  (if (empty? right)
    left
    (map
     (fn [elements]
       (permutations-helper
        (conj left (first elements))
        (rest elements)))
     (highlight-elements right))))

(defn permutations
  "Takes a `sequential?` and returns a list containing exactly all the
   possible permutations of the passed in list."
  [input]
  (nth (iterate #(apply concat %)
                (permutations-helper '() input))
       (dec (count input))))

(defn generate-substitutions
  "*IN PROGRESS*. Takes in a valid logic expression and returns a list of
   single-variable subtitutions that can be performed based on that rule."
  [expression]
  (cond

    (= :eqv (first expression))
    (filter #(not (sequential? (first %)))
            (permutations (rest expression)))))

(defn verify-substitution
  "All three arguments should be logic expressions in canonical form.
   (Whatever we end up deciding that should be). This function should
   return true if and only if the rule can be used to transform the
   original expression to the resulting expression, else returns false."
  [original-expression rule resulting-expression]
  (some (fn [substitution]
          (= resulting-expression
             (textual-substitution [substitution] original-expression)))
        (generate-substitutions rule)))
