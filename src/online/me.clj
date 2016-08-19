(ns online.me
  (:require [clojure.string :as str]
            [clojure.tools.logging :as log]
            [cheshire.core :refer :all]
            [clj-http.util :as util]
            [online.core :as o]))

(def api-path "/user")

(defn get-user-infos
  []
    (o/validate
      (if (o/initialized)
        (o/call {:method "GET"
                 :resource api-path})
        {:status 403}) 200))

(defn list-ssh-keys
  []
  (o/validate
    (if (o/initialized)
    (o/call {:method "GET"
             :resource (str api-path "/key/ssh")})
    {:status 403}) 200))

(defn get-ssh-key
  [key-id]
  (o/validate
    (if (o/initialized)
    (o/call {:method "GET"
             :resource (str api-path "/key/ssh/" key-id)})
    {:status 403}) 200))

(defn add-ssh-key
  [desc pub-string]
  (o/validate
    (if (o/initialized)
    (o/call {:method "POST"
             :resource (str api-path "/key/ssh")
             :body {:description desc
                    :content pub-string}})
    {:status 403}) 201))

(defn del-ssh-key
  [key-id]
  (o/validate
    (if (o/initialized)
    (o/call {:method "DELETE"
             :resource (str api-path "/key/ssh/" key-id)})
    {:status 403}) 204))
