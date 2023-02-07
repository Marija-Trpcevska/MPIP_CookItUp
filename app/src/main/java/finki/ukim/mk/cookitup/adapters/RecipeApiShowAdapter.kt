package finki.ukim.mk.cookitup.adapters

import android.animation.LayoutTransition
import android.transition.AutoTransition
import android.transition.TransitionManager
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

class RecipeApiShowAdapter(private val data: ArrayList<RecipeApi> = ArrayList(), var onClickListener : ((RecipeApi) -> Unit)) :
    RecyclerView.Adapter<RecipeApiShowAdapter.RecipeShowViewHolder>() {

    class RecipeShowViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private var layout : ConstraintLayout
        private var recipeTitle: TextView
        private var recipeImage: ImageView
        private var ingredients : RecyclerView
        private var chips : ChipGroup
        private var source: TextView
        private var url: TextView
        private var fab: FloatingActionButton
        private var currentRecipe: RecipeApi?

        init {
            layout = itemView.findViewById(R.id.layout_api_added)
            recipeTitle = itemView.findViewById(R.id.recipe_title_added)
            recipeImage = itemView.findViewById(R.id.recipe_image_added)
            ingredients = itemView.findViewById(R.id.recipe_ingredient_lines_added)
            chips = itemView.findViewById(R.id.recipe_chips_info_added)
            source = itemView.findViewById(R.id.recipe_source_added)
            url = itemView.findViewById(R.id.recipe_source_url_added)
            fab = itemView.findViewById(R.id.api_fab_added)
            currentRecipe = null
        }

        fun bind(recipe: RecipeApi) {
            layout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
            recipeTitle.text = recipe.label
            //ZAMENI
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
                    it.visibility= View.VISIBLE
                }
            }
            source.text = recipe.source
            url.text = recipe.url
            this.currentRecipe = recipe
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_api_item_added,parent,false)
        val holder = RecipeShowViewHolder(view)
        val layout = holder.itemView.findViewById<ConstraintLayout>(R.id.layout_api_added)
        val divider=  holder.itemView.findViewById<View>(R.id.recipe_divider_added)
        val ingredients =  holder.itemView.findViewById<RecyclerView>(R.id.recipe_ingredient_lines_added)
        val chips = holder.itemView.findViewById<HorizontalScrollView>(R.id.scroll_chip_added)
        val source = holder.itemView.findViewById<LinearLayout>(R.id.recipe_source_layout_added)
        val url = holder.itemView.findViewById<LinearLayout>(R.id.recipe_source_url_layout_added)

        val fab = holder.itemView.findViewById<FloatingActionButton>(R.id.api_fab_added)
        holder.itemView.findViewById<CardView>(R.id.card_api_added).setOnClickListener{

            TransitionManager.beginDelayedTransition(layout, AutoTransition())

            val visibile = if(divider.visibility == View.GONE) View.VISIBLE else View.GONE
            divider.visibility = visibile
            ingredients.visibility = visibile
            chips.visibility = visibile
            source.visibility = visibile
            url.visibility = visibile
            fab.visibility = visibile

        }
        fab.setOnClickListener{
            onClickListener.invoke(data[holder.absoluteAdapterPosition])

            TransitionManager.beginDelayedTransition(layout, AutoTransition())

            val visibile = if(divider.visibility == View.GONE) View.VISIBLE else View.GONE
            divider.visibility = visibile
            ingredients.visibility = visibile
            chips.visibility = visibile
            source.visibility = visibile
            url.visibility = visibile
            fab.visibility = visibile
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecipeShowViewHolder, position: Int) {
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