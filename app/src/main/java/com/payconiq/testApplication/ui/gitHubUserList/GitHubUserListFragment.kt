package com.payconiq.testApplication.ui.gitHubUserList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.payconiq.testApplication.Items
import com.payconiq.testApplication.R
import com.payconiq.testApplication.databinding.FragmentGithubUserListBinding
import com.payconiq.testApplication.ui.details.DetailsFragment
import com.payconiq.testApplication.utils.dialogUtils.CustomDialogCallback
import com.payconiq.testApplication.utils.dialogUtils.CustomDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GitHubUserListFragment : Fragment(),
    GitHubUserListNavigator, GitHubUserListRecyclerViewAdapter.CityItemAdapterListener {

    companion object {
        const val USER_ITEM = "USER_ITEM"
    }

    private lateinit var cityAndFoodBinding: FragmentGithubUserListBinding
    private val viewModel by viewModels<GitHubUserListViewModel>()


    private lateinit var gitHubUserAdapter: GitHubUserListRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        cityAndFoodBinding = FragmentGithubUserListBinding.inflate(layoutInflater)
        cityAndFoodBinding.lifecycleOwner = this

        cityAndFoodBinding.userListViewModel = viewModel

        return cityAndFoodBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setNavigator(this)
        viewModel.isLoading.value = true
        viewModel.fetchGitHubUserResponse()
        setupObservers()

    }


    private fun setupObservers() {
        viewModel.response.observe(requireActivity()) { response ->
            viewModel.getUserResponse(response)
        }
    }

    override fun messageDialog(message: String) {
        showDialog(requireContext().resources.getString(R.string.msg_failed_title), message, true)
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
                    dialog?.dismiss()
                }

                override fun onPositiveClick() {
                    dialog?.dismiss()
                }

                override fun onCloseClick() {
                    dialog?.dismiss()
                }
            },
            isOnlyPositive,
        )
        dialog.show(childFragmentManager, DetailsFragment.DIALOG_TAG)
    }


    override fun onSetUserInfo(userList: ArrayList<Items>) {
        viewModel.isDataFetching.set(true)
        gitHubUserAdapter = GitHubUserListRecyclerViewAdapter()
        cityAndFoodBinding.gitHubUserRecyclerView.adapter = gitHubUserAdapter
        gitHubUserAdapter.setListener(this)
        gitHubUserAdapter.clearItems()
        gitHubUserAdapter.addItems(userList)
    }



    override fun onUserContent(mUserListModel: Items) {
        val bundle = bundleOf(USER_ITEM to mUserListModel)
        findNavController().navigate(
            R.id.action_cityAndFoodListFragment_to_DetailFragment,
            bundle
        )
    }

    override fun onRetryClick() {

    }

}




