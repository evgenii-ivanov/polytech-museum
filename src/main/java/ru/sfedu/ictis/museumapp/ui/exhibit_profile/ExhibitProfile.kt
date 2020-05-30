package ru.sfedu.ictis.museumapp.ui.exhibit_profile

import android.media.MediaPlayer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import ru.sfedu.ictis.museumapp.R
import ru.sfedu.ictis.museumapp.repositories.ExhibitRepository
import ru.sfedu.ictis.museumapp.services.ExhibitService
import ru.sfedu.ictis.museumapp.ui.exhibits.ExhibitsFragment
import ru.sfedu.ictis.museumapp.ui.exhibits.ExhibitsFragmentArgs
import ru.sfedu.ictis.museumapp.ui.exhibits.ExhibitsViewModel

class ExhibitProfile : Fragment() {

    companion object {
        fun newInstance() = ExhibitProfile()
    }

    object ExhibitRepositoryProvider {
        fun provideExhibitRepository(apiService: ExhibitService): ExhibitRepository {
            return ExhibitRepository(apiService)
        }
    }

    val args: ExhibitsFragmentArgs by navArgs()
    private lateinit var exhibitsViewModel: ExhibitsViewModel

    var mPlayer: MediaPlayer? = null
    var play_button: ImageView? = null
    var exhibitName: String = ""
    var exhibitDescription: String = ""

    private lateinit var viewModel: ExhibitProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.exhibit_profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ExhibitProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        initAudioPlayer()
        initExhibitData()
        super.onStart()
    }

    fun initAudioPlayer() {
        val id = args.exhibitId
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

    fun initExhibitData() {
        val apiService = ExhibitService.create()
        val repository = ExhibitsFragment.ExhibitRepositoryProvider.provideExhibitRepository(apiService)
        val id = args.exhibitId
        repository.getById(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                    result ->
                if (result.responseData != null) {
                    if (result.ok) {
                        exhibitName = result.responseData.name
                        exhibitDescription = result.responseData.description
                        val name_text = this.activity?.findViewById(R.id.name_text) as TextView
                        val desc_text = this.activity?.findViewById(R.id.desc_text) as TextView
                        name_text.setText(exhibitName)
                        desc_text.setText(exhibitDescription)
                        val imageView = this.activity?.findViewById(R.id.imageView) as ImageView
                        if (result.responseData.image.isNotEmpty()) {
                            Glide.with(this)
                                .load("http://91.203.192.84" + result.responseData.image.first())
                                .into(imageView)
                        }
                    }
                }
            }, { error ->
                error.printStackTrace()
            })

    }

}
