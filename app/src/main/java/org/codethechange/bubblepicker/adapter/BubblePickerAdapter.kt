package org.codethechange.bubblepicker.adapter

import org.codethechange.bubblepicker.model.PickerItem

/**
 * Created by irinagalata on 5/22/17.
 */
interface BubblePickerAdapter {

    val totalCount: Int

    fun getItem(position: Int): PickerItem

}