package hr.ferit.sandroblavicki.sandroapp.account

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import hr.ferit.sandroblavicki.sandroapp.databinding.AccountPostBinding
import hr.ferit.sandroblavicki.sandroapp.models.PostData

class AccountRecyclerViewAdapter(
    private val context: Context,
    private var items: List<PostData>,
    private val viewModel: AccountViewModel,
) : RecyclerView.Adapter<AccountRecyclerViewAdapter.AccountPostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountPostViewHolder {
        val binding: AccountPostBinding =
            AccountPostBinding.inflate(LayoutInflater.from(context), parent, false)
        return AccountPostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountPostViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setPosts(newPosts: List<PostData>) {
        items = newPosts
        notifyDataSetChanged()
    }

    inner class AccountPostViewHolder(
        private val binding: AccountPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(postData: PostData) {
            binding.apply {

                Glide.with(context).load(postData.imageUrl).into(imageviewAccountPost)

                accountPostLinearLayout.setOnClickListener { _ ->
                    viewModel.onPostClicked(postData)
                }
            }
        }
    }
}