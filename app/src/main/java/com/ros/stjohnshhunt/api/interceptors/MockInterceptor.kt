package com.ros.stjohnshhunt.api.interceptors

import android.content.Context
import com.ros.stjohnshhunt.BuildConfig
import com.ros.stjohnshhunt.extensions.readAssetsFile
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url.toUri().toString()

            return chain.proceed(chain.request())
                .newBuilder()
                .code(getResponseCode(uri))
                .protocol(Protocol.HTTP_2)
                .body(getResponse(uri).toByteArray()
                        .toResponseBody("application/json".toMediaTypeOrNull()))
                .addHeader("content-type", "application/json")
                .build()
        } else {
            throw IllegalAccessError("MockInterceptor is only meant for Testing Purposes and " +
                    "bound to be used only with DEBUG mode")
        }
    }

    private fun getResponse(uri: String): String {
        return when {
            uri.contains("properties/list-residential") ->
                context.assets.readAssetsFile(MOCK_RESPONSE_listResidential)
            else -> ""
        }
    }

    private fun getResponseCode(uri: String): Int {
        return when {
            uri.contains("properties/list-residential") -> SUCCESS_CODE
            else -> ERROR_CODE
        }
    }

    companion object {
        const val SUCCESS_CODE = 200
        const val ERROR_CODE = 400
        const val MOCK_RESPONSE_listResidential = "response_listResidential.json"
    }

}
