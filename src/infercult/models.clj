;; Marshall Abrams
;; 
;; Clojure implementations of cultural transmission model involving
;; coherence and inference, from section 5 of 
;; 	"Coherence, Muller’s Ratchet, and the Maintenance of Culture"
;; 	Marshall Abrams, Philosophy of Science 2015 82:5, 983-996, 
;; and generalizations of that model, including the extended model in the
;; appendix of an earlier, longer draft of the same paper.

;; This software is copyright 2016 by Marshall Abrams, and is distributed
;; under the Gnu General Public License version 3.0 as specified in the
;; file LICENSE, except where noted.

(ns infercult.models)

(defn next-freq-1
  "Calculates next frequency of a cultural variant from previous frequency x,
  using direct transmission probability p and internal inference probability r.
  From \"Coherence, Muller's ratchet, and the maintenance of culture\",
  equation (9), p. 993, i.e. (7) after x and y have become equal."
 [p r x]
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

(defn swapped-fns
  "Returns a function that accepts a single pair [x y] as argument, and 
  returns a pair that containing the result of applying f to x y and
  g to y x (note reversed order).  That is, it returns [(f x y) (g y x)]."
  [f g]
  (fn [[x y]]
    [(f x y) (g y x)]))

(defn freq-pairs
  "Given two transmission probabilities p and q, and inference probabilities
  r and s, and initial frequencies x and y of two cultural variants, returns 
  a lazy sequence of pairs that are the successive x freqs and y freqs 
  calculated using next-freq-2, i.e. according to equations (13) and (14) 
  in the appendix of prepublication draft of \"Coherence, Muller's ratchet, 
  and the maintenance of culture\", equation (13), p. 14."
  [p q r s x y]
  (let [f (swapped-fns (partial next-freq-2 p q r)
                       (partial next-freq-2 q p s))]
    (iterate f [x y])))

