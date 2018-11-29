package ab.gitdemo.webapi.restapi

import ab.gitdemo.webapi.model.RepoData
import ab.gitdemo.webapi.model.UserData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface WebAPIInterface {


    @GET(Endpoint.USER_DETAIL)
    fun getUserDetails(@Path("username") username: String): Observable<UserData>

    @GET(Endpoint.USER_REPO_DETAIL)
    fun getReposDetails(@Path("username") username: String): Observable<ArrayList<RepoData>>


    object Endpoint {
        const val USER_DETAIL = "users/{username}"
        const val USER_REPO_DETAIL = "users/{username}/repos"
    }
}