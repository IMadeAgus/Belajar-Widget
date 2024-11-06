package com.example.mywidgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
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

    //karena kita menggunakan pending intent (melakukan broadcast, perlu membuat receiver.
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        //mengecek apakah action berasal dari wicget event click yang dimaksud
        //Pada konteks ini kita menggunakan action berisi string yang telah kita definisikan sebelumnya pada variabel WIDGET_CLICK.
        if (intent?.action == WIDGET_CLICK) {

            //Pada dasarnya, apa yang terjadi pada metode onReceive() mirip dengan metode updateAppWidget.
            // Yang membedakan hanyalah pada metode onUpdate(), ia dijalankan secara otomatis.
            // Sedangkan metode onReceive() dijalankan ketika aksi klik pada komponen telah di-broadcast.
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val views = RemoteViews(context?.packageName, R.layout.random_number_widget)
            val lastUpdate = "Random: " + NumberGenerator.generate(100)
            val appWidgetId = intent.getIntExtra(WIDGET_ID_EXTRA, 0)
            views.setTextViewText(R.id.appwidget_text, lastUpdate)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
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
    //Pending Intent
    val intent = Intent(context, RandomNumberWidget::class.java)
    intent.action = RandomNumberWidget.WIDGET_CLICK

    //pada object intent, tambahkan appWidgetId untuk mengetahui widget mana yang ditekan dengan appWidgetId sebagai identifier
    intent.putExtra(RandomNumberWidget.WIDGET_ID_EXTRA, appWidgetId)

    //karena kita menggunakan pending intent (melakukan broadcast, perlu melakukan metode override receiver
    val pendingIntent = PendingIntent.getBroadcast(
        context, appWidgetId, intent,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        } else {
            0
        }
    )



    val lastUpdate = "Random: " + NumberGenerator.generate(100)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.random_number_widget)
    //remote views merupakan komponen untuk mnegambil data layout dari widget yang dipakai
    // Seperti pada kode setTextViewText() di mana kita mengubah string text pada komponen R.id.appwidget_text.
    views.setTextViewText(R.id.appwidget_text, lastUpdate)


    //ketika views dengan btn_click ditekan, akan menjalankan pending intent yang di set
    views.setOnClickPendingIntent(R.id.btn_click, pendingIntent)



    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
    //Terjadi update widget yang sebenarnya. Parameter 1 adalah id widget yang ingin kita update,
    // sedangkan parameter 2 adalah RemoteViews yang berisikan views yang telah kita modifikasi.
}