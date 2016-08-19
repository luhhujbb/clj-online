(ns online.rpn
  (:require [clojure.string :as str]
            [clojure.tools.logging :as log]
            [cheshire.core :refer :all]
            [online.core :as o]))

(def api-path "/rpn")

(defn list-rpn-groups
  []
  (o/validate
    (if (o/initialized?)
      (o/call {:method "GET"
               :resource (str api-path "/group")})
      {:status 403}) 200 ))

(defn describe-group
  [group-id]
  (o/validate
    (if (o/initialized?)
      (o/call {:method "GET"
               :resource (str api-path "/group/" group-id)})
      {:status 403}) 200 ))

(defn create-group
  [name server-list]
  (o/validate
    (if (o/initialized?)
      (o/call {:method "POST"
               :resource (str api-path "/group")
               :body {:name name
                      :server_ids (str/join "," server-list)}})
      {:status 403}) 200 ))

(defn add-servers
  [group-id server-list]
  (o/validate
    (if (o/initialized?)
      (o/call {:method "POST"
               :resource (str api-path "/group/addServers")
               :body {:group_id group-id
                      :server_ids (str/join "," server-list)}})
      {:status 403}) 200 ))

(defn remove-servers
  [group-id server-list]
  (o/validate
    (if (o/initialized?)
      (o/call {:method "POST"
               :resource (str api-path "/group/removeServers")
               :body {:group_id group-id
                      :server_ids (str/join "," server-list)}})
      {:status 403}) 200 ))
