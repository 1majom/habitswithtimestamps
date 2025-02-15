
package hu.bme.sch.monkie.habits.testdi

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import hu.bme.sch.monkie.habits.data.datasource.HabitRepository
import hu.bme.sch.monkie.habits.data.di.DataModule
import hu.bme.sch.monkie.habits.data.di.FakeHabitRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
interface FakeDataModule {

    @Binds
    abstract fun bindRepository(
        fakeRepository: FakeHabitRepository
    ): HabitRepository
}
