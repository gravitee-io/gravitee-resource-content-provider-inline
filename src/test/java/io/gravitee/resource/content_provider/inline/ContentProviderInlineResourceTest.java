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

import static io.gravitee.resource.content_provider.inline.configuration.ContentAndAttributesTestConfiguration.MY_AWESOME_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;

import io.gravitee.resource.content_provider.inline.configuration.ContentAndAttributesTestConfiguration;
import io.gravitee.resource.content_provider.inline.configuration.ContentAndNoAttributesTestConfiguration;
import io.gravitee.resource.content_provider.inline.configuration.ContentProviderInlineResourceConfiguration;
import io.gravitee.resource.content_provider.inline.configuration.EmptyContentTestConfiguration;
import io.gravitee.resource.content_provider.inline.configuration.NullContentTestConfiguration;
import io.gravitee.resource.content_provider.inline.configuration.ResourceTestConfiguration;
import java.util.Map;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Yann TAVERNIER (yann.tavernier at graviteesource.com)
 * @author GraviteeSource Team
 */

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ContentProviderInlineResourceTest {

    @Nested
    @ExtendWith(SpringExtension.class)
    @ContextConfiguration(classes = { ResourceTestConfiguration.class, EmptyContentTestConfiguration.class })
    class EmptyContent {

        @Autowired
        ContentProviderInlineResource cut;

        @Test
        void should_get_empty() {
            final ContentProviderInlineResourceConfiguration configuration = cut.configuration();
            assertThat(configuration.getContent()).isEmpty();
            assertThat(configuration.getAttributes()).isEmpty();
            cut.getContent().test().assertNoValues();
        }
    }

    @Nested
    @ExtendWith(SpringExtension.class)
    @ContextConfiguration(classes = { ResourceTestConfiguration.class, NullContentTestConfiguration.class })
    class NullContent {

        @Autowired
        ContentProviderInlineResource cut;

        @Test
        void should_get_empty() {
            final ContentProviderInlineResourceConfiguration configuration = cut.configuration();
            assertThat(configuration.getContent()).isNull();
            assertThat(configuration.getAttributes()).isNull();
            cut.getContent().test().assertNoValues();
        }
    }

    @Nested
    @ExtendWith(SpringExtension.class)
    @ContextConfiguration(classes = { ResourceTestConfiguration.class, ContentAndAttributesTestConfiguration.class })
    class ContentAndAttributes {

        @Autowired
        ContentProviderInlineResource cut;

        @Test
        void should_get_content() {
            final ContentProviderInlineResourceConfiguration configuration = cut.configuration();
            assertThat(configuration.getContent()).isEqualTo(MY_AWESOME_CONTENT);
            assertThat(configuration.getAttributes()).containsExactlyEntriesOf(Map.of("key", "value"));
            cut
                .getContent()
                .test()
                .assertValue(value -> {
                    assertThat(value.getContent()).isEqualTo(MY_AWESOME_CONTENT);
                    assertThat(value.getAttributes()).containsExactlyEntriesOf(Map.of("key", "value"));
                    return true;
                });
        }
    }

    @Nested
    @ExtendWith(SpringExtension.class)
    @ContextConfiguration(classes = { ResourceTestConfiguration.class, ContentAndNoAttributesTestConfiguration.class })
    class ContentAndNoAttributes {

        @Autowired
        ContentProviderInlineResource cut;

        @Test
        void should_get_content() {
            final ContentProviderInlineResourceConfiguration configuration = cut.configuration();
            assertThat(configuration.getContent()).isEqualTo(MY_AWESOME_CONTENT);
            assertThat(configuration.getAttributes()).isNull();
            cut
                .getContent()
                .test()
                .assertValue(value -> {
                    assertThat(value.getContent()).isEqualTo(MY_AWESOME_CONTENT);
                    assertThat(value.getAttributes()).isEmpty();
                    return true;
                });
        }
    }
}
