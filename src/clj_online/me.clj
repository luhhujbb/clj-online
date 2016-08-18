(ns clj-online.me
  (:require [clojure.string :as str]
            [clojure.tools.logging :as log]
            [cheshire.core :refer :all]
            [clj-http.util :as util]
            [online.core :as online]))

(def api-path "/user")
