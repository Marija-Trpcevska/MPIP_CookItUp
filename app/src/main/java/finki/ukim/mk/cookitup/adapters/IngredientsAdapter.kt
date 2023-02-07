package finki.ukim.mk.cookitup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import finki.ukim.mk.cookitup.R

class IngredientsAdapter(private val data: ArrayList<String> = ArrayList() ):RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    class IngredientViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private var ingredientText: TextView
        private var ingredientBtnDelete: ImageButton
        init {
            ingredientText = itemView.findViewById(R.id.ingredient_text)
            ingredientBtnDelete = itemView.findViewById(R.id.ingredient_btn_delete)
        }

        fun bind(ingredient: String) {
            ingredientText.text = ingredient
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsAdapter.IngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item_view,parent,false)
        val holder = IngredientsAdapter.IngredientViewHolder(view)
        holder.itemView.findViewById<ImageButton>(R.id.ingredient_btn_delete).setOnClickListener {
            removeItemFromAdapter(holder.absoluteAdapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: IngredientsAdapter.IngredientViewHolder, position: Int) {
        val ingredient : String = data[position]
        holder.bind(ingredient)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addItemToAdapter(ingredient: String) {
        this.data.add(ingredient)
        notifyItemInserted(data.indexOf(ingredient))
    }
     fun removeItemFromAdapter(position: Int){
         this.data.removeAt(position)
         notifyItemRemoved(position)
     }

    fun getData(): ArrayList<String>{
        return this.data
    }
}