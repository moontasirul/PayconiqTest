package com.payconiq.testApplication.ui.gitHubUserList

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.payconiq.testApplication.Items
import com.payconiq.testApplication.R
import com.payconiq.testApplication.databinding.FragmentGithubUserListBinding
import com.payconiq.testApplication.ui.details.DetailsFragment
import com.payconiq.testApplication.utils.EspressoIdlingResource
import com.payconiq.testApplication.utils.PaginationScrollListener
import com.payconiq.testApplication.utils.Utils
import com.payconiq.testApplication.utils.dialogUtils.CustomDialogCallback
import com.payconiq.testApplication.utils.dialogUtils.CustomDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GitHubUserListFragment : Fragment(),
    GitHubUserListNavigator, GitHubUserListRecyclerViewAdapter.GitHubUserItemAdapterListener {

    companion object {
        const val GitHubUser_ITEM = "GitHubUser_ITEM"
    }

    private lateinit var userListBinding: FragmentGithubUserListBinding
    private val viewModel by viewModels<GitHubUserListViewModel>()


    private lateinit var gitHubUserAdapter: GitHubUserListRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userListBinding = FragmentGithubUserListBinding.inflate(layoutInflater)
        userListBinding.lifecycleOwner = this
        userListBinding.userListViewModel = viewModel
        return userListBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setNavigator(this)
        EspressoIdlingResource.increment()
        setupObservers()
        setUPSearch()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        userListBinding.gitHubUserRecyclerView.addOnScrollListener(object :
            PaginationScrollListener(userListBinding.gitHubUserRecyclerView.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                viewModel.isLoading.value = true
                viewModel.currentPage += 1

                Looper.myLooper()?.let {
                    Handler(it).postDelayed({
                        if (Utils.hasInternetConnection(requireContext())) {
                            viewModel.searchGitHubUser()
                        } else {
                            showDialog(
                                requireContext().resources.getString(R.string.msg_failed_title),
                                requireContext().resources.getString(R.string.internet_error_message),
                                true
                            )
                        }
                    }, 1000)
                }
            }

            override fun getTotalPageCount(): Int {
                return viewModel.totalPages
            }

            override fun isLastPage(): Boolean {
                return viewModel.isLastPage
            }

            override fun isLoading(): Boolean {
                var loading = false
                viewModel.isLoading.value?.let {
                    loading = it
                }
                return loading
            }

        })
    }

    private fun setUPSearch() {
        userListBinding.searchView.isActivated = true
        userListBinding.searchView.queryHint =
            requireContext().resources.getString(R.string.search_hits_text)
        userListBinding.searchView.onActionViewExpanded()
        userListBinding.searchView.isIconified = false
        userListBinding.searchView.clearFocus()

        userListBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (Utils.hasInternetConnection(requireContext())) {
                        viewModel.searchTest.value = it
                        viewModel.currentPage = 1
                        viewModel.searchGitHubUser()
                    } else {
                        showDialog(
                            requireContext().resources.getString(R.string.msg_failed_title),
                            requireContext().resources.getString(R.string.internet_error_message),
                            true
                        )
                    }
                }
                userListBinding.searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchTest.value = newText
                return false
            }
        })
    }


    private fun setupObservers() {
        viewModel.response.observe(requireActivity()) { response ->
            response?.let {
                EspressoIdlingResource.decrement()
                viewModel.getUserResponse(it)
            }
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
        viewModel.isDataFetched.value = true
        gitHubUserAdapter = GitHubUserListRecyclerViewAdapter()
        userListBinding.gitHubUserRecyclerView.adapter = gitHubUserAdapter
        gitHubUserAdapter.setListener(this)
        gitHubUserAdapter.clearItems()
        gitHubUserAdapter.addItems(userList)
    }


    override fun onUserContent(mUserListModel: Items) {
        val bundle = bundleOf(GitHubUser_ITEM to mUserListModel)
        findNavController().navigate(
            R.id.action_GitHubUserListFragment_to_DetailFragment,
            bundle
        )
    }


    override fun hideKeyBoard() {
        userListBinding.searchView.clearFocus()
    }

}




