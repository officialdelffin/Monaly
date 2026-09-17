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


// Adaptador configurado para enviar a posição clicada e a lista completa de imagens :
class AlbumDetailsAdapter(
    private var items: List<AlbumDetailItem>,
    private val onMediaClick: (Int, List<String>) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    // Constantes de identificação de layout :
    companion object {


        const val TYPE_HEADER = 0
        const val TYPE_MEDIA = 1


    }


    // Suporte para o cabeçalho :
    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val tvDateHeader: TextView = view.findViewById(R.id.tvDateHeader)


    }


    // Suporte para a miniatura :
    class MediaViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val ivMediaGridItem: ImageView = view.findViewById(R.id.ivMediaGridItem)


    }


    // Função de identificação de tipo :
    override fun getItemViewType(position: Int): Int {


        return when (items[position]) {


            is AlbumDetailItem.DateHeader -> TYPE_HEADER
            is AlbumDetailItem.Media -> TYPE_MEDIA


        }


    }


    // Inflador de layout condicional :
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


    // Contagem de itens totais :
    override fun getItemCount(): Int = items.size


    // Vinculação de dados reais aos componentes visuais :
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val item = items[position]


        // Injetando data no cabeçalho :
        if (holder is HeaderViewHolder && item is AlbumDetailItem.DateHeader) {


            holder.tvDateHeader.text = item.dateText


        }


        // Baixando imagem na miniatura :
        else if (holder is MediaViewHolder && item is AlbumDetailItem.Media) {


            val context = holder.itemView.context
            holder.ivMediaGridItem.alpha = 0f


            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 25f
            circularProgressDrawable.setColorSchemeColors(Color.WHITE)
            circularProgressDrawable.start()


            // Carregamento via Glide :
            Glide.with(context)
                .load(item.mediaData.mediaUrl)
                .placeholder(circularProgressDrawable)
                .centerCrop()
                .listener(object : RequestListener<Drawable> {


                    override fun onLoadFailed(
                        p0: GlideException?, p1: Any?, p2: Target<Drawable?>, p3: Boolean
                    ): Boolean {


                        holder.ivMediaGridItem.alpha = 1f
                        return false


                    }


                    override fun onResourceReady(
                        p0: Drawable, p1: Any, p2: Target<Drawable?>?, p3: DataSource, p4: Boolean
                    ): Boolean {


                        holder.ivMediaGridItem.animate().alpha(1f).setDuration(300L).start()
                        return false


                    }


                })
                .into(holder.ivMediaGridItem)


            // Tratamento de clique para extrair a lista limpa de URLs :
            holder.itemView.setOnClickListener {


                // Filtrando a lista principal para isolar apenas as mídias :
                val mediaItems = items.filterIsInstance<AlbumDetailItem.Media>()

                // Mapeando os itens isolados para obter uma lista pura de links :
                val urls = mediaItems.map { it.mediaData.mediaUrl }

                // Encontrando o índice exato da imagem clicada na lista pura :
                val currentIndex = urls.indexOf(item.mediaData.mediaUrl)


                // Disparando o Callback enviando a posição inicial e as fotos :
                onMediaClick(currentIndex, urls)


            }


        }


    }


    // Atualizador de lista reativo :
    fun updateItems(newItems: List<AlbumDetailItem>) {


        items = newItems
        notifyDataSetChanged()


    }


}