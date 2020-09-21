(ns node.core.fn)

(defn replaceIf [json]
  (defn clean-map [m]
    (if (map? m)
      (let [clean-val (fn [[k v]]
                        (let [v' (clean-map v)]
                          (when-not (nil? v')
                            [k v'])))
            m' (->> (map clean-val m)
                    (remove nil?)
                    (into {}))]
        (when-not (empty? m') m'))
      m))

  (defn clean-deep-merge! [& xs]
    (clean-map
     (let [xs (remove nil? xs)]
       (if (every? map? xs)
         (apply merge-with clean-deep-merge! xs)
         (last xs)))))

  (let [source (js->clj json :keywordize-keys true)]
    (let [src-map (get source :src)]
      (let [target-map (get source :target)]
        (clj->js (clean-deep-merge! src-map target-map))))))

(def nested-map
  {:a nil
   :b {:c nil
       :d 2
       :e {:f nil
           :g 4}}})

(def nested-map2
  {:a 1
   :b {:c 3
       :d nil
       :e {:f nil
           :g nil}}
   :c 10000})

nested-map
nested-map2
(replaceIf nested-map nested-map2)
;; (defn clean-map [m]
;;   (if (map? m)
;;     (let [clean-val (fn [[k v]]
;;                       (let [v' (clean v)]
;;                         (when-not (nil? v')
;;                           [k v'])))
;;           m' (->> (map clean-val m)
;;                   (remove nil?)
;;                   (into {}))]
;;       (when-not (empty? m') m'))
;;     m))

;; (def nested-map
;;   {:a nil
;;    :b {:c nil
;;        :d 2
;;        :e {:f nil
;;            :g 4}}})

;; (clean-map nested-map)

;; (defn replaceIf [case, json]
;;   (defn clean-map [m]
;;     (if (map? m)
;;       (let [clean-val (fn [[k v]]
;;                         (let [v' (clean v)]
;;                           (when-not (nil? v')
;;                             [k v'])))
;;             m' (->> (map clean-val m)
;;                     (remove nil?)
;;                     (into {}))]
;;         (when-not (empty? m') m'))
;;       m))

;;   (defn clean-deep-merge! [& xs]
;;     (clean-map
;;      (let [xs (remove nil? xs)]
;;        (if (every? map? xs)
;;          (apply merge-with clean-deep-merge! xs)
;;          (last xs)))))

;;   (defn deep-merge! [& xs]
;;     (let [xs (remove nil? xs)]
;;       (if (every? map? xs)
;;         (apply merge-with deep-merge! xs)
;;         (last xs))))

;;   (let [source (js->clj json :keywordize-keys true)]
;;     (let [src-map (get source :src)]
;;       (let [target-map (get source :target)]
;;         (clj->js (clean-deep-merge! src-map target-map))))))

;; (def src {:key1 "1", :key2 "2", :key3 "3", :key4 "4"})
;; (def target {:key1 "10", :key3 nil, :key4 "40"})

;; (seq src)
;; (seq target)

;; (clean-map nested-map)

;; (merge {:a 1 :b 2 :c 3} (clean-map {:a nil :b 9 :d 4}))

;; (defn merge-if-not-nil [m1 m2]
;;   (merge m1 (clean-map m2)))

;; (merge-if-not-nil nested-map nested-map2)


;; (clean-map nested-map)
;; (clean-map nested-map2)

;; (remove-nils nested-map)
;; (remove-nils nested-map2)

;; ;; res => 
;; ;; {:a 1 
;; ;;  :b {:c 3 :d 2 :e {:g 4}} 
;; ;;  :c 10000}
;; ;;  
;; (clj->js (clean-deep-merge! nested-map nested-map2))
;; (deep-merge! nested-map nested-map2)
;; ;; (deep-merge* nested-map nested-map2)
;; ;; (deep-merge? nested-map nested-map2)

;; (defn clean-deep-merge! [& xs]
;;   (clean-map
;;    (let [xs (remove nil? xs)]
;;      (if (every? map? xs)
;;        (apply merge-with clean-deep-merge! xs)
;;        (last xs)))))

;; (defn deep-merge! [& xs]
;;   (let [xs (remove nil? xs)]
;;     (if (every? map? xs)
;;       (apply merge-with deep-merge! xs)
;;       (last xs))))

;; ;; (defn deep-merge* [& maps]
;; ;;   (let [f (fn [old new]
;; ;;             (if (and (map? old) (map? new))
;; ;;               (merge-with deep-merge* old new)
;; ;;               new))]
;; ;;     (if (every? map? maps)
;; ;;       (apply merge-with f maps)
;; ;;       (last maps))))

;; ;; (defn deep-merge? [& maps]
;; ;;   (let [maps (filter identity maps)]
;; ;;     (assert (every? map? maps))
;; ;;     (apply merge-with deep-merge* maps)))

;; ;; res => 
;; ;; {:a 1 
;; ;;  :b {:c 3 :d 2 :e {:g 4}} 
;; ;;  :c 10000}

;; (def nested-map 
;;   {:a nil
;; 		:b {:c nil
;; 				:d 2
;; 				:e {:f nil
;; 						:g 4}}})

;; (def nested-map2
;;   {:a 1
;;    :b {:c 3
;;        :d nil
;;        :e {:f nil
;;            :g nil}}
;;    :c 10000})

;; (defn clean-map [m]
;;   (if (map? m)
;;     (let [clean-val (fn [[k v]]
;;                       (let [v' (clean-map v)]
;;                         (when-not (nil? v')
;;                           [k v'])))
;;           m' (->> (map clean-val m)
;;                   (remove nil?)
;;                   (into {}))]
;;       (when-not (empty? m') m'))
;;     m))


;; (defn remove-nils
;;   "remove pairs of key-value that has nil value from a (possibly nested) map. also transform map to nil if all of its value are nil"
;;   [nm]
;;   (clojure.walk/postwalk
;;    (fn [el]
;;      (if (map? el)
;;        (not-empty (into {} (remove (comp nil? second)) el))
;;        el))
;;    nm))

;; (clean-map nested-map)
;; (clean-map nested-map2)

;; (remove-nils nested-map)
;; (remove-nils nested-map2)
;; 
;; (prn src)
;; (prn target)

;; src => {:key1 "1", :key2 "2", :key3 "3", :key4 "4"}
;; target => {:key1 "10", :key3 nil, :key4 "40"}

;; (+ 1 2)
;; (type {key1: '1', key2: '2', key3: '3', key4: '4' })
;; (type [1 2])

;; (defn replaceIf [type, src]
;;   (.log js/console type)
;;   (.log js/console (js->clj src)))

;; { key1: '1', key2: '2', key3: '3', key4: '4' }
  ;; (aget src "target"))
  ;; (clj->js (js->clj src)))

;; (type {"src" {"key1" "1", "key2" "2", "key3" "3", "key4" "4"}, "target" {"key1" "10", "key3" nil, "key4" "40"}})

;; (let [src-map (js->clj {"src" {"key1" "1", "key2" "2", "key3" "3", "key4" "4"}, "target" {"key1" "10", "key3" nil, "key4" "40"}})]
;;   (prn "src => " (type src-map)))

;; (replaceIf "type" "src")

;; (.log js/console "hell JS!")
;; (+ 1 2)
;; (take 3 (range 0 100))