package finki.ukim.mk.cookitup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import finki.ukim.mk.cookitup.R
import finki.ukim.mk.cookitup.domain.search.model.RecipeApi

class RecipeApiAdapter(private val data: ArrayList<RecipeApi> = ArrayList(), var onClickListener : ((RecipeApi) -> Unit)) :
    RecyclerView.Adapter<RecipeApiAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private var recipeTitle: TextView
        private var recipeImage: ImageView

        private var currentRecipe:RecipeApi?

        init {
            recipeTitle = itemView.findViewById(R.id.recipe_title)
            recipeImage = itemView.findViewById(R.id.recipe_image)
            currentRecipe = null
        }

        fun bind(recipe:RecipeApi) {
            this.currentRecipe = recipe
            recipeTitle.text = recipe.label
            Glide.with(recipeImage)
                .load(recipe.image)
                .centerCrop().placeholder(R.drawable.ic_baseline_image_not_supported_24).into(recipeImage)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_api_item_view,parent,false)
        val holder = MovieViewHolder(view)
        holder.itemView.setOnClickListener {
            onClickListener.invoke(data[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val recipe : RecipeApi = data[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setItemsToAdapter(recipes: List<RecipeApi>) {
        this.data.clear()
        if (recipes!=null) {
            this.data.addAll(recipes)
        }
        notifyDataSetChanged()
    }
}