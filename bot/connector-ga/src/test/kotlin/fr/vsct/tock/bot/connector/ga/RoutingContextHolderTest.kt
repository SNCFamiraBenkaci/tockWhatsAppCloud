/*
 * Copyright (C) 2017 VSCT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.vsct.tock.bot.connector.ga

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import fr.vsct.tock.bot.connector.ga.GAConnector.ActionWithDelay
import fr.vsct.tock.bot.connector.ga.GAConnector.RoutingContextHolder
import fr.vsct.tock.bot.connector.ga.model.request.GAConversation
import fr.vsct.tock.bot.connector.ga.model.request.GADevice
import fr.vsct.tock.bot.connector.ga.model.request.GARequest
import fr.vsct.tock.bot.connector.ga.model.request.GASurface
import fr.vsct.tock.bot.connector.ga.model.request.GAUser
import fr.vsct.tock.bot.engine.BotBus
import fr.vsct.tock.bot.engine.action.SendSentence
import fr.vsct.tock.bot.engine.user.PlayerId
import fr.vsct.tock.bot.engine.user.PlayerType
import io.vertx.ext.web.RoutingContext
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

/**
 *
 */
class RoutingContextHolderTest {

    val bus: BotBus = mock()
    val context: RoutingContext = mock()

    @Before
    fun init() {
        whenever(bus.translate(any(), any())).thenAnswer { invocation -> invocation.arguments[0] }
        whenever(context.response()).thenReturn(mock())
    }

    @Test
    fun buildResponse_shouldMergeBasicCard_WhenApplicable() {
        with(bus) {
            val r0 = gaMessage("ok")
            val r1 = gaMessage(richResponse(basicCard("title1", null, "formattedText1")))
            val r2 = gaMessage(richResponse(basicCard("title2", "subtitle2", "formattedText2", gaImage("url2", "acc2"), gaButton("button2", "butonUrl2"))))

            val p1 = PlayerId("id1", PlayerType.user)

            val holder = RoutingContextHolder(
                    context,
                    GARequest(
                            GAUser("userId"),
                            GADevice(),
                            GASurface(emptyList()),
                            GAConversation(),
                            emptyList()
                    ),
                    listOf(r0, r1, r2).map { ActionWithDelay(SendSentence(p1, "appId", p1, null, mutableListOf(it))) }.toMutableList()
            )

            val result = holder.buildResponse()
            val richResponse = result.expectedInputs!!.first().inputPrompt.richInitialPrompt
            assertEquals(2, richResponse.items.size)
            val basicCard = richResponse.items[1].basicCard!!
            assertEquals("title1", basicCard.title)
            assertEquals("subtitle2", basicCard.subtitle)
            assertEquals("formattedText1", basicCard.formattedText)
            assertEquals(gaImage("url2", "acc2"), basicCard.image)
            assertEquals(1, basicCard.buttons.size)
            assertEquals(gaButton("button2", "butonUrl2"), basicCard.buttons.first())
        }
    }
}