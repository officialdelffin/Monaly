package com.example.monaly.ui.adapter


// Importações :
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.monaly.R
import com.example.monaly.domain.model.AlbumDetailItem


// Adaptador que agora recebe uma função de Callback (onMediaClick) para avisar a tela quando uma foto for tocada :
class AlbumDetailsAdapter(


    private var items: List<AlbumDetailItem>,
    private val onMediaClick: (String) -> Unit


) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {


        const val TYPE_HEADER = 0
        const val TYPE_MEDIA = 1


    }


    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val tvDateHeader: TextView = view.findViewById(R.id.tvDateHeader)


    }


    class MediaViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val ivMediaGridItem: ImageView = view.findViewById(R.id.ivMediaGridItem)


    }


    override fun getItemViewType(position: Int): Int {


        return when (items[position]) {


            is AlbumDetailItem.DateHeader -> TYPE_HEADER
            is AlbumDetailItem.Media -> TYPE_MEDIA


        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        val inflater = LayoutInflater.from(parent.context)


        return if (viewType == TYPE_HEADER) {


            val view = inflater.inflate(R.layout.item_album_date_header, parent, false)
            HeaderViewHolder(view)


        } else {


            val view = inflater.inflate(R.layout.item_album_media, parent, false)
            MediaViewHolder(view)


        }


    }


    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val item = items[position]


        if (holder is HeaderViewHolder && item is AlbumDetailItem.DateHeader) {


            holder.tvDateHeader.text = item.dateText


        }


        else if (holder is MediaViewHolder && item is AlbumDetailItem.Media) {


            val context = holder.itemView.context
            holder.ivMediaGridItem.alpha = 0f


            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 25f
            circularProgressDrawable.setColorSchemeColors(Color.WHITE)
            circularProgressDrawable.start()


            Glide.with(context)

                .load(item.mediaData.mediaUrl)
                .placeholder(circularProgressDrawable)
                .centerCrop()
                .listener(object : RequestListener<Drawable> {


                    override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: Target<Drawable?>, p3: Boolean): Boolean {


                        holder.ivMediaGridItem.alpha = 1f
                        return false


                    }


                    override fun onResourceReady(p0: Drawable, p1: Any, p2: Target<Drawable?>?, p3: DataSource, p4: Boolean): Boolean {


                        holder.ivMediaGridItem.animate().alpha(1f).setDuration(300L).start()
                        return false


                    }


                })


                .into(holder.ivMediaGridItem)


            // Interceptando o toque do usuário e enviando a URL da foto de volta para o Fragmento :
            holder.itemView.setOnClickListener {


                onMediaClick(item.mediaData.mediaUrl)


            }


        }


    }


    fun updateItems(newItems: List<AlbumDetailItem>) {


        items = newItems
        notifyDataSetChanged()


    }


}