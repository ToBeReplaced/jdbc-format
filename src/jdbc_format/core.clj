(ns jdbc-format.core
  "Functions for creating parameterized SQL statements."
  (:require (clojure (string :as string))))

(def ^{:private true} clojure-keyword
  "Matches a clojure keyword beginning with a single colon."
  #":([a-zA-Z0-9-_\.\*\+\?\/\%\!]+)")

(defn sql
  "Returns a string with all instances of pattern in template replaced
  with ?.  This is presumably used to take your parameterized template
  and turn it into a template suitable for use with jdbc."
  [template & {:keys [pattern] :or {pattern clojure-keyword}}]
  (string/replace template pattern "?"))

(defn formatter
  "Returns a function that accepts a map and returns a vector.  The
  first group in each match for pattern in template returns a word
  that gets converted by converter.  The vector consists of the values
  in the map for each of the converted words.  If a converted key is
  not found in the map, nil will be used as its value.  When there are
  no matches for pattern in template, the returned function will
  accept a map and return an empty vector

  More easily understood, this returns a function that accepts a map
  and returns a vector that can be used by jdbc in conjunction with
  the return string from the sql function."
  [template & {:keys [pattern converter]
               :or {pattern clojure-keyword
                    converter keyword}}]
  (if-let [getters (-> (fn [match]
                         (fn [m]
                           (m (-> match second converter))))
                       (map (re-seq pattern template))
                       seq)]
    (apply juxt getters)
    (fn [m] [])))

(defn formatting
  "Returns a map containing :sql and :formatter for template and
  optional options."
  [template & {:as options}]
  (let [options-seq (apply concat options)]
    {:sql (apply sql template options-seq)
     :formatter (apply formatter template options-seq)}))
