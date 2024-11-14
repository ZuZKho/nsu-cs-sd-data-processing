(ns ru.nsu.fit.goncharov.task2-test
    (:use ru.nsu.fit.goncharov.task2)
    (:require [clojure.test :as test]))


(test/deftest task2-test1
    (test/testing "Testing task2"
        (test/is (= (take 0 (prime-seq)) '()))
        (test/is (= (take 1 (prime-seq)) '(2)))
        (test/is (= (take 2 (prime-seq)) '(2 3)))
        (test/is (= (take 3 (prime-seq)) '(2 3 5)))
        (test/is (= (take 5 (prime-seq)) '(2 3 5 7 11)))
        (test/is (= (take 10 (prime-seq)) '(2 3 5 7 11 13 17 19 23 29)))))

