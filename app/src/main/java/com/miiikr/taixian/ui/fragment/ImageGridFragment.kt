package com.miiikr.taixian.ui.fragment

import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.AbsListView
import android.widget.AbsListView.LayoutParams
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.hyphenate.util.DateUtils
import com.hyphenate.util.EMLog
import com.hyphenate.util.TextFormater
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.VideoEntity
import com.miiikr.taixian.ui.activity.RecorderVideoActivity
import com.miiikr.taixian.utils.thumbUtils.ImageCache
import com.miiikr.taixian.utils.thumbUtils.ImageResizer
import com.miiikr.taixian.utils.thumbUtils.Utils
import com.miiikr.taixian.widget.RecyclingImageView

import java.util.ArrayList

/**
 * Empty constructor as per the Fragment documentation
 */
class ImageGridFragment : Fragment(), OnItemClickListener {
    private var mImageThumbSize: Int = 0
    private var mImageThumbSpacing: Int = 0
    private var mAdapter: ImageAdapter? = null
    private var mImageResizer: ImageResizer? = null
    lateinit var mList: MutableList<VideoEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mImageThumbSize = resources.getDimensionPixelSize(
                R.dimen.image_thumbnail_size)
        mImageThumbSpacing = resources.getDimensionPixelSize(
                R.dimen.image_thumbnail_spacing)
        mList = ArrayList<VideoEntity>()
        getVideoFile()
        mAdapter = ImageAdapter(activity!!)

        val cacheParams = ImageCache.ImageCacheParams()

        cacheParams.setMemCacheSizePercent(0.25f) // Set memory cache to 25% of
        // app memory

