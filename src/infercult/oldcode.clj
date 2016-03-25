
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
