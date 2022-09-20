package hr.ferit.sandroblavicki.sandroapp.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import hr.ferit.sandroblavicki.sandroapp.databinding.HomePostBinding

class HomePageRecyclerViewAdapter (
    private val context: Context,
    private var items: List<PostData>,
    private val viewModel: HomeViewModel,
        ) : RecyclerView.Adapter<HomePageRecyclerViewAdapter.PostViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding: HomePostBinding = HomePostBinding.inflate(LayoutInflater.from(context),parent,false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
       holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setPosts(newPosts : List<PostData>) {
        items = newPosts
        notifyDataSetChanged()
    }


    inner class PostViewHolder(private val binding: HomePostBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(postData: PostData) {
            binding.apply {
                textviewHomePostUsername.text = postData.username
                textviewHomePostDescription.text = postData.description
                Glide.with(context).load(postData.imageUrl).into(imageviewHomePostImage)

                val postJsonString: String = Gson().toJson(postData)

                imageviewHomePostImage.setOnClickListener {
                    viewModel.navigateTo(HomeFragmentDirections.navigateToPostFragment(postJsonString))
                }

                textviewHomePostUsername.setOnClickListener {
                    viewModel.navigateTo(HomeFragmentDirections.navigateToAccountFragment(postData.userId))
                }
            }
        }
    }

}