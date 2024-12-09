package com.sonozaki.ticketsapp.utils

import android.content.Context
import androidx.core.content.ContextCompat

fun colorResToColorInt(rId: Int, context: Context): Int = ContextCompat.getColor(context, rId)