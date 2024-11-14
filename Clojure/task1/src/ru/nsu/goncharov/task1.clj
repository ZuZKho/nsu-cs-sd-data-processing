(ns ru.nsu.goncharov.task1)

;; returns all cur_strings + charlists concatenation option
(defn add [cur_strings charlist]
    (reduce
        (fn [acc x] (concat acc (reduce (fn [acc2 y] (conj acc2 (str x y))) '() charlist)))
        '()
        cur_strings)
    )

(defn dirty_list
    [charlist n]
    (if (> n 0)
        (reduce (fn [acc x] (add acc charlist)) charlist (range 1 n))
        '())
    )

(defn smart_filter [lst]
    (filter
        (fn [x]
            (reduce
                (fn [acc y] (if (= acc false)
                                false
                                (not= (nth y 0) (nth y 1))))
                true
                (partition 2 1 x))
            )
        lst))

(let [x (smart_filter (dirty_list '("a" "b" "c") 4))] (println x))
