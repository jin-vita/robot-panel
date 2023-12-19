package org.techtown.robotpanel

import android.annotation.SuppressLint
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.techtown.robotpanel.data.DoorData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

object DoorClient {
    interface RobotApi {
        // 자동문 열기
        @FormUrlEncoded
        @POST("door-control")
        fun postDoorControl(
            @Field("requestCode") requestCode: String,
            @Field("source") source: String,
            @Field("target") target: String,
            @Field("command") command: String
        ): Call<DoorData>

    }

    private const val TAG = "RobotClient"

    @Volatile
    private var instance: RobotApi? = null
    val api: RobotApi get() = getInstance()

    @Synchronized
    fun getInstance(): RobotApi {
        if (instance == null) instance = create()
        return instance as RobotApi
    }

    @Synchronized
    fun resetInstance() {
        instance = create()
    }

    private fun create(): RobotApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val clientBuilder = OkHttpClient.Builder()

        @SuppressLint("CustomX509TrustManager")
        val x509TrustManager: X509TrustManager = object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                AppData.debug(TAG, ": authType: $authType")
            }

            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                AppData.debug(TAG, ": authType: $authType")
            }
        }

        try {
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory
            clientBuilder.sslSocketFactory(sslSocketFactory, x509TrustManager)
        } catch (e: Exception) {
            AppData.error(TAG, e.message!!)
        }

        clientBuilder.hostnameVerifier(RelaxedHostNameVerifier())

        val headerInterceptor = Interceptor {
            val request = it.request()
                .newBuilder()
                .build()
            return@Interceptor it.proceed(request)
        }
        if (true) {
            clientBuilder.addInterceptor(headerInterceptor)
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }

        clientBuilder.connectTimeout(5, TimeUnit.SECONDS)
        clientBuilder.readTimeout(5, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(5, TimeUnit.SECONDS)

        val client = clientBuilder.build()

        return Retrofit.Builder()
            .baseUrl(AppData.BASE_URL_DOOR)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RobotApi::class.java)
    }

    @SuppressLint("CustomX509TrustManager")
    private val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()

        @SuppressLint("TrustAllX509TrustManager")
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        @SuppressLint("TrustAllX509TrustManager")
        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        }
    })

    class RelaxedHostNameVerifier : HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        override fun verify(hostname: String, session: SSLSession): Boolean {
            return true
        }
    }
}