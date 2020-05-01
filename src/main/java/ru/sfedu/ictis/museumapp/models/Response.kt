package ru.sfedu.ictis.museumapp.models

data class PagedResponse<T> (val ok: Boolean, val responseData: List<T>)
data class Response<T> (val ok: Boolean, val responseData: T)