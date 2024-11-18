(ns ru.nsu.goncharov.task3-test
    (:use ru.nsu.goncharov.task3)
    (:require [clojure.test :as test]))

(test/deftest task3-test1
    (test/testing "Testing task3"
        (test/is (= (take 0 (p-filter even? (iterate inc 1))) `()))
        (test/is (= (take 4 (p-filter even? (iterate inc 1))) `(2 4 6 8)))
        (test/is (= (p-filter even? '(1 3 5 7)) `()))
        (test/is (= (p-filter even? '(1 3 2 5 7 4)) `(2 4)))
        ))
