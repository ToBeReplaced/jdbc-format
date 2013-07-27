# jdbc-format

Clojure functions for creating parameterized SQL statements.

jdbc-format is intended to be used with [clojure.java.jdbc].  It provides a mechanism of turning declarative templates into map-based parmeterized sql statements, eliminating the complexity of keeping track of parameter ordering.  This is its one and only goal.

## Supported Clojure Versions

jdbc-format is tested on Clojure 1.5.1 only.  It may work on other Clojure versions.

## Maturity

This is alpha quality software.

## Installation

jdbc-format is available as a Maven artifact from [Clojars]:
```clojure
[jdbc-format "0.2.0"]
```
jdbc-format follows [Semantic Versioning].  Please note that this means the public API for this library is not considered stable.

## Documentation

The library exports three functions: `sql`, `formatter` and `sql-params-fn`.  If you are the 99% use-case, you should only ever use `sql-params-fn`.

[Codox API Documentation]

## Usage

```clojure
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
```

## Changelog

### v0.2.0

- Remove the `formatting` function in favor of `sql-params-fn`, which covers the most common use case more effectively.

### v0.1.0

- Initial Release

## Support

Please post any comments, concerns, or issues to the Github issues page.

## License

Copyright © 2013 ToBeReplaced

Distributed under the Eclipse Public License, the same as Clojure.  The license can be found at epl-v10.html in the root of this distribution.

[Clojars]: http://clojars.org/jdbc-format
[clojure.java.jdbc]: https://github.com/clojure/java.jdbc
[tests]: https://github.com/ToBeReplaced/jdbc-format/blob/master/test/jdbc_format/core_test.clj
[source]: https://github.com/ToBeReplaced/jdbc-format/blob/master/src/jdbc_format/core.clj
[Semantic Versioning]: http://semver.org
[Codox API Documentation]: http://ToBeReplaced.github.com/jdbc-format
