
;; Generalized version of next-freq-from-freq in which transmission
;; probabilities differ for the two cultural variants.
(defn next-freq-from-freqs
  "Calculates next frequency from previous frequencies x and y, using 
  transmission probabilities p and q, and inference probability r.  From 
  \"Coherence, Muller's ratchet, and the maintenance of culture\", 
  equation (7) after x and y have become equal."
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


;; FIXME DOESN'T WORK
;; Intended to allow iterating next-freq-2
;; e.g. like this:
;; (double-cross-iterate (partial next-freq-2 0.5 0.2 0.8) (partial next-freq-2 0.5 0.8 0.2)  [0.5 0.4]))
(defn double-cross-iterate
  "Returns a lazy sequence of
     [x y], [(f x y) (g y x)], 
     [(f (f x y) (g y x))
      (g (g y x) (f x y))], etc.
  i.e. at each step, takes the result [x y] from the previous step and 
  generates [x' y'] by applying f to x and y, and g to the same values 
  in reverse order."
  [f g [x y]]
  (cons [x y]
        (lazy-seq double-cross-iterate f g [(f x y) (g y x)])))
