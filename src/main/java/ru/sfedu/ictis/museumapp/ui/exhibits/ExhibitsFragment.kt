package ru.sfedu.ictis.museumapp.ui.exhibits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import ru.sfedu.ictis.museumapp.R

class ExhibitsFragment : Fragment() {

    private lateinit var exhibitsViewModel: ExhibitsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exhibitsViewModel =
            ViewModelProviders.of(this).get(ExhibitsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_exhibits, container, false)
        return root
    }
}