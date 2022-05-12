package com.rmoralf.xkcd.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.rmoralf.xkcd.data.network.entities.ApiComic
import com.rmoralf.xkcd.data.network.remote.ComicsService
import com.rmoralf.xkcd.domain.model.Response
import com.rmoralf.xkcd.domain.model.toDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyInt
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalCoroutinesApi::class)
class ComicsRepositoryImplTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: ComicsService
    private lateinit var client: OkHttpClient

    private lateinit var comicsRepositoryImpl: ComicsRepositoryImpl

    @Before
    fun setup() {
        //Mock server
        mockWebServer = MockWebServer()
        mockWebServer.start()
        client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()


        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ComicsService::class.java)

        //Create repository with mocked server
        comicsRepositoryImpl = ComicsRepositoryImpl(service)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `service_getLatestComic and check response success returned`() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(apiComic))
        mockWebServer.enqueue(response)

        runBlocking {
            val actual = service.getLatestComic()
            val expected = apiComic

            assertThat(actual).isEqualTo(expected)
        }
    }

    @Test
    fun `comicsRepositoryImpl_getLatestComic returns a domain Comic`() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(apiComic))
        mockWebServer.enqueue(response)
        runBlocking {
            comicsRepositoryImpl.getLatestComic().test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(Response.Loading)
                val emission2 = awaitItem()
                assertThat(emission2).isEqualTo(Response.Success(apiComic.toDomain()))
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `comicsRepositoryImpl_getLatestComic catches Timeout exception if there is an error`() {
        val errorMsg = "timeout"

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(apiComic))
            .setBodyDelay(2, TimeUnit.SECONDS)
        mockWebServer.enqueue(response)

        runBlocking {
            comicsRepositoryImpl.getLatestComic().test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(Response.Loading)
                val emission2 = awaitItem()
                assertThat(emission2).isInstanceOf(Response.Error::class.java)
                assertThat(emission2).isEqualTo(Response.Error(errorMsg))
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `comicsRepositoryImpl_getLatestComic catches HTTP 400 BAD REQUEST exception if there is an error`() {
        val errorMsg = "HTTP 400 Client Error"

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
        mockWebServer.enqueue(response)

        runBlocking {
            comicsRepositoryImpl.getLatestComic().test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(Response.Loading)
                val emission2 = awaitItem()
                assertThat(emission2).isInstanceOf(Response.Error::class.java)
                assertThat(emission2).isEqualTo(Response.Error(errorMsg))
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `comicsRepositoryImpl_getLatestComic catches HTTP 500 INTERNAL ERROR exception if there is an error`() {

        val errorMsg = "HTTP 500 Server Error"

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(response)

        runBlocking {
            comicsRepositoryImpl.getLatestComic().test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(Response.Loading)
                val emission2 = awaitItem()
                assertThat(emission2).isInstanceOf(Response.Error::class.java)
                assertThat(emission2).isEqualTo(Response.Error(errorMsg))
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `service_getComic and check response success returned`() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(apiComic))
        mockWebServer.enqueue(response)

        runBlocking {
            val actual = service.getComic(anyInt())
            val expected = apiComic

            assertThat(actual).isEqualTo(expected)
        }
    }

    @Test
    fun `comicsRepositoryImpl_getComic returns a domain Comic`() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(apiComic))
        mockWebServer.enqueue(response)

        runBlocking {
            comicsRepositoryImpl.getComic(anyInt()).test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(Response.Loading)
                val emission2 = awaitItem()
                assertThat(emission2).isEqualTo(Response.Success(apiComic.toDomain()))
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `comicsRepositoryImpl_getComic catches Timeout exception if there is an error`() {
        val errorMsg = "timeout"

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(apiComic))
            .setBodyDelay(2, TimeUnit.SECONDS)
        mockWebServer.enqueue(response)

        runBlocking {
            comicsRepositoryImpl.getComic(anyInt()).test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(Response.Loading)
                val emission2 = awaitItem()
                assertThat(emission2).isInstanceOf(Response.Error::class.java)
                assertThat(emission2).isEqualTo(Response.Error(errorMsg))
                cancelAndIgnoreRemainingEvents()
            }
        }
    }


    @Test
    fun `comicsRepositoryImpl_getComic catches HTTP 400 BAD REQUEST exception if there is an error`() {
        val errorMsg = "HTTP 400 Client Error"

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
        mockWebServer.enqueue(response)

        runBlocking {
            comicsRepositoryImpl.getComic(anyInt()).test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(Response.Loading)
                val emission2 = awaitItem()
                assertThat(emission2).isInstanceOf(Response.Error::class.java)
                assertThat(emission2).isEqualTo(Response.Error(errorMsg))
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `comicsRepositoryImpl_getComic catches HTTP 500 INTERNAL ERROR exception if there is an error`() {
        val errorMsg = "HTTP 500 Server Error"

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(response)

        runBlocking {
            comicsRepositoryImpl.getComic(anyInt()).test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(Response.Loading)
                val emission2 = awaitItem()
                assertThat(emission2).isInstanceOf(Response.Error::class.java)
                assertThat(emission2).isEqualTo(Response.Error(errorMsg))
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    private val apiComic = ApiComic(
        alt = "AAAAAAA",
        day = "01",
        img = "img",
        link = "link",
        month = "02",
        news = "news",
        num = 123,
        safe_title = "safe_title",
        title = "title",
        transcript = "transcript",
        year = "2022"
    )
}