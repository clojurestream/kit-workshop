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


(comment
  ;; Test save
  (let [{:keys [:db.sql/query-fn :http/hato]} integrant.repl.state/system]
    (save-gif {:query-fn query-fn :http-client hato}
              {:parameters {:body {:link "https://media.tenor.com/JMzBeLgNaSoAAAAj/banana-dance.gif" :name "foo"}}}))
  
  ;; Test save v2
  (save-gif (user/api-ctx) {:parameters {:body {:link "https://media.tenor.com/JMzBeLgNaSoAAAAj/banana-dance.gif" :name "foo"}}})
  
  ;; Test get gifs
  (->> (list-gifs (user/api-ctx) nil)
       :body
       (map #(select-keys % [:name :id])))
  
  ;; Test get gif
  (get-gif-by-id (user/api-ctx) {:parameters {:path {:id 3}}}))
