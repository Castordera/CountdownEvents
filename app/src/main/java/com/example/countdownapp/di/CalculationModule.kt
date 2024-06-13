package com.example.countdownapp.di

import com.ulises.date_utils.calculation.MonthCalculation
import com.ulises.date_utils.calculation.SeasonCalculation
import com.ulises.date_utils.calculation.TimeCalculation
import com.ulises.date_utils.calculation.TodayCalculation
import com.ulises.date_utils.calculation.WeekCalculation
import com.ulises.date_utils.calculation.YearCalculation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CalculationModule {

    @Binds
    abstract fun bindDayCalculation(imp: TodayCalculation): TimeCalculation

    @Binds
    abstract fun bindMonthCalculation(imp: MonthCalculation): TimeCalculation

    @Binds
    abstract fun bindYearCalculation(imp: YearCalculation): TimeCalculation

    @Binds
    abstract fun bindWeekCalculation(imp: WeekCalculation): TimeCalculation

    @Binds
    abstract fun bindSeasonCalculation(imp: SeasonCalculation): TimeCalculation
}