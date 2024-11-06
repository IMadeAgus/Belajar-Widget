package com.example.mywidgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class RandomNumberWidget : AppWidgetProvider() {
    //merupak metode yang pertama kali dipanggil ketika widget dibuat
    //dijalankan ketika update period milis mencapai waktunya
    //Perulangan di sini dimaksudkan untuk menentukan widget mana yang akan di-update karena jumlah widget dalam sebuah aplikasi bisa lebih dari 1.
    // Jadi, kita perlu mendefinisikan widget mana yang perlu diperbarui oleh sistem.
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        //perulangan array menggunakan appWIdgetIds
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
            //metode untuk update
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {
        const val WIDGET_CLICK = "android.appwidget.action.APPWIDGET_UPDATE"
        const val WIDGET_ID_EXTRA = "widget_id_extra"
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {



    val lastUpdate = "Random: " + NumberGenerator.generate(100)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.random_number_widget)
    //remote views merupakan komponen untuk mnegambil data layout dari widget yang dipakai
    // Seperti pada kode setTextViewText() di mana kita mengubah string text pada komponen R.id.appwidget_text.
    views.setTextViewText(R.id.appwidget_text, lastUpdate)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
    //Terjadi update widget yang sebenarnya. Parameter 1 adalah id widget yang ingin kita update,
    // sedangkan parameter 2 adalah RemoteViews yang berisikan views yang telah kita modifikasi.
}