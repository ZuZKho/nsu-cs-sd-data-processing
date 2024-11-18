(ns ru.nsu.goncharov.task3)

(defn p-filter
    "Parallel lazy filter"
    [predicate lst]
    (let [small-chunk-size 40,
          big-chunk-size 200,
          parts (partition-all big-chunk-size lst)]
        (->> parts
             (map (fn [part]
                      (let [small-part (partition-all small-chunk-size part)]
                          (->> small-part
                               (map (fn [coll1]
                                        (future (doall (filter predicate coll1)))))
                               (doall)
                               (map deref)
                          ))))
             (flatten)
        )))

(defn num-divisors [x]
    (count (filter (comp zero? (partial rem x)) (range 1 (inc x)))))

;; "Elapsed time: 162.2002 msecs"
(time (doall (take 1000 (filter #(> (num-divisors %) 10) (iterate inc 1)))))


;; "Elapsed time: 46.576 msecs"
(time (doall (take 1000 (p-filter #(> (num-divisors %) 10) (iterate inc 1)))))
