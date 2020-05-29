package ru.sfedu.ictis.museumapp.ui.exhibits

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.sfedu.ictis.museumapp.R
import ru.sfedu.ictis.museumapp.repositories.ExhibitRepository
import ru.sfedu.ictis.museumapp.services.ExhibitService

class ExhibitsFragment : Fragment() {

    object ExhibitRepositoryProvider {
        fun provideExhibitRepository(apiService: ExhibitService): ExhibitRepository {
            return ExhibitRepository(apiService)
        }
    }

    private lateinit var exhibitsViewModel: ExhibitsViewModel

    var mPlayer: MediaPlayer? = null
    var id: Int? = 1011
    var play_button: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exhibitsViewModel =
            ViewModelProviders.of(this).get(ExhibitsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_exhibits, container, false)
        //val apiService = ExhibitService.create()
        //val repository = ExhibitRepositoryProvider.provideExhibitRepository(apiService)
        /*
        if (id != null) {
            repository.getById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                        result ->
                    if (result.responseData != null) {
                        val exhibit = result
                        exhibitIntent.putExtra(ExhibitActivity.EXHIBIT_NAME, user.login)
                        exhibitIntent.putExtra(ExhibitActivity.EXHIBIT_IMAGE, user.avatar_url)
                        startActivity(exhibitIntent)
                    }
                    startActivity(exhibitIntent)
                }, { error ->
                    error.printStackTrace()
                    startActivity(exhibitIntent)
                })
        }
        */

        return root
    }

    override fun onStart() {
        initAudioPlayer()
        super.onStart()
    }

    fun initAudioPlayer() {
        when (id) {
            1011 -> {
                mPlayer = MediaPlayer.create(context, R.raw.exhibit1011)
            }
            1014 -> {
                mPlayer = MediaPlayer.create(context, R.raw.exhibit1014)
            }
            else -> {
                mPlayer = MediaPlayer.create(context, R.raw.undefined)
            }
        }
        play_button = this.activity?.findViewById(R.id.play_btn) as ImageView
        mPlayer?.setOnPreparedListener {
            println("Ready to GO")
        }

        play_button?.setOnClickListener {
            mPlayer?.start()
        }

        /*
        stop_button.setOnClickListener {
            mPlayer?.pause()
        }
         */
    }


}