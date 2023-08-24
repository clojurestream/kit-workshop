(ns io.github.kit.gif2html.web.controllers.gifs
  (:require
   [ring.util.http-response :as http-response]
   [gif-to-html.convert :as convert]
   [hato.client :as hato]))

(def Gif
  [:map
   [:id integer?]
   [:ascii map?]
   [:name string?]])

(defn save-gif [{:keys [query-fn http-client] :as opts}
                {{{link :link name :name} :body} :parameters}]
  (try
    (->> (hato/get
          link
          {:http-client http-client
           :as          :stream})
         :body
         (convert/gif->html)
         (assoc {:name name} :ascii)
         (query-fn :create-gif!)
         (first)
         (http-response/ok))
    (catch Exception _e
      (http-response/internal-server-error))))

(defn list-gifs [{:keys [query-fn] :as opts} _]
  (http-response/ok (query-fn :list-gifs {})))

(defn get-gif-by-id [{:keys [query-fn] :as opts} {{params :path} :parameters}]
  (http-response/ok (query-fn :get-gif-by-id params)))
