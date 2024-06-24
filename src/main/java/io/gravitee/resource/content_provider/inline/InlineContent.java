/*
 * Copyright © 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.resource.content_provider.inline;

import io.gravitee.resource.content_provider.api.Content;
import io.gravitee.resource.content_provider.inline.configuration.Attribute;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class InlineContent implements Content<String> {

    private final String content;
    private final Map<String, Object> attributes;

    public InlineContent(String content, List<Attribute> attributes) {
        this.content = content;
        if (attributes == null || attributes.isEmpty()) {
            this.attributes = Map.of();
        } else {
            this.attributes = attributes.stream().collect(Collectors.toMap(Attribute::key, Attribute::value));
        }
    }
}
