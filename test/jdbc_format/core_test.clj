(ns jdbc-format.core_test
  (:require (clojure (test :refer [deftest is]))
            (jdbc-format (core :refer [formatter sql sql-params-fn]))))

(deftest sql-test
  (is (= (sql ":foo AND :bar") "? AND ?")
      "\":foo AND :bar\" should return \"? AND ?\"")
  (is (= (sql ":aA0/-_.*+?%!") "?")
      "should match :aA0/-_.*+?%!"))

(deftest formatter-test
  (is (= ["bar"] ((formatter ":foo") {:foo "bar"}))
      "should handle single values")
  (is (= ["bar" "bar"] ((formatter ":foo AND :foo") {:foo "bar"}))
      "should handle duplicate values")
  (is (= [nil] ((formatter ":foo") {}))
      "should return nils when a key is missing")
  (is (= [] ((formatter "") {:foo "bar"}))
      "should return empty list when no pattern matches"))

(deftest sql-params-test
  (is (= ((sql-params-fn ":foo") {:foo "bar"})
         ["?" "bar"])
      "should accept simple case")
  (is (= ((sql-params-fn ":foo" :converter #(.toUpperCase %)) {"FOO" "bar"})
         ["?" "bar"])
      "should accept different converters")
  (is (= ((sql-params-fn "new {foo}" :pattern #"\{(\w+)\}") {:foo "bar"})
         ["new ?" "bar"])
      "should accept different patterns"))
