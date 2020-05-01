package ru.sfedu.ictis.museumapp.repositories

import io.reactivex.Observable
import ru.sfedu.ictis.museumapp.models.Exhibit
import ru.sfedu.ictis.museumapp.models.PagedResponse
import ru.sfedu.ictis.museumapp.services.ExhibitService

class ExhibitRepository(private val apiService: ExhibitService) {
    fun getExhibits(limit: Int, offset: Int): Observable<PagedResponse<Exhibit>> {
        return apiService.getExhibits()
    }
}