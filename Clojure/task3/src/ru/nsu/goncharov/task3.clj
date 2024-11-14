(ns ru.nsu.goncharov.task3)

(defn heavy+ [& args]
    (Thread/sleep 10)
    (apply + args))


(defn p-reduce-3
    ([value-f init-val coll]
     (p-reduce-3 value-f value-f init-val coll))
    ([value-f merge-f init-val coll]
     (let [chunk-size (int (Math/ceil (Math/sqrt (count coll)))),

           parts (partition-all chunk-size coll)]
         (->> parts
              (map (fn [coll1]

                       (future (reduce value-f init-val coll1))))

              ;;do not forget to cancel map’s laziness!
              (doall)
              (map deref)
              (reduce merge-f init-val)))))

;; (time (p-reduce-3 heavy+ 0 (range 1 16)))



(defn p-filter
    "Parallel lazy filter"
    [predicate lst]
    (let [small-chunk-size 100,
          big-chunk-size 400,
          parts (partition-all big-chunk-size lst)]
        (->> parts
             (map (fn [part]
                      (let [small-part (partition-all small-chunk-size part)]
                          (->> small-part
                               (doall)
                               (map (fn [coll1]
                                        (future (filter predicate coll1))))
                               (doall)
                               (map deref)
                          ))))
             (flatten)
        )))



(defn num-divisors [x]
    (Thread/sleep 5)
    (even? x))

(time (doall (take 100 (p-filter num-divisors (iterate inc 1)))))

;;    (count (filter #(zero? (mod x %)) (range 1 (inc x)))))

;;(time (count (take 10 (filter (fn [x] (> (num-divisors x) 50)) (iterate inc 1)))))
(time (doall (take 100 (p-filter num-divisors (iterate inc 1)))))
;;(time (doall (take 100 (filter num-divisors (iterate inc 1)))))
;;(time (println (take 100 (filter (fn [x] (> (num-divisors x) 30)) (iterate inc 1)))))
"Elapsed time: 28012.7057 msecs"