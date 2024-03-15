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

package ai.tock.bot.connector.whatsapp.cloud.model.webhook.message.content

import com.fasterxml.jackson.annotation.JsonProperty

data class OrderContent(
        @JsonProperty("catalog_id") val catalogId: String,
        val text: String,
        @JsonProperty("product_items") val productItems: List<ProductItem>
)

data class ProductItem(
        @JsonProperty("product_retailer_id") val productRetailerId: String,
        val quantity: String,
        @JsonProperty("item_price") val itemPrice: String,
        val currency: String
)
