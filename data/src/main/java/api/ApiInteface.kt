package api.api

import retrofit2.http.GET

interface ApiInterface {
    @GET("uc?export=download&id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r")
    suspend fun getResponse() : Response

}