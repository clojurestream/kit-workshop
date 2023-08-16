(ns io.github.kit.gif2html.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init       (fn []
                 (log/info "\n-=[io.github.kit.gif2html starting]=-"))
   :start      (fn []
                 (log/info "\n-=[io.github.kit.gif2html started successfully]=-"))
   :stop       (fn []
                 (log/info "\n-=[io.github.kit.gif2html has shut down successfully]=-"))
   :middleware (fn [handler _] handler)
   :opts       {:profile :prod}})
