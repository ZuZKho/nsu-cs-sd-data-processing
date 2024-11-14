(ns ru.nsu.fit.goncharov.task2)

(defn next-prime [a]
    (first
        (filter
            (fn [x] (every? (fn [y] (not= 0 (mod x y))) a))
            (iterate inc (inc (first a))))))

(defn prime-seq-internal
    "Returns a lazy sequence of prime numbers"
    ([] (prime-seq-internal '(2)))
    ([a] (lazy-seq
             (cons (next-prime a) (prime-seq-internal (cons (next-prime a) a))))))

(defn prime-seq [] (cons 2 (prime-seq-internal)))

;; (println (take 10 (prime-seq)))
