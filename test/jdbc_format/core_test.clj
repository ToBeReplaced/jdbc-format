(ns jdbc-format.core_test
  (:require (clojure (test :refer [deftest is testing]))
            (jdbc-format (core :refer [formatter sql formatting]))))

(deftest sql-test
  (testing "\":foo AND :bar\" should return \"? AND ?\""
    (is (= (sql ":foo AND :bar") "? AND ?")))
  (testing "should match :aA0/-_.*+?%!"
    (is (= (sql ":aA0/-_.*+?%!") "?"))))

(deftest formatter-test
  (testing "should handle single values"
    (is (= ["bar"] ((formatter ":foo") {:foo "bar"}))))
  (testing "should handle duplicate values"
    (is (= ["bar" "bar"] ((formatter ":foo AND :foo") {:foo "bar"}))))
  (testing "should return nils when a key is missing"
    (is (= [nil] ((formatter ":foo") {}))))
  (testing "should return empty list when no pattern matches"
    (is (= [] ((formatter "") {:foo "bar"})))))

(deftest formatting-test
  (testing "should accept simple case"
    (let [m (formatting ":foo")]
      (is (= (:sql m) "?"))
      (is (= ((:formatter m) {:foo "bar"}) ["bar"]))))
  (testing "should accept different converters"
    (let [m (formatting ":foo" :converter #(.toUpperCase %))]
      (is (= (:sql m) "?"))
      (is (= ((:formatter m) {"FOO" "bar"}) ["bar"]))))
  (testing "should accept different patterns"
    (let [m (formatting "new {foo} pattern" :pattern #"\{(\w+)\}")]
      (is (= (:sql m) "new ? pattern"))
      (is (= ((:formatter m) {:foo "bar"}) ["bar"])))))
