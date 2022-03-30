package com.payconiq.testApplication.ui.gitHubUserList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.payconiq.testApplication.Items
import com.payconiq.testApplication.databinding.LayoutGithubUserItemBinding
import com.payconiq.testApplication.ui.base.BaseViewHolder

class GitHubUserListRecyclerViewAdapter(

) :
    RecyclerView.Adapter<GitHubUserListRecyclerViewAdapter.UserListViewHolder>() {

    lateinit var mListener: GitHubUserItemAdapterListener
    private var userList: ArrayList<Items> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {

        val binding: LayoutGithubUserItemBinding = LayoutGithubUserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return UserListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addItems(list: List<Items>) {
        userList.addAll(list)
        notifyDataSetChanged()
    }


    fun clearItems() {
        userList.clear()
    }

    /**
     *Set Listener
     */
    fun setListener(listener: GitHubUserItemAdapterListener) {
        mListener = listener
    }

    /**
     * Adapter Listener
     */
    interface GitHubUserItemAdapterListener {
        fun onUserContent(mUserListModel: Items)
        fun onRetryClick()
    }

    inner class UserListViewHolder(
        itemViewBinding: LayoutGithubUserItemBinding
    ) : BaseViewHolder(itemViewBinding.root),
        GitHubUserItemViewModel.UserItemViewModelListener {
        private val mBinding: LayoutGithubUserItemBinding = itemViewBinding
        private lateinit var mGitHubUserItemViewModel: GitHubUserItemViewModel

        override fun onBind(position: Int) {
            val mGitHubUserListModel: Items = userList[position]
            mGitHubUserItemViewModel =
                GitHubUserItemViewModel(
                    position,
                    mGitHubUserListModel,
                    this
                )
            mBinding.userItemViewModel = mGitHubUserItemViewModel
            mBinding.executePendingBindings()
        }


        override fun onContentDetails(mUserListModel: Items) {
            mListener.onUserContent(mUserListModel)
        }
    }
}