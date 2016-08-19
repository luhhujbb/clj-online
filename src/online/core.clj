(ns online.core
  (:require [clojure.string :as str]
            [clojure.tools.logging :as log]
            [cheshire.core :refer :all]
            [clj-http.client :as http]
            [digest]))

(def creds (atom {}))

(def initialized (atom false))

(defn initialized?
  []
  @initialized)

(def endpoint "https://api.online.net/api/v1")

(def request-conf {:throw-exceptions false})

(defn mk-headers [] {"Authorization" (str "Bearer " (:token @creds))})

(defn validate
  ([res code]
    (validate res code nil))
  ([res code fallback-value]
  (if (= code (:status res))
    (:body res)
    fallback-value)))

(defmulti call (fn [params] (:method params)))

(defmethod call "GET" [params]
  (let [url (str endpoint (:resource params))
        opts (merge {:headers (mk-headers) :accept :json :as :json} request-conf)]
        (try
          (http/get url opts)
          (catch Exception e
            (log/error "Ressource : "url "- Error :" e)
            {:status 500}))))

(defmethod call "PUT" [params]
  (let [url (str endpoint (:resource params))
        opts (merge {:form-params (:body params) :headers (mk-headers)} request-conf)]
      (try
        (http/post url opts)
        (catch Exception e
          (log/error "Ressource : "url "- Error :" e)
          {:status 500}))))

(defmethod call "POST" [params]
  (let [url (str endpoint (:resource params))
        opts (merge {:form-params (:body params) :headers (mk-headers)} request-conf)]
      (try
        (http/post url opts)
        (catch Exception e
          (log/error "Ressource : "url "- Error :" e)
          {:status 500}))))

(defmethod call "DELETE" [params]
 (let [url (str endpoint (:resource params))
       opts (merge {:headers (mk-headers)} request-conf)]
    (try
      (http/delete url opts)
      (catch Exception e
        (log/error "Ressource : "url "- Error :" e)
        {:status 500}))))

(defmethod call :default [params]
  (log/info "Unsupported http verb"))

(defn init!
  [token]
  (swap! creds assoc :token token)
  (reset! initialized true))
