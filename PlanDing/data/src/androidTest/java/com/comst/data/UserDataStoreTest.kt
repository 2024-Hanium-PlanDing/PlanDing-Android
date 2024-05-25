package com.comst.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDataStoreTest {

    @Test
    fun 토큰_저장_삭제_테스트() = runTest{
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val userDataStore = UserDataStore(context)

        val expectedAccessTokenTest = "accessToken"
        val expectedRefreshTokenTest = "refreshToken"
        userDataStore.setAccessToken(expectedAccessTokenTest)
        userDataStore.setRefreshToken(expectedRefreshTokenTest)

        var actualAccessToken = userDataStore.getAccessToken()
        var actualRefreshToken = userDataStore.getRefreshToken()
        Assert.assertEquals(expectedAccessTokenTest, actualAccessToken)
        Assert.assertEquals(expectedRefreshTokenTest, actualRefreshToken)

        userDataStore.clear()
        actualAccessToken = userDataStore.getAccessToken()
        actualRefreshToken = userDataStore.getRefreshToken()
        Assert.assertNull(null, actualAccessToken)
        Assert.assertNull(null, actualRefreshToken)
    }

}