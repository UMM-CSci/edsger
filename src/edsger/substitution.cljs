(ns edsger.substitution)

(def equiv :equiv)

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
  (nth (permutations-helper '() input)
       (dec (count input))))
