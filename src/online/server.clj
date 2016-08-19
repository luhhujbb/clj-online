(ns online.server
  (:require [clojure.string :as str]
            [clojure.tools.logging :as log]
            [cheshire.core :refer :all]
            [online.core :as o]))

(def api-path "/server")

(defn list-servers
  []
  (map
    (fn [x]
      (last (str/split x #"/")))
  (o/validate
    (if (o/initialized?)
      (o/call {:method "GET"
               :resource api-path})
      {:status 403}) 200 () )))

(defn describe
  [server-id]
  (o/validate
    (if (o/initialized?)
      (o/call {:method "GET"
               :resource (str api-path "/" server-id)})
      {:status 403}) 200 ))

(defn failover
  []
  (o/validate
    (if (o/initialized?)
      (o/call {:method "GET"
               :resource (str api-path "/failover")})
      {:status 403}) 200 ))

(defn reboot
  ([server-id]
    (reboot server-id "unknown" nil))
  ([server-id reason email]
    (let [body (if (nil? reason)
                  {}
                  {:reason reason})
          body* (if (nil? email)
                  body
                  (assoc body :email email))]
    (if (o/initialized?)
      (o/call {:method "POST"
                 :resource (str api-path "/reboot/" server-id)
                 :body body*})
      {:status 403}))))
