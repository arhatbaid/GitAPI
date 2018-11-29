package ab.gitdemo.ui.repolist

import ab.gitdemo.R
import ab.gitdemo.ui.base.ActBase
import ab.gitdemo.utils.Utils
import ab.gitdemo.webapi.model.RepoData
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_repo.view.*


class AdapterRepoList(context: ActBase, arrData: ArrayList<RepoData>, onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<AdapterRepoList.ViewHolder>() {

    private var arrayData = ArrayList<RepoData>()
    private var mContext: ActBase? = null
    private var layoutInflater: LayoutInflater? = null
    private var onItemClickListener: OnItemClickListener? = null
    private var likeList: Map<String, Any>? = null
    private var lastPosition = -1

    init {
        this.arrayData = arrData
        this.mContext = context
        this.onItemClickListener = onItemClickListener
        this.layoutInflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: AdapterRepoList.ViewHolder, position: Int) {

        val repoDetail = arrayData[position]

        holder.lblRepoTitle?.text = repoDetail.name
        holder.lblRepoDesc?.text = repoDetail.description

        holder.imgUser?.background = mContext!!.resources.getDrawable(Utils.getColorDrawable(position % 5));

        Picasso.get().load(repoDetail.owner.avatarUrl)
                .placeholder(R.drawable.circular_view_purple)
                .resize(200, 200)
                .onlyScaleDown()
                .into(holder.imgUser)

        holder.itemView.tag = position

        setAnimation(holder.itemView, position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater?.inflate(R.layout.cell_repo, parent, false)!!)
    }

    override fun getItemCount(): Int = arrayData.size

    fun setLikeMapList(document: Map<String, Any>?) {
        likeList = document
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var lblRepoTitle: TextView? = null
        var lblRepoDesc: TextView? = null
        var imgUser: ImageView? = null

        init {
            lblRepoTitle = itemView.lblRepoTitle
            lblRepoDesc = itemView.lblRepoDesc
            imgUser = itemView.imgUser
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = v?.tag
            onItemClickListener?.onItemClick(position as Int)
        }
    }


    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
