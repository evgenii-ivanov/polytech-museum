package ru.sfedu.ictis.museumapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.sfedu.ictis.museumapp.R
import ru.sfedu.ictis.museumapp.repositories.ExhibitRepository
import ru.sfedu.ictis.museumapp.services.ExhibitService

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    object ExhibitRepositoryProvider {
        fun provideExhibitRepository(apiService: ExhibitService): ExhibitRepository {
            return ExhibitRepository(apiService)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val apiService = ExhibitService.create()
        val repository = HomeFragment.ExhibitRepositoryProvider.provideExhibitRepository(apiService)
        var text = "No text"
        repository.getExhibits(10, 0)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                    result ->
                if (result.responseData.isNotEmpty()) {
                    text = result.toString()
                }
            }, { error ->
                error.printStackTrace()
            })
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = text
        })
        return root
    }
}