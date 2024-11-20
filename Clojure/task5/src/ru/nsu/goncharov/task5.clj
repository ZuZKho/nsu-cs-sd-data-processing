(defn create-threads [nThreads func]
    (map (fn [x] (new Thread (fn [] (func x)))) (range nThreads)))

(def transaction-atom (atom 0))

(defn table-emulator [nPhilosophers thinkingDelay diningDelay nDinings]
    (let [forks (map (fn [_] (ref 0)) (range nPhilosophers)),
          behaviour-fn (fn [id]
                           (dotimes [_ nDinings]
                               (Thread/sleep thinkingDelay) ;; Thinking
                               (dosync
                                   (swap! transaction-atom inc) ;; For counting transactions number.
                                   (let [f1 (nth forks id)
                                         f2 (nth forks (mod (inc id) nPhilosophers))]
                                       (alter f1 inc)
                                       (alter f2 inc)
                                       (Thread/sleep diningDelay) ;; Dining
                                       )))),
          philosophers (create-threads nPhilosophers behaviour-fn)]

        (doall (map #(.start (nth philosophers %)) (range nPhilosophers)))
        (doall (map #(.join (nth philosophers %)) (range nPhilosophers)))
        (println (map #(deref %) forks))))

;; Example usage:
(time (table-emulator 5 0 100 3))
(println @transaction-atom)