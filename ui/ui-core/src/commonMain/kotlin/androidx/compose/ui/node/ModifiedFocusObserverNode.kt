/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.compose.ui.node

import androidx.compose.ui.FocusObserverModifier
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.FocusState.Inactive
import androidx.compose.ui.focus.searchChildrenForFocusNode

@OptIn(ExperimentalFocus::class)
internal class ModifiedFocusObserverNode(
    wrapped: LayoutNodeWrapper,
    modifier: FocusObserverModifier
) : DelegatingLayoutNodeWrapper<FocusObserverModifier>(wrapped, modifier) {

    override fun propagateFocusStateChange(focusState: FocusState) {
        modifier.onFocusChange(focusState)
        super.propagateFocusStateChange(focusState)
    }

    override fun onModifierChanged() {
        super.onModifierChanged()
        // If the modifier is re-used, we can't be sure that it is in the same position as before.
        // For instance, if the observer is moved to the end of the list, and there is no focus
        // modifier following this observer, it's focus state will be invalid. To solve this, we
        // always reset the focus state when a focus observer is re-used.
        val focusNode = wrapped.findNextFocusWrapper() ?: layoutNode.searchChildrenForFocusNode()
        modifier.onFocusChange(focusNode?.modifier?.focusState ?: Inactive)
    }
}