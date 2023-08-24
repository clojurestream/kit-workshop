(require
 '[org.httpkit.server :as srv]
 '[hiccup.core :as hp]
 '[cheshire.core :as json]
 '[org.httpkit.client :as http])

(defn animation [{:keys [delay frames frame-count]}]
  [:div
   [:div
    (map-indexed
     (fn [i frame]
       [:pre
        {:id    (str "frame-" i)
         :style "font-size:4pt;line-height:4pt;letter-spacing:1px;font-weight:bold;display:none;font-family:monospace;"}
        frame])
     frames)]
   [:script {:type "text/javascript"}
    "var delay = " delay ";"]
   [:script {:type "text/javascript"}
    "var totalFrames = " (dec frame-count) ";"]
   [:script {:type "text/javascript"}
    "var animation;
          function showNextFrame(frame) {
            document.getElementById('frame-' + ((frame > 0) ? frame - 1 : totalFrames)).style.display = 'none';
            document.getElementById('frame-' + frame).style.display = 'block';;
            animation = setTimeout(function(){showNextFrame((frame < totalFrames) ? frame + 1 : 0);}, delay);}
          showNextFrame(0);"]])

(defn home-page []
  (let [animations (-> @(http/get "http://localhost:3000/api/gifs")
                       :body
                       (json/decode true))]
    [:html
     [:body
      (animation (:ascii (last animations)))]]))

(defonce server (atom nil))

(defn run []
  (let [port 3001
        url  (str "http://localhost:" port "/")]
    (when-let [server @server]
      (server))
    (reset! server
            (srv/run-server
             (fn [_request]
               {:body (hp/html (home-page))
                :status 200})
             {:port port}))
    (println "serving" url)))

(run)
@(promise)