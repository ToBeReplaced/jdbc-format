# jdbc-format

Clojure functions for creating parameterized SQL statements.

jdbc-format is intended to be used with [clojure.java.jdbc].  It provides a mechanism of turning declarative templates into map-based parmeterized sql statements, eliminating the complexity of keeping track of parameter ordering.  This is its one and only goal.

## Supported Clojure Versions

jdbc-format is tested on Clojure 1.4.0 and 1.5.1.

## Maturity

The scope of jdbc-format is deliberately small.  However, it is not known to be used in a production environment.

## Installation

jdbc-format is available as a Maven artifact from [Clojars]:
```clojure
[jdbc-format "0.1.0"]
```
jdbc-format follows [Semantic Versioning].

## Documentation

[Codox API Documentation]

## Usage

First, you define a template string like:
```clojure
(def template
"SELECT fruit FROM fruits WHERE color = :color AND citrus = :citrus?")
```

The library exports three functions: `sql`, `formatter` and `formatting`.

`sql` transforms your template into a string that can be used by jdbc:
```clojure
(sql template)
; "SELECT fruit FROM fruits WHERE color = ? AND citrus = ?"
```

`formatter` transforms your template into a function that accepts a map and returns a vector of parameters:
```clojure
((formatter template) {:color "orange" :citrus? true})
; ["orange" true]
((formatter template) {:color "black"})
; ["black" nil]
((formatter template) {:citrus? false :color "yellow" :peel? true})
; ["yellow" false]
```

`formatting` returns a map containing keys :sql and :formatter that correspond to the return values of their functions.  Since formatter and sql go together in the majority of cases, this is provided as a function and is the recommended usage.  Here as an example of using the formatting function to give you a sql-params sequence that can be passed to the `clojure.java.jdbc/query`:
```clojure
(let [{:keys [sql formatter]} (formatting template)]
  (concat [sql] (formatter {:color "orange" :citrus? true})))
; ("SELECT fruit FROM fruits WHERE color = ? AND citrus = ?" "orange" true)
```

## Support

Contact ToBeReplaced on IRC at #clojure with any questions.

## License

Copyright © 2013 ToBeReplaced

Distributed under the Eclipse Public License, the same as Clojure.

[Clojars]: http://clojars.org/jdbc-format
[clojure.java.jdbc]: https://github.com/clojure/java.jdbc
[tests]: https://github.com/ToBeReplaced/jdbc-format/blob/master/test/jdbc_format/core_test.clj
[source]: https://github.com/ToBeReplaced/jdbc-format/blob/master/src/jdbc_format/core.clj
[Semantic Versioning]: http://semver.org
[Codox API Documentation]: http://ToBeReplaced.github.com/jdbc-format
