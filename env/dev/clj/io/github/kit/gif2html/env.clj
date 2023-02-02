(ns io.github.kit.gif2html.env
  (:require
    [clojure.tools.logging :as log]
    [io.github.kit.gif2html.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init       (fn []
                 (log/info "\n-=[io.github.kit.gif2html starting using the development or test profile]=-"))
   :start      (fn []
                 (log/info "\n-=[io.github.kit.gif2html started successfully using the development or test profile]=-"))
   :stop       (fn []
                 (log/info "\n-=[io.github.kit.gif2html has shut down successfully]=-"))
   :middleware wrap-dev
   :opts       {:profile       :dev
                :persist-data? true}})
