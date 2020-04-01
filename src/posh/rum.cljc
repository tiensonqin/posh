(ns posh.rum
  (:require #?(:clj  [posh.plugin-base :as base]
               :cljs [posh.plugin-base :as base :include-macros true])
            [datascript.core :as d]
            [rum.core :as rum]))

#?(:cljs
   (defn make-reaction [reaction key & local-mixin]
     (rum/derived-atom [reaction] key identity)))

(def dcfg
  (let [dcfg {:db            d/db
              :pull*         d/pull
              :pull-many     d/pull-many
              :q             d/q
              :filter        d/filter
              :with          d/with
              :entid         d/entid
              :transact!     d/transact!
              :listen!       d/listen!
              :conn?         d/conn?
              :ratom         atom
              :react         rum/react
              :make-reaction #?(:clj  nil
                                :cljs make-reaction)}]
    (assoc dcfg :pull (partial base/safe-pull dcfg))))

(base/add-plugin dcfg)
