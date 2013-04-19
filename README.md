# jdbc-format

Clojure functions for creating parameterized SQL statements.

## Installation

`jdbc-format` is available as a Maven artifact from [Clojars]:

`["jdbc-format/jdbc-format" "0.1.0"]

## Usage

`jdbc-format` is intended to be used with [clojure.java.jdbc].  It provides a mechanism of turning declarative templates into map-based parmeterized sql statements, eliminating the complexity of keeping track of parameter ordering.

    (ns example
      (:require (jdbc-format (core :refer [formatting]))))

    (def template
    "SELECT fruit FROM fruits WHERE color = :color AND citrus = :citrus?")

    (let [{:keys [sql formatter]} (formatting template)]
      (prn sql) ; "SELECT fruit FROM fruits WHERE color = ? AND citrus = ?"
      (prn (formatter {:color "orange" :citrus? true})) ; ["orange" true]
      (prn (formatter {:color "black"})) ; ["black" nil]
      (prn (formatter {:citrus? false :color "red" :peel? true})) ; ["red" false]
      ;; Here is a jdbc-compatible sequence you can pass to query:
      (concat [sql] (formatter {:color "yellow" :citrus? true})))

See the [tests] and [source] for more details.

## License

Copyright © 2013 ToBeReplaced

Distributed under the Eclipse Public License, the same as Clojure.

[Clojars]: http://clojars.org/jdbc-format
[clojure.java.jdbc]: https://github.com/clojure/java.jdbc
[tests]: https://github.com/ToBeReplaced/jdbc-format/blob/master/test/jdbc_format/core_test.clj
[source]: https://github.com/ToBeReplaced/jdbc-format/blob/master/src/jdbc_format/core.clj
