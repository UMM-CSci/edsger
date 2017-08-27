(ns edsger.core
  (:require [clojure.browser.repl :as repl]
            [clojure.browser.dom  :as dom]
            [clojure.browser.event :as ev]
            [goog.events :as events]))

(enable-console-print!)

;; Constants and State

(def ENTER_KEY 13)
(def STORAGE_NAME "exps-cljs")
(def exp-list (atom [])) ;; ALL APPLICATION STATE LIVES HERE

;; State management

(defn save-exps []
  (.setItem js/localStorage STORAGE_NAME
            (.stringify js/JSON (clj->js @exp-list))))

(defn load-exps []
  (if (not (seq (.getItem js/localStorage STORAGE_NAME)))
    (do
      (reset! exp-list [])
      (save-exps)))
  (reset! exp-list
         (js->clj (.parse js/JSON (.getItem js/localStorage STORAGE_NAME)))))

;; HELPER: shortcut for dom/get-element
(defn by-id [id] (dom/get-element id))

;; HELPER: shortcut for dom/element
(defn elemt [tag params] (dom/element tag params))

;; HELPER: updates a exp by its id, changes puts a new val for the attr
(defn update-attr [id attr val]
  (let [updated
        (vec (map #(if (= (% "id") id) (conj % {attr val}) %) @exp-list))]
    (reset! exp-list updated)))

(defn remove-exp-by-id [id]
  (reset! exp-list
          (vec (filter #(not= (% "id") id) @exp-list))))

;; UI and handlers

(declare refresh-data)

(defn delete-click-handler [ev]
  (let [id (.getAttribute (.-target ev) "data-exp-id")]
    (remove-exp-by-id id)
    (refresh-data)))

(defn exp-content-handler [ev]
  (let [id    (.getAttribute (.-target ev) "data-exp-id")
        div   (by-id (str "li_" id))
        input (by-id (str "input_" id))]
    (dom/set-properties div {"class" "editing"})
    (.focus input)))

(defn input-exp-key-handler [ev]
  (let [input (.-target ev)
        text  (.trim (.-value input))
        id    (apply str (drop 6 (.-id input)))]
    (if (seq text)
      (if (= ENTER_KEY (.-keyCode ev))
        (do
          (update-attr id "title" text)
          (refresh-data)))
      (do
        (remove-exp-by-id id)
        (refresh-data)))))

(defn input-exp-blur-handler [ev]
  (let [input (.-target ev)
        text  (.trim (.-value input))
        id    (apply str (drop 6 (.-id input)))] ;; drops "input_"
    (do
      (update-attr id "title" text)
      (refresh-data))))

(defn redraw-exps-ui []
  (set! (.-innerHTML (by-id "exp-list")) "")
  (dom/set-value (by-id "new-exp") "")
  (dorun ;; materialize lazy list returned by map below
   (map
    (fn [exp]
      (let [
        id          (exp "id")
        li          (elemt "li" {"id" (str "li_" id)})
        checkbox    (elemt "input" {"class" "toggle" "data-exp-id" id
                                   "type" "checkbox"})
        label       (elemt "label" {"data-exp-id" id})
        delete-link (elemt "button" {"class" "destroy" "data-exp-id" id})
        div-display (elemt "div" {"class" "view" "data-exp-id" id})
        input-exp  (elemt "input" {"id" (str "input_" id) "class" "edit"})]

        (dom/set-text label (exp "title"))
        (dom/set-value input-exp (exp "title"))

        (ev/listen label "dblclick" exp-content-handler)
        (ev/listen delete-link "click" delete-click-handler)
        (ev/listen input-exp "keypress" input-exp-key-handler)
        (ev/listen input-exp "blur" input-exp-blur-handler)

        (dom/append div-display checkbox label delete-link)
        (dom/append li div-display input-exp)
        (dom/append (by-id "exp-list") li)))
    @exp-list)))

(defn clear-click-handler []
  (reset! exp-list [])
  (refresh-data))

(defn draw-exp-clear []
  (let [footer (by-id "footer")
        button (by-id "clear")]
    (ev/listen button "click" clear-click-handler)))

;; TODO: Proof validation should be implemented
(defn validate [exps]
  (str exps))

(defn validate-click-handler []
  (js/alert (validate (map #(get % "title") @exp-list))))

(defn validate-event-listener []
  (let [button (by-id "validate")]
    (ev/listen button "click" validate-click-handler)))

(defn refresh-data []
  (save-exps)
  (redraw-exps-ui)
  (draw-exp-clear)
  (validate-event-listener))

;; This get-uuid fn is almost equiv to the original
(defn get-uuid []
  (apply
   str
   (map
    (fn [x]
      (if (= x \0)
        (.toString (bit-or (* 16 (.random js/Math)) 0) 16)
        x))
    "00000000-0000-4000-0000-000000000000")))

(defn add-exp [text]
  (let [tt (.trim text)]
    (if (seq tt)
      (do
        (swap! exp-list conj {"id" (get-uuid) "title" tt})
        (refresh-data)))))

(defn new-exp-handler [ev]
  (if (= ENTER_KEY (.-keyCode ev))
    (add-exp (.-value (by-id "new-exp")))))

(defn add-event-listeners []
  (ev/listen (by-id "new-exp") "keypress" new-exp-handler))

(defn window-load-handler []
  (load-exps)
  (refresh-data)
  (add-event-listeners))

;; Launch window-load-handler when window loads
;; -- not sure why (ev/listen js/window "load" fn) does not work
(events/listen js/window "load" window-load-handler)
