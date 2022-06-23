package com.fabricaswsenactpi.com.construyendopacifico.data.models.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.department.DepartmentDao
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.measure.MeasureDao
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.municipalities.MunicipalitiesDao
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.populatedcenter.PopulatedCenterDao
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.population.PopulationDao
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.sample.SampleDao
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.user.UserDao
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.*

@Database(
    entities = arrayOf(
        AnalysisEntity::class,
        DepartmentEntity::class,
        EthnicGroupEntity::class,
        FeatureEntity::class,
        MeasureEntity::class,
        MunicipalitiesEntity::class,
        ParameterEntity::class,
        PopulatedCentersEntity::class,
        PopulationsEntity::class,
        PopulationTypesEntity::class,
        ProfilesEntity::class,
        ResultsEntity::class,
        SamplesEntity::class,
        SampleTypeEntity::class,
        UsersEntity::class,
        WaterTypesEntity::class
    ), version = 6
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun UserDao(): UserDao
    abstract fun PopulationDao(): PopulationDao
    abstract fun MeasureDao(): MeasureDao
    abstract fun SampleDao(): SampleDao
    abstract fun DepartmentDao():DepartmentDao
    abstract fun MunicipalitiesDao() : MunicipalitiesDao
    abstract fun PopulatedCenterDao() : PopulatedCenterDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getAnalysisDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "analysis_table"
            ).build()
            return INSTANCE!!
        }

        fun getDepartmentsDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "departments_table"
            ).build()
            return INSTANCE!!
        }

        fun getEthnicGroupsDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "ethnic_groups_table"
            ).build()
            return INSTANCE!!
        }

        fun getFeaturesDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "features_table"
            ).build()
            return INSTANCE!!
        }

        fun getMeasureDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "measures_table"
            ).build()
            return INSTANCE!!
        }

        fun getMunicipalitiesDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "municipalities_table"
            ).build()
            return INSTANCE!!
        }

        fun getParametersDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "parameters_table"
            ).build()
            return INSTANCE!!
        }

        fun getPopulatedCentersDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "populated_centers_table"
            ).build()
            return INSTANCE!!
        }

        fun getPopulationsDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "populations_table"
            ).build()
            return INSTANCE!!
        }

        fun getPopulationTypesDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "population_types_table"
            ).build()
            return INSTANCE!!
        }

        fun getProfilesTypesDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "profiles_table"
            ).build()
            return INSTANCE!!
        }

        fun getResultsDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "results_table"
            ).build()
            return INSTANCE!!
        }

        fun getSamplesDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "samples_table"
            ).build()
            return INSTANCE!!
        }

        fun getSampleTypesDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "sample_types_table"
            ).build()
            return INSTANCE!!
        }

        fun getUsersDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "users_table"
            ).build()
            return INSTANCE!!
        }

        fun getWaterTypesDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "water_types_table"
            ).build()
            return INSTANCE!!
        }

    }
}