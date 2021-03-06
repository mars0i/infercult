# examples

Each example is followed by notes about new functions introduced in
the example.

## next-freq-1

```clojure
(use '[incanter core charts])  ; for view, xy-plot, and add-lines
(use 'infercult.models)        ; next-freq-1

(def plt (xy-plot (range) 
                  (take 50 (iterate (partial next-freq-1 0.9 0.1)
                           0.1)))) ; initial freq
(view plt)
(add-lines plt (range)
               (take 50 (iterate (partial next-freq-1 0.7 0.9) 0.5)))

```

`(infercult.models/next-freq-1 p r x)` calculates next frequency of a
cultural variant from previous frequency `x`, using direct
transmission probability `p` and internal inference probability `r`. 
From "Coherence, Muller's ratchet, and the maintenance of culture",
equation (9), p. 993, i.e. (7) after *x* and *y* have become equal.


## next-freq-2

### All at once:

```clojure
(use 'incanter.core)        ; for view
(use 'infercult.incantfns)
(def plt (freq-pairs-plot 0.7 0.8 0.9 0.3 0.2 0.8 50))
                         ; p   q   r   s   x   y  n
(view plt)
```

Here `p`, `q` are external transitional probabilities for two cultural
variants (i.e. the probability of learning from an individual who has
the variant), `r`, `s` are internal inference probabilities (i.e. the
probability `r` of infering the first variant from the second, and vice
versa for `s`), `x` and `y` are initial frequencies of the two
variants, and `n` is the number of iterations to plot.


### Piecemeal:

```clojure
(use 'incanter.core)       ; for view
(use 'incanter.charts)     ; for set-y-range
(use 'infercult.incantfns) ; xyp, add
(use 'infercult.models)    ; next-freq-2
 
                                       ;  p   q   r
(def f (swapped-fns (partial next-freq-2 0.7 0.8 0.9)
                    (partial next-freq-2 0.8 0.7 0.3)))
                                       ;  q   p   s
;; Note that the first two parameters are supposed to be the same
;; numbers but in swapped order.

(def xys (iterate f [0.2 0.8])) ; could insert swapped-fns call for f

(def plt (xyp (take 50 (map first  xys))))
(add plt      (take 50 (map second xys)))
(set-y-range plt 0.0 1.0)
(view plt)
```

`(infercult.models/next-freq-2 p q r x y)` calculates next frequency
from previous frequencies `x` and `y`, using  transmission probabilities
`p` and `q`, and inference probability `r`. From appendix of
prepublication draft of "Coherence, Muller's ratchet,  and the
maintenance of culture", equation (13), p.  14.  (Note that we don't
need a second inference probability *s*; that's only used in calculating
the frequency of *y* in (14), which is the same as (13) except for
renaming. That is, this one function can be used for both (13) and
(14).)

<!-- Note that the difference between code vars and italic vars above
and below is intentional.  The italicized vars are purely
mathematical; I'm not referencing code in these cases. -->

We need two instances of `next-freq-2`, one for *x* and one for *y*.
At each step, the next value of *x* will be a function of the previous
value of *x* and *y*, and the next value of *y* will be a function of
*y* and *x*.  To make it simpler to implement this, we use the following
helper function:

`infercult.models/swapped-fns` returns a function that accepts a single
pair `[x y]` as argument, and  returns a pair that containing the result
of applying `f` to `x` `y` and `g` to `y` `x` (note reversed order).
That is, it returns `[(f x y) (g y x)]`.

We can also use `xyp` and `add` from `infercult.incantfns` (as we could
have above).  These are convenience versions of Incanter's `xy-plot` and
`add-lines` that automatically add the necessary *x* coordinate
sequence.
