(ns online.server
  (:require [clojure.string :as str]
            [clojure.tools.logging :as log]
            [cheshire.core :refer :all]
            [online.core :as online]))

(def api-path "/server")