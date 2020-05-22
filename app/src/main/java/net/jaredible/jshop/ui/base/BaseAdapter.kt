package net.jaredible.jshop.ui.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T: BaseViewHolder> : RecyclerView.Adapter<T>() {}