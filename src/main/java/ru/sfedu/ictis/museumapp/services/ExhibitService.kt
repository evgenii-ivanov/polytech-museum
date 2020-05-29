package ru.sfedu.ictis.museumapp.services

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.sfedu.ictis.museumapp.models.Exhibit
import ru.sfedu.ictis.museumapp.models.PagedResponse
import ru.sfedu.ictis.museumapp.models.Response

interface ExhibitService {

    @GET("exhibits")
    fun getExhibits(): Observable<PagedResponse<Exhibit>>

    @GET("exhibits/{id}")
    fun getById(@Path("id") id: Int): Observable<Response<Exhibit>>

    companion object Factory {
        fun create(): ExhibitService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://91.203.192.84/v1/")
                .build()
            return retrofit.create(ExhibitService::class.java)
        }
    }
}