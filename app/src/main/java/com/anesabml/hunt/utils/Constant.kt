package com.anesabml.hunt.utils

object Constant {
    const val GRAPHQL_API = "https://api.producthunt.com/v2/api/graphql"
    const val REDIRECT_URI = "auth://callback"
    const val API_KEY = "GfOhHzzZChuSWmCSXk0AXIBKg6rIdVLH9VPY8wMdYEU"
    const val API_SECRET = "5b-Amo-N4c58ciWCX7TlgDIGgLvtGVltRM-7r08BcYg"
    const val TOKEN = "AP-G2nP-rjOTVSJpEbqmceRc_NWLqQvSgYtO_3IG8G0"
    const val PRODUCT_HUNT_API = "https://api.producthunt.com/"
    const val AUTH_API =
        "${PRODUCT_HUNT_API}v2/oauth/authorize" + "?client_id=$API_KEY" + "&redirect_uri=$REDIRECT_URI" + "&response_type=code" + "&scope=public+private"
}
