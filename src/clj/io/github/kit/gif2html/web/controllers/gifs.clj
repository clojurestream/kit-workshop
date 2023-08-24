(ns io.github.kit.gif2html.web.controllers.gifs
  (:require
   [ring.util.http-response :as http-response]))

(def Gif
  [:map
   [:id integer?]
   [:ascii map?]
   [:name string?]])

(defn save-gif [{:keys [query-fn] :as opts}
                {{{link :link name :name} :body} :parameters}]
  (-> (query-fn :create-gif! {:ascii {:blob link} :name name})
      (first)
      (http-response/ok)))

(defn list-gifs [{:keys [query-fn] :as opts} _]
  (http-response/ok (query-fn :list-gifs {})))

(defn get-gif-by-id [{:keys [query-fn] :as opts} {{params :path} :parameters}]
  (http-response/ok (query-fn :get-gif-by-id params)))
