package com.oguzhanturkmen.kotlinulkelerapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.oguzhanturkmen.kotlinulkelerapp.model.Country

@Dao
interface CountryDao {

    //Data Access Object

    @Insert
    suspend fun insertAll(vararg countries: Country) : List<Long>

    //Insert -> INSERT INTO
    // suspend -> coroutine, pause & resume korotinler içinde çağırılır ve durdulup devam etmeye olanak verir
    // vararg -> multiple country objects insert yaparken normalde tek tek insert eder item sayının tam belli olmadığı
    //zamanlarda vararg kullanırız ve bana listLong içinde primary idleri döndürür
    // List<Long> -> primary keys

    //feed fragment için
    @Query("SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>
    //country fragment için
    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()


}