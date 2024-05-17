/*
 * Copyright (C) 2017/2021 e-voyageurs technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.tock.bot.connector.whatsapp.cloud

import ai.tock.bot.connector.whatsapp.cloud.database.repository.PayloadWhatsAppCloudDAO
import ai.tock.bot.connector.whatsapp.cloud.database.repository.PayloadWhatsAppCloudMongoDAO
import ai.tock.shared.service.BotAdditionalModulesService
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton

val WhatsAppCloudConnectorModule = Kodein.Module {
    bind<PayloadWhatsAppCloudDAO>() with singleton {
        PayloadWhatsAppCloudMongoDAO
    }
}

// used in file META-INF/services/ai.tock.shared.service.BotAdditionalModulesService
class IOCModulesService : BotAdditionalModulesService {
    override fun modules() = setOf(WhatsAppCloudConnectorModule)
}