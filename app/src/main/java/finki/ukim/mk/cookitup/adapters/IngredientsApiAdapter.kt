package finki.ukim.mk.cookitup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import finki.ukim.mk.cookitup.R

class IngredientsApiAdapter(private val data: ArrayList<String> = ArrayList() ):RecyclerView.Adapter<IngredientsApiAdapter.IngredientApiViewHolder>(){
    class IngredientApiViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private var ingredientText: TextView
        init {
            ingredientText = itemView.findViewById(R.id.ingredient_text2)
        }

        fun bind(ingredient: String) {
            ingredientText.text = ingredient
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientApiViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredient_item_api_view, parent, false)
        return IngredientApiViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientApiViewHolder, position: Int) {
        val ingredient : String = data[position]
        holder.bind(ingredient)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addItemsToAdapter(ingredients: ArrayList<String>) {
        this.data.addAll(ingredients)
        notifyDataSetChanged()
    }
}