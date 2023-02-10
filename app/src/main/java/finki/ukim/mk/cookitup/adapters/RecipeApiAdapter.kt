package finki.ukim.mk.cookitup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import finki.ukim.mk.cookitup.R
import finki.ukim.mk.cookitup.domain.search.model.RecipeApi

class RecipeApiAdapter(private val data: ArrayList<RecipeApi> = ArrayList(), var onClickListener : ((RecipeApi) -> Unit)) :
    RecyclerView.Adapter<RecipeApiAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        private var layout : ConstraintLayout
        private var recipeTitle: TextView
        private var recipeImage: ImageView
        private var ingredients : RecyclerView
        private var chips : ChipGroup
        private var source: TextView
        private var url: TextView
        private var fab: FloatingActionButton
        private var currentRecipe:RecipeApi?

        init {
            layout = itemView.findViewById(R.id.layout_api)
            recipeTitle = itemView.findViewById(R.id.recipe_title)
            recipeImage = itemView.findViewById(R.id.recipe_image)
            ingredients = itemView.findViewById(R.id.recipe_ingredient_lines)
            chips = itemView.findViewById(R.id.recipe_chips_info)
            source = itemView.findViewById(R.id.recipe_source)
            url = itemView.findViewById(R.id.recipe_source_url)
            fab = itemView.findViewById(R.id.api_fab)
            currentRecipe = null
        }

        fun bind(recipe:RecipeApi) {
            recipeTitle.text = recipe.label
            Glide.with(recipeImage)
                .load(recipe.image)
                .centerCrop().placeholder(R.drawable.ic_baseline_image_not_supported_24).into(recipeImage)
            val adapter = IngredientsApiAdapter()
            ingredients.adapter = adapter
            adapter.addItemsToAdapter(ArrayList(recipe.ingredientLines))
            chips.children.forEach {
                it.isEnabled=false
                if(recipe.mealType.contains(itemView.findViewById<Chip>(it.id).text.toString()))
                {
                    it.visibility=View.VISIBLE
                }
            }
            source.text = recipe.source
            url.text = recipe.url
            this.currentRecipe = recipe
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_api_item_view,parent,false)
        val holder = RecipeViewHolder(view)
        holder.itemView.findViewById<ConstraintLayout>(R.id.layout_api)
        val divider=  holder.itemView.findViewById<View>(R.id.recipe_divider)
        val ingredients =  holder.itemView.findViewById<RecyclerView>(R.id.recipe_ingredient_lines)
        val chips = holder.itemView.findViewById<HorizontalScrollView>(R.id.scroll_chip)
        val source = holder.itemView.findViewById<LinearLayout>(R.id.recipe_source_layout)
        val url = holder.itemView.findViewById<LinearLayout>(R.id.recipe_source_url_layout)
        val fab = holder.itemView.findViewById<FloatingActionButton>(R.id.api_fab)

        holder.itemView.findViewById<CardView>(R.id.card_api).setOnClickListener{

            val visible = if(divider.visibility == View.GONE) View.VISIBLE else View.GONE
            divider.visibility = visible
            ingredients.visibility = visible
            chips.visibility = visible
            source.visibility = visible
            url.visibility = visible
            fab.visibility = visible

        }
        fab.setOnClickListener{
            onClickListener.invoke(data[holder.absoluteAdapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe : RecipeApi = data[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setItemsToAdapter(recipes: List<RecipeApi>) {
        this.data.clear()
        this.data.addAll(recipes)
        notifyDataSetChanged()
    }
}