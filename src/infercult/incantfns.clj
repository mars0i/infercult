;; Marshall Abrams
;;
;; This software is copyright 2016 by Marshall Abrams, and is distributed
;; under the Gnu General Public License version 3.0 as specified in the
;; file LICENSE, except where noted.

(ns infercult.incantfns
  (:require [incanter.charts :as ch]
            [infercult.models :as im]))

(defn xyp
  "Calls incanter.charts/xy-plot with y coordinates ys, adding x coordinates
  from zero to n as needed."
  [ys]
  (ch/xy-plot (range) ys))

(defn add
  "Calls incanter.charts/add-lines with y coordinates ys, adding x coordinates
  from zero to n as needed."
  [p xs]
  (ch/add-lines p (range) xs))

(defn freq-pairs-plot
  [p q r s x y n]
  (let [pairs (take n (im/freq-pairs p q r s x y))
        plt (xyp (map first pairs))]
    (add plt (map second pairs))
    (ch/set-y-range plt 0.0 1.0)))

