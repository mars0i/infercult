(defproject infercult "0.1.0"
  :description "Aggregate cultural transmission models involving inference."
  :url "https://github.com/mars0i/infercult"
  :license {:name "Gnu General Public License version 3.0"
            :url "http://www.gnu.org/copyleft/gpl.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [incanter "1.5.7"]]
  :main ^:skip-aot infercult.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
