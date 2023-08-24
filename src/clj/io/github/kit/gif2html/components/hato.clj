(ns io.github.kit.gif2html.components.hato
  (:require
   [integrant.core :as ig]
   [hato.client :as hc]))

(defmethod ig/init-key :http/hato [_ opts]
  (hc/build-http-client opts))

(defmethod ig/halt-key! :http/hato [_ _http-client]
  ;; If there was effectful logic here to stop it you would do it here
  nil)
