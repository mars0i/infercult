;; models.clj
;; Marshall Abrams
;; 
;; Clojure implementations of cultural transmission model involving
;; coherence and inference, from section 5 of 
;; 
;; 	"Coherence, Muller’s Ratchet, and the Maintenance of Culture"
;; 	Marshall Abrams
;; 	Philosophy of Science 2015 82:5, 983-996 
;; 
;; available at http://www.journals.uchicago.edu/doi/abs/10.1086/683434,
;; and generalizations of that model, including the extended model in the
;; appendix of an earlier, longer draft of the same paper, available at
;; http://philsci-archive.pitt.edu/11139/.
;; 
;; This software is copyright 2016 by Marshall Abrams, and is distributed
;; under the Gnu General Public License version 3.0 as specified in the
;; file LICENSE, except where noted.

(ns infercult.models)

;; Example Incanter plotting:
;; (use '[incanter core stats charts])
;; (def p (xy-plot (range 50) 
;;                 (take 50 (iterate (partial next-freq-1 0.9 0.1 0.5)
;;                          0.1)))) ; initial freq
;; (view p)
;; (add-lines p (range 50)
;;              (take 50 (iterate (partial next-freq-1 0.7 0.9) 0.5)))

(defn next-freq-1 [p r x]
  "Calculates next frequency of a cultural variant from previous frequency x,
  using direct transmission probability p and internal inference probability r.
  From \"Coherence, Muller's ratchet, and the maintenance of culture\",
  equation (9), p. 993, i.e. (7) after x and y have become equal."
  (+ (* p x) 
     (* r p x) 
     (* -1 r p p x x)))

(defn next-freq-2
  "Calculates next frequency from previous frequencies x and y, using 
  transmission probabilities p and q, and inference probability r.
  From appendix of prepublication draft of \"Coherence, Muller's ratchet, 
  and the maintenance of culture\", equation (13), p. 14.  (Note that we
  don't need a second inference probability s; that's only used in calculating
  the frequency of y in (14), which is the same as (13) except for renaming.
  That is, this one function can be used for both (13) and (14).)"
  [p q r x y]
  (let [px  (* p x)
        rqy (* r q y)]
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


;(defn verbose-next-freq-from-freqs
;  "Same as next-freq-from-freqs but with generalized, verbose param names.
;  See docstring for that function."
;  [transm-prob other-transm-prob infer-prob freq other-freq]
;  (let [px  (* transm-prob freq)
;        rqy (* infer-prob other-transm-prob other-freq)]
;    (- (+ px rqy) 
;       (* px rqy))))