        // The ImageFetcher takes care of loading images into our ImageView
        // children asynchronously
        mImageResizer = ImageResizer(activity, mImageThumbSize)
        mImageResizer!!.setLoadingImage(R.mipmap.em_empty_photo)
        mImageResizer!!.addImageCache(activity!!.supportFragmentManager,
                cacheParams)


    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.em_image_grid_fragment,
                container, false)
        val mGridView = v.findViewById(R.id.gridView) as GridView
        mGridView.adapter = mAdapter
        mGridView.onItemClickListener = this
        mGridView.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(absListView: AbsListView,
                                              scrollState: Int) {
                // Pause fetcher to ensure smoother scrolling when flinging
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Before Honeycomb pause image loading on scroll to help
                    // with performance
                    if (!Utils.hasHoneycomb()) {
                        mImageResizer!!.setPauseWork(true)
                    }
                } else {
                    mImageResizer!!.setPauseWork(false)
                }
            }

            override fun onScroll(absListView: AbsListView, firstVisibleItem: Int,
                                  visibleItemCount: Int, totalItemCount: Int) {
            }
        })

        // This listener is used to get the final width of the GridView and then
        // calculate the
        // number of columns and the width of each column. The width of each
        // column is variable
        // as the GridView has stretchMode=columnWidth. The column width is used
        // to set the height
        // of each view so we get nice square thumbnails.
        mGridView.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    @TargetApi(VERSION_CODES.JELLY_BEAN)
                    override fun onGlobalLayout() {
                        val numColumns = Math.floor((mGridView
                                .width / (mImageThumbSize + mImageThumbSpacing)).toDouble()).toInt()
                        if (numColumns > 0) {
                            val columnWidth = mGridView.width / numColumns - mImageThumbSpacing
                            mAdapter!!.setItemHeight(columnWidth)
//                            if (BuildConfig.DEBUG) {
//                                Log.d(TAG,
//                                        "onCreateView - numColumns set to $numColumns")
//                            }
                            mGridView.viewTreeObserver
                                    .removeOnGlobalLayoutListener(this)
                        }
                    }
                })
        return v

    }

    override fun onResume() {
        super.onResume()
        mImageResizer!!.setExitTasksEarly(false)
        mAdapter!!.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        mImageResizer!!.closeCache()
        mImageResizer!!.clearCache()
    }

    override fun onItemClick(parent: AdapterView<*>, v: View, position: Int, id: Long) {

        mImageResizer!!.setPauseWork(true)

        if (position == 0) {

            val intent = Intent()
            intent.setClass(activity!!, RecorderVideoActivity::class.java!!)
            startActivityForResult(intent, 100)
        } else {
            val vEntty = mList[position - 1]
            val intent = activity!!.intent.putExtra("path", vEntty.filePath).putExtra("dur", vEntty.duration)
            activity!!.setResult(Activity.RESULT_OK, intent)
            activity!!.finish()
        }
    }

    private inner class ImageAdapter(private val mContext: Context) : BaseAdapter() {
        private var mItemHeight = 0
        private var mImageViewLayoutParams: RelativeLayout.LayoutParams? = null

        init {
            mImageViewLayoutParams = RelativeLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }

        override fun getCount(): Int {
            return mList.size + 1
        }

        override fun getItem(position: Int): Any? {
            return if (position == 0) null else mList[position - 1]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }


        override fun getView(position: Int, convertView: View?, container: ViewGroup): View {
            var convertView = convertView
            var holder: ViewHolder? = null
            if (convertView == null) {
                holder = ViewHolder()
                convertView = LayoutInflater.from(mContext).inflate(R.layout.em_choose_griditem, container, false)
                holder.imageView = convertView!!.findViewById<View>(R.id.imageView) as RecyclingImageView
                holder.icon = convertView.findViewById<View>(R.id.video_icon) as ImageView
                holder.tvDur = convertView.findViewById<View>(R.id.chatting_length_iv) as TextView
                holder.tvSize = convertView.findViewById<View>(R.id.chatting_size_iv) as TextView
                holder.imageView!!.setScaleType(ImageView.ScaleType.CENTER_CROP)
                holder.imageView!!.setLayoutParams(mImageViewLayoutParams)
                convertView.tag = holder
            } else {
                holder = convertView.tag as ViewHolder
            }

            // Check the height matches our calculated column width
            if (holder.imageView!!.getLayoutParams().height !== mItemHeight) {
                holder.imageView!!.setLayoutParams(mImageViewLayoutParams)
            }

            // Finally load the image asynchronously into the ImageView, this
            // also takes care of
            // setting a placeholder image while the background thread runs
            val st1 = resources.getString(R.string.Video_footage)
            if (position == 0) {
                holder.icon!!.visibility = View.GONE
                holder.tvDur!!.visibility = View.GONE
                holder.tvSize!!.text = st1
                holder.imageView!!.setImageResource(R.mipmap.em_actionbar_camera_icon)
            } else {
                holder.icon!!.visibility = View.VISIBLE
                val entty = mList[position - 1]
                holder.tvDur!!.visibility = View.VISIBLE

                holder.tvDur!!.text = DateUtils.toTime(entty.duration)
                holder.tvSize!!.text = TextFormater.getDataSize(entty.size.toLong())
                holder.imageView!!.setImageResource(R.mipmap.em_empty_photo)
                mImageResizer!!.loadImage(entty.filePath, holder.imageView)
            }
            return convertView
            // END_INCLUDE(load_gridview_item)
        }

        /**
         * Sets the item height. Useful for when we know the column width so the
         * height can be set to match.
         *
         * @param height
         */
        fun setItemHeight(height: Int) {
            if (height == mItemHeight) {
                return
            }
            mItemHeight = height
            mImageViewLayoutParams = RelativeLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, mItemHeight)
            mImageResizer!!.setImageSize(height)
            notifyDataSetChanged()
        }

        internal inner class ViewHolder {

            var imageView: RecyclingImageView? = null
            var icon: ImageView? = null
            var tvDur: TextView? = null
            var tvSize: TextView? = null
        }
    }

    private fun getVideoFile() {
        val mContentResolver = activity!!.contentResolver
        var cursor = mContentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.DEFAULT_SORT_ORDER)

        if (cursor != null && cursor.moveToFirst()) {
            do {

                // ID:MediaStore.Audio.Media._ID
                val id = cursor.getInt(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media._ID))

                // title：MediaStore.Audio.Media.TITLE
                val title = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE))
                // path：MediaStore.Audio.Media.DATA
                val url = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.DATA))

                // duration：MediaStore.Audio.Media.DURATION
                val duration = cursor
                        .getInt(cursor
                                .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))

                // 大小：MediaStore.Audio.Media.SIZE
                val size = cursor.getLong(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)).toInt()

                val entty = VideoEntity()
                entty.ID = id
                entty.title = title
                entty.filePath = url
                entty.duration = duration
                entty.size = size
                mList.add(entty)
            } while (cursor.moveToNext())

        }
        if (cursor != null) {
            cursor.close()
            cursor = null
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                val uri = data!!.getParcelableExtra<Uri>("uri")
                val projects = arrayOf(MediaStore.Video.Media.DATA, MediaStore.Video.Media.DURATION)
                var cursor = activity!!.contentResolver.query(
                        uri, projects, null, null, null)
                var duration = 0
                var filePath: String? = null

                if (cursor!!.moveToFirst()) {
                    // path：MediaStore.Audio.Media.DATA
                    filePath = cursor.getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                    // duration：MediaStore.Audio.Media.DURATION
                    duration = cursor
                            .getInt(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))
                    EMLog.d(TAG, "duration:$duration")
                }
                if (cursor != null) {
                    cursor.close()
                    cursor = null
                }

                activity!!.setResult(Activity.RESULT_OK, activity!!.intent.putExtra("path", filePath).putExtra("dur", duration))
                activity!!.finish()

            }
        }
    }

    companion object {

        private val TAG = "ImageGridFragment"
    }
}
