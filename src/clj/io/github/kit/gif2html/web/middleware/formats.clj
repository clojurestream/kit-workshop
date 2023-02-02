(ns io.github.kit.gif2html.web.middleware.formats
  (:require
    [luminus-transit.time :as time]
    [muuntaja.core :as m]))

(def instance
  (m/create
    (-> m/default-options
        (update-in
          [:formats "application/transit+json" :decoder-opts]
          (partial merge time/time-deserialization-handlers))
        (update-in
          [:formats "application/transit+json" :encoder-opts]
          (partial merge time/time-serialization-handlers)))))
