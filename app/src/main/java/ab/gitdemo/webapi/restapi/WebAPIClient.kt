package ab.gitdemo.webapi.restapi

import ab.gitdemo.webapi.model.RepoData
import ab.gitdemo.webapi.model.UserData
import android.content.Context
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class WebAPIClient(private val jsonClient: Retrofit) {

    private fun json(): WebAPIInterface {
        return jsonClient.create(WebAPIInterface::class.java)
    }

    fun getUserDetails(username: String): Observable<UserData> {
        return json().getUserDetails(username)
    }

    fun getReposDetails(username: String): Observable<ArrayList<RepoData>> {
        return json().getReposDetails(username)
    }

    companion object {
        private val TAG = WebAPIClient::class.java.simpleName

        var baseUrl = "https://api.github.com/"

        fun getInstance(c: Context): WebAPIClient {
            val builder = OkHttpClient().newBuilder()
            builder.readTimeout(60, TimeUnit.MINUTES)
            builder.connectTimeout(60, TimeUnit.MINUTES)

            /* HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);*/

            val httpClient = builder.build()

            val json = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build()

            return WebAPIClient(json)
        }
    }

}
