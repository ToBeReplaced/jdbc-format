(ns jdbc-format-usage
  (:require (jdbc-format (core :refer [sql-params-fn]))))

(def template
  "SELECT fruit FROM fruits WHERE color = :color AND citrus = :citrus?")

(def formatter
  "A function that turns maps into a parameterized SQL vector according
  to my template."
  (sql-params-fn template))

(formatter {:color "orange" :citrus? true})
;; ["SELECT fruit FROM fruits WHERE color = ? AND citrus = ?" "orange" true]

(formatter {:color "black"})
;; ["SELECT fruit FROM fruits WHERE color = ? AND citrus = ?" "black" nil]

(formatter {:color "yellow" :citrus? false :peel? true})
;; ["SELECT fruit FROM fruits WHERE color = ? AND citrus = ?" "yellow" false]
