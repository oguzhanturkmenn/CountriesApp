package com.oguzhanturkmen.kotlinulkelerapp.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oguzhanturkmen.kotlinulkelerapp.model.Country

@Database(entities = [Country::class],version = 1)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun countryDao() : CountryDao
    //bu sınıfı singleton yapma amacımız farklı threadlerde çalışacak olmamız
    //Singleton  yalnızca 1 object yaratılmasını garanti eden tasarım desenidir. Kullanımına ihtiyaç
    // duyulan durum şudur : Birden çok sınıfın aynı instance'ı kullanması gerekmektedir. Tüm uygulama için yalnızca bir nesne olması gerekmektedir

    //class'ı tekil(singleton) halde kullanabilmeyi sağlamak için companion object'i kullanıyoruz. her yerde ulaşılabilir
    companion object {
        //volatile: diğer görünümlerede (threadlere) görünür hale getirilir
        @Volatile private var instance : CountryDatabase? = null

        private val lock = Any()
        //invoke etmek burdaki instance var mı yok mu onu kontrol edip sonra yoksa oluşturmak var ise instance'ı döndürmek amacı güder
        //synchroized: birden fazla thread aynı anda buraya gelir instance oluşturmaya çalışırsa sychnronize çalışır. tek tek
        operator fun invoke(context : Context) = instance ?: synchronized(lock) {

            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        //veritabanını oluştururuz
        private fun makeDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,CountryDatabase::class.java,"countrydatabase"
        ).build()
    }
}