package com.example.inventory

import android.app.Application
import com.example.inventory.data.ItemRoomDatabase

class InventoryApplication : Application() {
    val database by lazy { ItemRoomDatabase.getDatabase(this) }
}