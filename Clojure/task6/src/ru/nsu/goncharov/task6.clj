(ns ru.nsu.goncharov.task6)

;;;an empty route map
;;;it is enough to use either forward or backward part (they correspond to each other including shared reference to number of tickets)
;;;:forward is a map with route start point names as keys and nested map as values
;;;each nested map has route end point names as keys and route descriptor as values
;;;each route descriptor is a map (structure in fact) of the fixed structure where
;;;:price contains ticket price
;;;and :tickets contains reference to tickets number
;;;:backward has the same structure but start and end points are reverted
(def empty-map
    {:forward {},
     :backward {}})

(defn route
    "Add a new route (route) to the given route map
     route-map - route map to modify
     from - name (string) of the start point of the route
     to - name (string) of the end point of the route
     price - ticket price
     tickets-num - number of tickets available"
    [route-map from to price tickets-num]
    (let [tickets (ref tickets-num :validator (fn [state] (>= state 0))),     ;reference for the number of tickets
          orig-source-desc (or (get-in route-map [:forward from]) {}),
          orig-reverse-dest-desc (or (get-in route-map [:backward to]) {}),
          route-desc {:price price,                                            ;route descriptor
                      :tickets tickets},
          source-desc (assoc orig-source-desc to route-desc),
          reverse-dest-desc (assoc orig-reverse-dest-desc from route-desc)]
        (-> route-map
            (assoc-in [:forward from] source-desc)
            (assoc-in [:backward to] reverse-dest-desc))))


(def transaction-atom (atom 0))
(defn dijkstra [route-map from to]
    (let [dist (atom {})
          par (atom {})
          queue (atom (sorted-set-by (fn [a b] (compare (second a) (second b))) [from 0]))]

        ;; Initialize distances and predecessors
        (doseq [curv (keys (route-map :forward))]
            (swap! dist assoc curv (if (= curv from) 0 Integer/MAX_VALUE))
            (swap! par assoc curv nil))

        ;; Process the queue
        (while (not (empty? @queue))
            (let [[curv curdist] (first @queue)]
                (swap! queue disj [curv curdist])

                ;; Update distances and predecessors for neighbors
                (doseq [neighbor (keys (get-in route-map [:forward curv]))]
                    (when (> @(get-in route-map [:forward curv neighbor :tickets]) 0)
                        (let [new-distance (+ curdist (get-in route-map [:forward curv neighbor :price]))]
                            (when (< new-distance (get @dist neighbor))
                                (swap! dist assoc neighbor new-distance)
                                (swap! par assoc neighbor curv)
                                (swap! queue conj [neighbor new-distance]))))))
            )

        ;; Reconstruct the path
        (if (< (get @dist to) Integer/MAX_VALUE)
            (let [path (reverse (loop [path [] current to]
                                    (if (nil? current)
                                        path
                                        (recur (conj path current) (@par current)))))]

                    (doseq [edge (butlast path)]
                        (let [next-edge (second (drop-while #(not= % edge) path))]
                            (alter (get-in route-map [:forward edge next-edge :tickets]) dec)))
                    {:path path, :price (get @dist to)})
            nil))
    )

(defn book-tickets
    "Tries to book tickets and decrement appropriate references in route-map atomically
     returns map with either :price (for the whole route) and :path (a list of destination names) keys
            or with :error key that indicates that booking is impossible due to lack of tickets"
    [route-map from to]
    (if (= from to)
        {:path '(), :price 0}
        ;; start transaction
        (dosync
            (swap! transaction-atom inc) ;; For counting transactions number.
            (let [path (dijkstra route-map from to)] ;; path - hashmap {:path :price} or nil
                (if path path {:error -1}))
            )
        )
    )



;;;cities
(def spec1 (-> empty-map
               (route "City1" "Capital"    200 5)
               (route "Capital" "City1"    250 5)
               (route "City2" "Capital"    200 5)
               (route "Capital" "City2"    250 5)
               (route "City3" "Capital"    300 3)
               (route "Capital" "City3"    400 3)
               (route "City1" "Town1_X"    50 2)
               (route "Town1_X" "City1"    150 2)
               (route "Town1_X" "TownX_2"  50 2)
               (route "TownX_2" "Town1_X"  150 2)
               (route "Town1_X" "TownX_2"  50 2)
               (route "TownX_2" "City2"    50 3)
               (route "City2" "TownX_2"    150 3)
               (route "City2" "Town2_3"    50 2)
               (route "Town2_3" "City2"    150 2)
               (route "Town2_3" "City3"    50 3)
               (route "City3" "Town2_3"    150 2)))

(defn booking-future [route-map from to init-delay loop-delay]
    (future
        (Thread/sleep init-delay)
        (loop [bookings []]
            (Thread/sleep loop-delay)
            (let [booking (book-tickets route-map from to)]
                (if (booking :error)
                    bookings
                    (recur (conj bookings booking)))))))

(defn print-bookings [name ft]
    (println (str name ":") (count ft) "bookings")
    (doseq [booking ft]
        (println "price:" (booking :price) "path:" (booking :path) )))

(defn run []
    ;;try to tune timeouts in order to all the customers gain at least one booking
    (let [f1 (booking-future spec1 "City1" "City3" 700 1),
          f2 (booking-future spec1 "City1" "City2" 300 10),
          f3 (booking-future spec1 "City2" "City3" 500 1)]
        (print-bookings "City1->City3" @f1)
        (print-bookings "City1->City2" @f2)
        (print-bookings "City2->City3" @f3)
        (println "Total (re-)starts:" @transaction-atom)
        ))



(run)

(defn print-ref-values [m]
    (doseq [[k v] m]
        (cond
            (map? v) (print-ref-values v)
            (instance? clojure.lang.Ref v) (print @v " "))))

(print-ref-values spec1)

