(ns infercult.models
  [:use [incanter core stats charts]])

(defn f [p r x]
  (+ (* p x) 
     (* r p x) 
     (* -1 r p p x x)))

(defn next-x
  [p q r x y]
  (let [px  (* p x)
        rqy (* r q y)]
    (- (+ px rqy) 
       (* px rqy))))

;; same as next-x but with generalized, verbose param names
(defn next-freq
  [transm-prob other-transm-prob infer-prob freq other-freq]
  (let [px  (* transm-prob freq)
        rqy (* infer-prob other-transm-prob other-freq)]
    (- (+ px rqy) 
       (* px rqy))))

;(view (xy-plot (range 1000) (take 1000 (iterate (partial f 0.9 0.10) 0.1))))
