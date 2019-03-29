package com.softsandr.placesearch.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.softsandr.placesearch.api.ApiClient
import com.softsandr.placesearch.api.SearchApiResponse
import com.softsandr.placesearch.db.dao.SavedVenuesDao
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

/**
 * Created by Oleksandr Drachuk on 29.03.19.
 */
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var apiClient: ApiClient
    @Mock
    lateinit var savedVenuesDao: SavedVenuesDao

    private lateinit var gson: Gson

    @Before
    @Throws(Exception::class)
    fun before() {
        gson = GsonBuilder().create()

        `when`(apiClient.search("", "", 20)).thenReturn(Single.just(searchResponse()))
    }

    @Test
    @Throws(Exception::class)
    fun testSearchForPlacesHasParsedApiResponse() {
        val viewModel = SearchViewModel(apiClient, savedVenuesDao)
        viewModel.searchForPlaces("", "", 20)

        verify(apiClient, times(1)).search(anyString(), anyString(), anyInt(), anyString(), anyString(), anyString())
        Assert.assertNotNull(viewModel.getVenues().value)
        Assert.assertTrue("Some data has been lost during parsing response", viewModel.getVenues().value?.size == 3)
    }

    @Throws(Exception::class)
    private fun searchResponse(): SearchApiResponse {
        val searchResponseJson: InputStream = javaClass.classLoader.getResourceAsStream("api_search_response.json")
        val searchResponseAsString: String = BufferedReader(InputStreamReader(searchResponseJson)).lines().collect(Collectors.joining("\n"))
        return gson.fromJson(searchResponseAsString, SearchApiResponse::class.java)
    }

    companion object {

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            val immediate = object : Scheduler() {
                override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                    return super.scheduleDirect(run, 0, unit)
                }

                override fun createWorker(): Worker {
                    return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
                }
            }

            RxJavaPlugins.setInitIoSchedulerHandler { immediate }
            RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
            RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
            RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
        }
    }
}