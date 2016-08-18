(ns clj-online.core
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

(def request-conf {:accept :json
                   :as :json
                   :throw-exceptions false})

(defn mk-headers [] {"Authorization" (str "Bearer " (:token @conf))})

(defn init!
  [token]
  (swap! creds assoc :token token)
  (reset! initialized true))
