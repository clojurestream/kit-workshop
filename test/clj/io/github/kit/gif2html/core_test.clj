(ns io.github.kit.gif2html.core-test
  (:require
    [io.github.kit.gif2html.test-utils :as utils]
    [clojure.test :refer :all]
    [io.github.kit.gif2html.web.controllers.gifs :as gifs]))

(use-fixtures :once utils/system-fixture)

(defn test-ctx
  []
  (let [{:keys [:db.sql/query-fn :http/hato]} (utils/system-state)]
    {:query-fn    query-fn
     :http-client hato}))

(deftest test-parsing-and-loading-gif
  (testing "save GIF"
    (let [{status :status
           {:keys [id]} :body} (gifs/save-gif (test-ctx) {:parameters {:body {:link "https://media.tenor.com/JMzBeLgNaSoAAAAj/banana-dance.gif" :name "foo"}}})]
      (is (= 200 status))
      (is (nat-int? id))
      (testing "load GIF"
        (let [{:keys [status body]} (gifs/get-gif-by-id (test-ctx) {:parameters {:path {:id id}}})]
          (is (= 200 status))
          (is (= id (:id body)))))
      (testing "list GIFs"
        (is (-> (gifs/list-gifs (test-ctx) {}) :body vector?))))))
