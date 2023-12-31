package com.ozgurbaykal.carcaretracker.model

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "car_care_tracker_database"
        ).fallbackToDestructiveMigration()
            .addMigrations(MIGRATION_1_2)
            .build()
    }


    @Singleton
    @Provides
    fun provideVehicleDao(appDatabase: AppDatabase): VehicleDao {
        return appDatabase.vehicleDao()
    }
}
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {

        // Add 'selected_file' column to 'CustomServerFolders' table
        database.execSQL(
            "ALTER TABLE Vehicles ADD COLUMN iconColor TEXT NOT NULL DEFAULT '#FFFFFF'"
        )
    }
}