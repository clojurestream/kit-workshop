(ns io.github.kit.gif2html.web.routes.api
  (:require
    [io.github.kit.gif2html.web.controllers.health :as health]
    [io.github.kit.gif2html.web.middleware.exception :as exception]
    [io.github.kit.gif2html.web.middleware.formats :as formats]
    [integrant.core :as ig]
    [reitit.coercion.malli :as malli]
    [reitit.ring.coercion :as coercion]
    [reitit.ring.middleware.muuntaja :as muuntaja]
    [reitit.ring.middleware.parameters :as parameters]
    [reitit.swagger :as swagger]
    [io.github.kit.gif2html.web.controllers.gifs :as gifs]))

;; Routes
(defn api-routes [opts]
  [["/swagger.json"
    {:get {:no-doc  true
           :swagger {:info {:title "io.github.kit.gif2html API"}}
           :handler (swagger/create-swagger-handler)}}]
   ["/health"
    {:get health/healthcheck!}]
   ["/gifs"
    ["" {:post {:summary    "creates a new gif and returns the inserted row"
                :parameters {:body [:map
                                    [:link string?]
                                    [:name string?]]}
                :responses  {200 {:body gifs/Gif}}
                :handler    (partial gifs/save-gif opts)}
         :get  {:summary   "returns all created gifs"
                :responses {200 {:body [:vector gifs/Gif]}}
                :handler   (partial gifs/list-gifs opts)}}]
    ["/:id" {:get {:summary    "gets a single gif based off of ID"
                   :parameters {:path [:map [:id integer?]]}
                   :responses  {200 {:body gifs/Gif}}
                   :handler    (partial gifs/get-gif-by-id opts)}}]]])

(defn route-data
  [opts]
  (merge
    opts
    {:coercion   malli/coercion
     :muuntaja   formats/instance
     :swagger    {:id ::api}
     :middleware [;; query-params & form-params
                  parameters/parameters-middleware
                  ;; content-negotiation
                  muuntaja/format-negotiate-middleware
                  ;; encoding response body
                  muuntaja/format-response-middleware
                  ;; exception handling
                  coercion/coerce-exceptions-middleware
                  ;; decoding request body
                  muuntaja/format-request-middleware
                  ;; coercing response bodys
                  coercion/coerce-response-middleware
                  ;; coercing request parameters
                  coercion/coerce-request-middleware
                  ;; exception handling
                  exception/wrap-exception]}))

(derive :reitit.routes/api :reitit/routes)

(defmethod ig/init-key :reitit.routes/api
  [_ {:keys [base-path]
      :or   {base-path ""}
      :as   opts}]
  [base-path (route-data opts) (api-routes opts)])
