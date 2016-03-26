;; Marshall Abrams
;;
;; This software is copyright 2016 by Marshall Abrams, and is distributed
;; under the Gnu General Public License version 3.0 as specified in the
;; file LICENSE, except where noted.

(ns infercult.incantfns
  (:require [incanter.charts :as ch]))

(defn xyp
  [xs]
  (ch/xy-plot (range) xs))

(defn add
  [p xs]
  (ch/add-lines p (range) xs))
