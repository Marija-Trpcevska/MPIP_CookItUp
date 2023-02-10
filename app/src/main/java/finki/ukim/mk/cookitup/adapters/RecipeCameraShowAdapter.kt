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
import coil.load
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import finki.ukim.mk.cookitup.R
import finki.ukim.mk.cookitup.domain.add.model.RecipeCamera

class RecipeCameraShowAdapter(private val data: ArrayList<RecipeCamera> = ArrayList(), var onClickListener : ((RecipeCamera) -> Unit)) :
    RecyclerView.Adapter<RecipeCameraShowAdapter.RecipeCameraShowViewHolder>() {

    class RecipeCameraShowViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private var layout : ConstraintLayout
        private var recipeTitle: TextView
        private var recipeImage: ImageView
        private var chips : ChipGroup
        private var notes: TextView
        private var fab: FloatingActionButton
        private var currentRecipe: RecipeCamera?

        init {
            layout = itemView.findViewById(R.id.layout_camera_added)
            recipeTitle = itemView.findViewById(R.id.recipe_title_camera_added)
            recipeImage = itemView.findViewById(R.id.recipe_image_camera_added)
            chips = itemView.findViewById(R.id.recipe_chips_info_camera_added)
            notes = itemView.findViewById(R.id.recipe_notes_camera_added)
            fab = itemView.findViewById(R.id.api_fab_camera_added)
            currentRecipe = null
        }

        fun bind(recipe: RecipeCamera) {
            recipeTitle.text = recipe.label
            recipeImage.load(recipe.image){
                placeholder(R.drawable.ic_baseline_image_not_supported_24)
            }

            chips.children.forEach {
                it.isEnabled=false
                if(recipe.mealType.contains(itemView.findViewById<Chip>(it.id).text.toString()))
                {
                    it.visibility= View.VISIBLE
                }
            }
            notes.text = recipe.notes
            this.currentRecipe = recipe
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeCameraShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_camera_item_added,parent,false)
        val holder = RecipeCameraShowViewHolder(view)
        holder.itemView.findViewById<ConstraintLayout>(R.id.layout_camera_added)
        val divider=  holder.itemView.findViewById<View>(R.id.recipe_divider_camera_added)
        val chips = holder.itemView.findViewById<HorizontalScrollView>(R.id.scroll_chip_camera_added)
        val notes = holder.itemView.findViewById<LinearLayout>(R.id.recipe_notes_camera_box_added)
        val fab = holder.itemView.findViewById<FloatingActionButton>(R.id.api_fab_camera_added)

        holder.itemView.findViewById<CardView>(R.id.card_camera_added).setOnClickListener{

            val visible = if(divider.visibility == View.GONE) View.VISIBLE else View.GONE
            divider.visibility = visible
            chips.visibility = visible
            notes.visibility = visible
            fab.visibility = visible
        }
        fab.setOnClickListener{

            val visible = if(divider.visibility == View.GONE) View.VISIBLE else View.GONE
            divider.visibility = visible
            chips.visibility = visible
            notes.visibility = visible
            fab.visibility = visible

            onClickListener.invoke(data[holder.absoluteAdapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecipeCameraShowViewHolder, position: Int) {
        val recipe : RecipeCamera = data[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setItemsToAdapter(recipes: List<RecipeCamera>) {
        this.data.clear()
        this.data.addAll(recipes)
        notifyDataSetChanged()
    }
}