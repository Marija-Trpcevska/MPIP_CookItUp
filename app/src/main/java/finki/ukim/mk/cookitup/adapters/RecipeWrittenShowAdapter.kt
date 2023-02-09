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
import finki.ukim.mk.cookitup.domain.add.model.RecipeWritten

class RecipeWrittenShowAdapter(private val data: ArrayList<RecipeWritten> = ArrayList(), var onClickListener : ((RecipeWritten) -> Unit)) :
    RecyclerView.Adapter<RecipeWrittenShowAdapter.RecipeWrittenShowViewHolder>() {

    class RecipeWrittenShowViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private var layout : ConstraintLayout
        private var recipeTitle: TextView
        private var recipeImage: ImageView
        private var ingredients : RecyclerView
        private var chips : ChipGroup
        private var instructions: TextView
        private var fab: FloatingActionButton
        private var currentRecipe: RecipeWritten?

        init {
            layout = itemView.findViewById(R.id.layout_written_added)
            recipeTitle = itemView.findViewById(R.id.recipe_title_written_added)
            recipeImage = itemView.findViewById(R.id.recipe_image_written_added)
            ingredients = itemView.findViewById(R.id.recipe_ingredient_lines_written_added)
            chips = itemView.findViewById(R.id.recipe_chips_info_written_added)
            instructions = itemView.findViewById(R.id.recipe_instructions_written_added)
            fab = itemView.findViewById(R.id.api_fab_written_added)
            currentRecipe = null
        }

        fun bind(recipe: RecipeWritten) {
            layout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
            recipeTitle.text = recipe.label
//            recipeImage.load(recipe.image){
//                placeholder(R.drawable.ic_baseline_image_not_supported_24)
//            }
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
            instructions.text = recipe.instructions
            this.currentRecipe = recipe
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeWrittenShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_written_item_added,parent,false)
        val holder = RecipeWrittenShowViewHolder(view)
        val layout = holder.itemView.findViewById<ConstraintLayout>(R.id.layout_written_added)
        val divider=  holder.itemView.findViewById<View>(R.id.recipe_divider_written_added)
        val ingredients =  holder.itemView.findViewById<RecyclerView>(R.id.recipe_ingredient_lines_written_added)
        val chips = holder.itemView.findViewById<HorizontalScrollView>(R.id.scroll_chip_written_added)
        val instructions = holder.itemView.findViewById<LinearLayout>(R.id.recipe_instructions_box_written_added)
        val fab = holder.itemView.findViewById<FloatingActionButton>(R.id.api_fab_written_added)

        layout.layoutTransition.setAnimateParentHierarchy(false)
        holder.itemView.findViewById<CardView>(R.id.card_written_added).setOnClickListener{

            TransitionManager.beginDelayedTransition(layout, AutoTransition())

            val visible = if(divider.visibility == View.GONE) View.VISIBLE else View.GONE
            divider.visibility = visible
            ingredients.visibility = visible
            chips.visibility = visible
            instructions.visibility = visible
            fab.visibility = visible

        }
        fab.setOnClickListener{
            TransitionManager.beginDelayedTransition(layout, AutoTransition())

            val visible = if(divider.visibility == View.GONE) View.VISIBLE else View.GONE
            divider.visibility = visible
            ingredients.visibility = visible
            chips.visibility = visible
            instructions.visibility = visible
            fab.visibility = visible

            onClickListener.invoke(data[holder.absoluteAdapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecipeWrittenShowViewHolder, position: Int) {
        val recipe : RecipeWritten = data[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setItemsToAdapter(recipes: List<RecipeWritten>) {
        this.data.clear()
        this.data.addAll(recipes)
        notifyDataSetChanged()
    }
}