package com.payconiq.testApplication.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.payconiq.testApplication.Items
import com.payconiq.testApplication.R
import com.payconiq.testApplication.databinding.FragmentDetailsBinding
import com.payconiq.testApplication.utils.Utils
import com.payconiq.testApplication.utils.dialogUtils.CustomDialogCallback
import com.payconiq.testApplication.utils.dialogUtils.CustomDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(), IDetailsNavigator {

    companion object {
        const val DIALOG_TAG = "dialog"
        const val GitHubUser_ITEM = "GitHubUser_ITEM"
    }

    private lateinit var detailsBinding: FragmentDetailsBinding
    private val viewModel by viewModels<DetailsViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailsBinding = FragmentDetailsBinding.inflate(layoutInflater)
        detailsBinding.lifecycleOwner = this
        detailsBinding.detailsViewModel = viewModel
        return detailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setNavigator(this)
        arguments?.getParcelable<Items>(GitHubUser_ITEM)?.let {
            if (Utils.hasInternetConnection(requireContext())) {
                viewModel.fetchUserInfo(it)
            } else {
                showDialog(
                    requireContext().resources.getString(R.string.msg_failed_title),
                    requireContext().resources.getString(R.string.internet_error_message),
                    true
                )
            }
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.response.observe(requireActivity()) { response ->
            viewModel.getUserInfoResponse(response)
        }
    }


    fun showDialog(title: String, message: String, isOnlyPositive: Boolean) {
        var dialog: DialogFragment? = null
        var bntText = requireContext().getString(R.string.yes_txt)
        if (isOnlyPositive) {
            bntText = requireContext().getString(R.string.btn_text_ok)
        }
        dialog = CustomDialogFragment(
            title,
            message,
            bntText,
            requireContext().getString(R.string.cancel_text),
            object : CustomDialogCallback {
                override fun onNextClick() {

                }

                override fun onPositiveClick() {
                    dialog?.dismiss()
                    findNavController().popBackStack()
                }

                override fun onCloseClick() {
                    dialog?.dismiss()
                }
            },
            isOnlyPositive,
        )
        dialog.show(childFragmentManager, DIALOG_TAG)
    }

    override fun messageDialog(message: String) {
        showDialog(requireContext().resources.getString(R.string.msg_failed_title), message, true)
    }

}