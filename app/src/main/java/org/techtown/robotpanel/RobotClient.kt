package org.techtown.robotpanel

import android.annotation.SuppressLint
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.techtown.robotpanel.data.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

object RobotClient {
    interface RobotApi {
        @FormUrlEncoded
        @POST("currmap")
        fun getCurrentMap(
            @Field("requestCode") requestCode: String
        ): Call<CurrentMapData>
        
        @GET("setting/navigation/pointlist")
        fun getPointList(
            @Query("requestCode") requestCode: String
        ): Call<PointListData>

        
        @GET("setting/cruze/pathlist")
        fun getPathList(
            @Query("requestCode") requestCode: String
        ): Call<PathListData>

        
        @GET("setting/sequence/sequencelist")
        fun getSequenceList(
            @Query("requestCode") requestCode: String
        ): Call<SequenceListData>

        
        @GET("mode/sequence/cancel")
        fun getSequenceCancel(
            @Query("requestCode") requestCode: String
        ): Call<RobotSimpleData>

        
        @GET("mode/navi/start")
        fun postNaviStart(
            @Query("requestCode") requestCode: String,
            @Query("locations") locations: String
        ): Call<RobotSimpleData>


        @FormUrlEncoded
        @POST("mode/cruze/start")
        fun postCruzeStart(
            @Field("requestCode") requestCode: String,
            @Field("pathname") pathname: String,
            @Field("count") count: String,
        ): Call<RobotSimpleData>


        @FormUrlEncoded
        @POST("mode/sequence/start")
        fun postSequenceStart(
            @Field("requestCode") requestCode: String,
            @Field("sequence") sequence: String
        ): Call<RobotSimpleData>

        @FormUrlEncoded
        @POST("mode/sequence/startbyname")
        fun postSequenceStartByName(
            @Field("requestCode") requestCode: String,
            @Field("name") name: String
        ): Call<RobotSimpleData>


        @FormUrlEncoded
        @POST("command/bumper/release")
        fun postBumperRelease(
            @Field("requestCode") requestCode: String,
        ): Call<RobotSimpleData>

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
            .baseUrl(AppData.BASE_URL_ROBOT)
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