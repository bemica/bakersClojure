;;TODO: Have some method to randomly generate new customers
;;TODO: have servers come back somehow
(ns async-bakers.core
  (:gen-class))

(def upper-bound 42)
(def cust-chan-size 10)
(def serve-chan-size 10)

;;Takes a number from a customer and a server-id, prints the fib
(defn serve-one [cust-num server]
  (print (str "fib of " cust-num " is " (fib cust-num) ", computed by server " server)))

(defn fib [n]
  (cond (= n 0) 0
    (= n 1) 1
    :else (+ (fib (- n 1)) (fib (- n 2)))))

;; creates server and customer channels, populates them, and starts serving
(defn start-serving [num-init-custs num-servs]
  (let [cust-chan (chan cust-chan-size) serve-chan (chan serve-chan-size)]
    ;;populate the server channel with however many servers
    (go (doseq [x (range num-servs)] (>! serve-chan x)))
    ;;populate the customer channel with the initial number of customers
    (go (doseq [x (range num-init-custs)] (>! cust-chan x)))
    ;;loop forever to serve,
    ;;TODO: use alts! to hang until there is a server/customer to serve
    (go-loop)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (start-serving (first args)) (first (rest args)))
