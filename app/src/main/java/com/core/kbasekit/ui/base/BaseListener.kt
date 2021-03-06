package com.core.kbasekit.ui.base

import android.view.View


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 12/14/2017 at 6:41 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Last edited by : Md. Azizul Islam on 12/14/2017.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

interface BaseListener<T> {
    fun onItemClick(view : View, t : T)
    fun onItemClick(view : View, position : Int)
}