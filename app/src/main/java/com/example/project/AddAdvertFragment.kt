package com.example.project

import android.media.Image
import android.os.Binder
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.text.isDigitsOnly
import com.example.project.databinding.FragmentAddAdvertBinding
import com.example.project.presenter.AdvertPresenter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
class AddAdvertFragment : Fragment(), Contract.AddAdvertView {
    lateinit var binding: FragmentAddAdvertBinding
    private val FIRST_BUTTON_CODE = 1
    private val SECOND_BUTTON_CODE = 2
    private val THIRD_BUTTON_CODE = 3
    private lateinit var presenter: AdvertPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddAdvertBinding.inflate(inflater, container, false)
        presenter = AdvertPresenter(this, activity as MainActivity)
        binding.addAdvertFragmentArrowBack.setOnClickListener(View.OnClickListener {
            presenter.onArrowBackIsClicked()
        })
        binding.createAdvertButton.setOnClickListener(View.OnClickListener {
            presenter.onAddAdvertButtonIsClicked(binding.titleEditText.text.toString(), binding.descriptionEditText.text.toString(), binding.coastEditText.text.toString(),
                binding.categoryEditText.text.toString(), binding.breedEditText.text.toString(), binding.ageEditText.text.toString(), binding.genderEditText.text.toString())
        })
        binding.firstContainer.setOnClickListener(View.OnClickListener {
            presenter.onUploadImageButtonIsPressed(FIRST_BUTTON_CODE)
        })
        binding.secondContainer.setOnClickListener(View.OnClickListener {
            presenter.onUploadImageButtonIsPressed(SECOND_BUTTON_CODE)
        })
        binding.thirdContainer.setOnClickListener(View.OnClickListener {
            presenter.onUploadImageButtonIsPressed(THIRD_BUTTON_CODE)
        })
        return binding.root
    }

    override fun dataIsEmptySnackbar() {
        Snackbar.make(binding.root, "Please enter all fields", Snackbar.LENGTH_LONG).show()
    }

}

