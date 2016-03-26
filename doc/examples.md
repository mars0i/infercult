# examples

## next-freq-1

`infercult.models/next-freq-1` Calculates next frequency of a cultural variant from
previous frequency x, using direct transmission probability p and
internal inference probability r.  From "Coherence, Muller's ratchet,
and the maintenance of culture", equation (9), p. 993, i.e. (7) after
x and y have become equal.

```clojure
(use '[incanter core charts])
(use 'infercult.models) ; next-freq-1

(def p (xy-plot (range) 
                (take 50 (iterate (partial next-freq-1 0.9 0.1)
                         0.1)))) ; initial freq
(view p)
(add-lines p (range)
             (take 50 (iterate (partial next-freq-1 0.7 0.9) 0.5)))

```

## next-freq-2

`infercult.models/next-freq-2` Calculates next frequency from previous frequencies x
and y, using  transmission probabilities p and q, and inference
probability r. From appendix of prepublication draft of "Coherence,
Muller's ratchet,  and the maintenance of culture", equation (13), p.
14.  (Note that we don't need a second inference probability s; that's
only used in calculating the frequency of y in (14), which is the same
as (13) except for renaming. That is, this one function can be used
for both (13) and (14).)

We need two instances of `next-freq-2`, one for *x* and one for *y*.
At each step, the next value of *x* will be a function of the previous
value of *x* and *y*, and the next value of *y* will be a function of
*y* and *x*.  To make it simpler to implement this, we use the following
helper function:

`infercult.models/swapped-fns` returns a function that accepts a single pair [x y] as
argument, and  returns a pair that containing the result of applying f
to x y and g to y x (note reversed order).  That is, it returns [(f x
y) (g y x)].

We can also use `xyp` and `add` from `infercult.incantfns` (as we could
have above).  These are convenience versions of Incanter's `xy-plot` and
`add-lines` that automatically add the necessary *x* coordinate
sequence.

```clojure
(use '[incanter core])     ; for view
(use 'infercult.incantfns) ; xyp, add
(use 'infercult.models)    ; next-freq-2

(def f (swapped-fns (partial next-freq-2 0.7 0.9 0.8)
                    (partial next-freq-2 0.8 0.3 0.5)))

(def xys (iterate f [0.2 0.8])) ; could insert swapped-fns call for f

(def p (xyp (take 60 (map first  xys))))
(add p      (take 60 (map second xys)))
(view p)
```
